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

//    private val ssboId : Int

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
                // gl.glGetShaderiv(shader, GL30.GL_INFO_LOG_LENGTH, intbuf);
                // int infoLogLength = intbuf.get(0);
                // if (infoLogLength > 1) {
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
                // gl.glGetProgramiv(program, GL20.GL_INFO_LOG_LENGTH, intbuf);
                // int infoLogLength = intbuf.get(0);
                // if (infoLogLength > 1) {
                val infoLog = gl.glGetProgramInfoLog(programId)
                throw GdxRuntimeException("Failed to link shader program. Log: $infoLog");
            }
        }

        checkGdxError()

//        this.texture = Texture(GLOnlyTextureData(TEXTURE_SIZE, TEXTURE_SIZE, 0, GL_RGBA32F, GL_RGBA, GL_FLOAT))
        this.texture = Texture(ImmutableGLTextureData(TEXTURE_SIZE, TEXTURE_SIZE, 1, GL_RGBA32F))
        texture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge)
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)

        checkGdxError()
    }

//    // Init SSBO buffer.
//    init {
//        val gl = Gdx.gl30
//
//        val bufferLen = 4
//        val bufferStride = 8 // Byte type size.
//        val buffer = ByteBuffer.allocateDirect(4)
//        buffer.putInt(23)
//
//        this.ssboId = gl.glGenBuffer()
//        gl.glBindBuffer(GL_SHADER_STORAGE_BUFFER, ssboId)
//        gl.glBufferData(GL_SHADER_STORAGE_BUFFER, bufferLen * bufferStride, buffer, GL_DYNAMIC_COPY)
//        gl.glBindBufferBase(GL_SHADER_STORAGE_BUFFER, 0, ssboId) //TODO This is Use() part.
//
//        also {
////            gl.glMapBuffer()
//            val readBuf: ByteBuffer = gl.glMapBufferRange(GL_SHADER_STORAGE_BUFFER, 0, bufferLen * bufferStride, GL_MAP_READ_BIT) as ByteBuffer
//            val value = readBuf.asIntBuffer().get()
//            debug { "Value: $value" }
//        }
//
//        checkGdxError()
//    }

    override fun dispose() {
        val gl = Gdx.gl30

        gl.glDeleteShader(shaderId)
        gl.glDeleteProgram(programId)
//        gl.glDeleteBuffer(ssboId)

        texture.dispose()
    }

    /** Dispatch compute shader call. */
    fun update() {
        val gl = Gdx.gl30
        val glExt = App.Inst.gl31Ext;

        gl.glUseProgram(programId)

        checkGdxError()

//        gl.glUniform1i(fetchUniformLocation(programId, "u_textureSize"), TEXTURE_SIZE)
//        checkGdxError()

//        texture.bindImageTexture(0)
        glExt.glBindImageTexture(
            0,
            texture.textureObjectHandle,
            0,
            false,
            0,
//            GL_READ_WRITE,
            GL_WRITE_ONLY,
            GL_RGBA32F
        )

        checkGdxError()

        val invocationGroups = 16
        val shaderThreads = MathUtils.ceil(invocationGroups.toFloat() / TEXTURE_SIZE)
        glExt.glDispatchCompute(shaderThreads, shaderThreads, 1)

        checkGdxError()

        // make sure writing to image has finished before read
//        gl.glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT)
        glExt.glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT or
            GL_SHADER_STORAGE_BARRIER_BIT or
            GL_BUFFER_UPDATE_BARRIER_BIT)

        checkGdxError()
    }

//    /** Dispatch compute shader call. */
//    fun update() {
//        val gl = Gdx.gl30
//
//        gl.glUseProgram(programId)
//
//        checkGdxError()
//
//        gl.glBindBufferBase(GL_SHADER_STORAGE_BUFFER, 0, ssboId)
//
//        checkGdxError()
//
//        gl.glDispatchCompute(1, 1, 1)
//
//        checkGdxError()
//
//        // make sure writing to image has finished before read
////        gl.glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT)
//        gl.glMemoryBarrier(GL_SHADER_IMAGE_ACCESS_BARRIER_BIT or
//            GL_SHADER_STORAGE_BARRIER_BIT or
//            GL_BUFFER_UPDATE_BARRIER_BIT)
//
//        checkGdxError()
//    }

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
