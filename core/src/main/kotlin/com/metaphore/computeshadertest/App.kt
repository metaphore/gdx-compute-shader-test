package com.metaphore.computeshadertest

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL31.*
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.profiling.GLErrorListener
import com.badlogic.gdx.graphics.profiling.GLProfiler
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.BufferUtils
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.GdxRuntimeException
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.graphics.use
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.IntBuffer

class App(val gl31Ext: GLES31Imp) : KtxGame<KtxScreen>() {

    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG

        ShaderProgram.pedantic = false

        GLProfiler(Gdx.graphics).also {
            it.enable()
            it.listener = GLErrorListener.THROWING_LISTENER
        }

        instance = this;

        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }

    override fun dispose() {
        super.dispose()

        instance = null;
    }

    companion object {
        private var instance: App? = null
        public val Inst: App get() {
            return instance ?: throw GdxRuntimeException("App is not initialized yet.")
        }
    }
}

class FirstScreen : KtxScreen {
    private val batch = SpriteBatch()
    private val computeShader = ComputeShaderTester()
    private val viewport = ScreenViewport()

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        batch.projectionMatrix = viewport.camera.combined
    }

    override fun render(delta: Float) {
        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)

        computeShader.update()

        batch.use {
            it.draw(computeShader.getTexture(), 0f, 0f, viewport.worldWidth, viewport.worldHeight)
        }
    }

    override fun dispose() {
        batch.disposeSafely()
        computeShader.dispose()
    }
}

class ComputeShaderTester : Disposable {

    private val shaderId: Int
    private val programId: Int
    private val texture: Texture

    private val ssboId : Int
    private val ssboSize : Int

    fun getTexture(): Texture = texture

    init {
        val gl = Gdx.gl30

        this.shaderId = gl.glCreateShader(GL_COMPUTE_SHADER);
        if (shaderId == 0)
            throw GdxRuntimeException("Failed to create compute shader.");

        val shaderSource = Gdx.files.internal("test0.compute.glsl").readString()
        gl.glShaderSource(shaderId, shaderSource)
        gl.glCompileShader(shaderId)
        also {
            val intbuf = BufferUtils.newIntBuffer(1)
            gl.glGetShaderiv(shaderId, GL_COMPILE_STATUS, intbuf)
            val compileResult = intbuf.get(0)
            if (compileResult != GL_TRUE) {
                val infoLog = gl.glGetShaderInfoLog(shaderId)
                throw GdxRuntimeException("Failed to compile shader. Log: $infoLog")
            }
        }

        this.programId = gl.glCreateProgram()
        gl.glAttachShader(programId, shaderId)
        gl.glLinkProgram(programId)
        also {
            val tmp = ByteBuffer.allocateDirect(4)
            tmp.order(ByteOrder.nativeOrder())
            val intbuf = tmp.asIntBuffer()

            gl.glGetProgramiv(programId, GL_LINK_STATUS, intbuf)
            val linkResult = intbuf[0]
            if (linkResult != GL_TRUE) {
                val infoLog = gl.glGetProgramInfoLog(programId)
                throw GdxRuntimeException("Failed to link shader program. Log: $infoLog");
            }
        }

        this.texture = Texture(ImmutableGLTextureData(TEXTURE_SIZE, TEXTURE_SIZE, 1, GL_RGBA32F))
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge)
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
    }

    // Init SSBO buffer.
    init {
        val gl = Gdx.gl30

        val bufferLen = 128
        val bufferStride = 4 // int = 4 bytes.
        this.ssboSize = bufferLen * bufferStride
        val intBuffer = BufferUtils.newIntBuffer(bufferLen)
        intBuffer.put(23)
        intBuffer.put(7)
        intBuffer.put(42)
        intBuffer.put(101)
        intBuffer.position(0)

        this.ssboId = gl.glGenBuffer()
        gl.glBindBuffer(GL_SHADER_STORAGE_BUFFER, ssboId)
        // Initialize SSBO with data. Or pass NULL to "data" to initialize just the SSBO memory space.
        gl.glBufferData(GL_SHADER_STORAGE_BUFFER, ssboSize, intBuffer, GL_DYNAMIC_READ) // PS: "size" is read from the "data" (on LWJGL3).
        gl.glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0) // Unbind

        checkGdxError()
    }

    override fun dispose() {
        val gl = Gdx.gl30

        gl.glDeleteShader(shaderId)
        gl.glDeleteProgram(programId)
        gl.glDeleteBuffer(ssboId)

        texture.dispose()
    }

    /** Dispatch compute shader call. */
    fun update() {
        val gl = Gdx.gl30
        val glExt = App.Inst.gl31Ext;

        gl.glUseProgram(programId)

        glExt.glBindImageTexture(0, texture.textureObjectHandle, 0, false, 0, GL_WRITE_ONLY, GL_RGBA32F)

        gl.glBindBuffer(GL_SHADER_STORAGE_BUFFER, ssboId)
        gl.glBindBufferBase(GL_SHADER_STORAGE_BUFFER, 0, ssboId)

        val invocationGroups = 16
        val shaderThreads = MathUtils.ceil(invocationGroups.toFloat() / TEXTURE_SIZE)
        glExt.glDispatchCompute(shaderThreads, shaderThreads, 1)

        // Make sure writing to image has finished before read
//        gl.glMemoryBarrier(GL_ALL_BARRIER_BITS)
        glExt.glMemoryBarrier(
            GL_SHADER_IMAGE_ACCESS_BARRIER_BIT or
            GL_SHADER_STORAGE_BARRIER_BIT or
            GL_BUFFER_UPDATE_BARRIER_BIT)

        gl.glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0) // Unbind

        // Read data from SSBO.
        also {
            gl.glBindBuffer(GL_SHADER_STORAGE_BUFFER, ssboId)
            // Each invocation spawns a new ByteBuffer + IntBuffer.
            // So it's best to avoid reading data frequently.
            val byteBuffer = gl.glMapBufferRange(GL_SHADER_STORAGE_BUFFER, 0, ssboSize, GL_MAP_READ_BIT) as ByteBuffer
            val intView = byteBuffer.asIntBuffer()
            //TODO Read data here using intView.
            gl.glUnmapBuffer(GL_SHADER_STORAGE_BUFFER)
            gl.glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0) // Unbind
        }

        checkGdxError()
    }

    companion object {
        const val TEXTURE_SIZE = 16

        fun fetchUniformLocation(programId: Int, name: String): Int {
            val location = Gdx.gl30.glGetUniformLocation(programId, name)
            if (location == -1) {
                throw GdxRuntimeException("No uniform with name '$name' in shader")
            }
            return location
        }

        fun checkGdxError() {
            val glError = Gdx.gl30.glGetError()
            if (glError != 0)
                throw GdxRuntimeException("GL ERROR: $glError")
        }
    }
}
