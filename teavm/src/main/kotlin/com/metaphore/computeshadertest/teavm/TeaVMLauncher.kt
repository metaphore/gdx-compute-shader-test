@file:JvmName("TeaVMLauncher")

package com.metaphore.computeshadertest.teavm

import com.badlogic.gdx.Gdx
import com.github.xpenatan.gdx.backends.teavm.TeaApplication
import com.github.xpenatan.gdx.backends.teavm.TeaApplicationConfiguration
import com.github.xpenatan.gdx.backends.teavm.TeaGL30
import com.github.xpenatan.gdx.backends.teavm.dom.HTMLCanvasElementWrapper
import com.github.xpenatan.gdx.backends.teavm.dom.HTMLElementWrapper
import com.github.xpenatan.gdx.backends.teavm.gl.WebGL2RenderingContextWrapper
import com.metaphore.computeshadertest.App
import com.metaphore.computeshadertest.GLES31Imp
import ktx.log.debug

/** Launches the TeaVM/HTML application. */
fun main() {
    val config = TeaApplicationConfiguration("canvas").apply {
        //// If width and height are each greater than 0, then the app will use a fixed size.
        //width = 640
        //height = 480
        //// If width and height are both 0, then the app will use all available space.
        //width = 0
        //height = 0
        //// If width and height are both -1, then the app will fill the canvas size.
        width = -1
        height = -1
        useGL30 = true
    }

    //TODO AC:
    // The WebGL2 is GLES 3.0 equivalent and the latest GL-like API available in modern browsers.
    // As of Jul '24 there's no sign or hope for any updates to WebGL that could unlock
    // possibilities for compute shaders and GLES 3.1+ functionality.
    // The next gen WebGPU API is substantially different from OpenGL (just like OpenGL vs Vulkan)
    // and is not likely to be supported by TeaVM backend nor but libGDX in general.
    // So maybe some time in the future revisit the situation. Until then, good luck!
    val glExt = object : GLES31Imp {

        private lateinit var gl: WebGL2RenderingContextWrapper

        override fun onAppCreate() {
            //TODO Find a way to properly extract the WebGL context from TeaGL classes or fetch it manually.
//            val teaGL30 = Gdx.gl30 as TeaGL30
//            gl = teaGL30.javaClass.getDeclaredField("gl").get(teaGL30) as WebGL2RenderingContextWrapper
//            debug { "WebGL2 context has been obtained: $gl" }
        }

        override fun glTexStorage2D(target: Int, levels: Int, internalFormat: Int, width: Int, height: Int) {
            TODO("Not yet implemented")
        }

        override fun glBindImageTexture(unit: Int, texture: Int, level: Int, layered: Boolean, layer: Int, access: Int, format: Int) {
            TODO("Not yet implemented")
        }

        override fun glDispatchCompute(num_groups_x: Int, num_groups_y: Int, num_groups_z: Int) {
            TODO("Not yet implemented")
        }

        override fun glMemoryBarrier(barriers: Int) {
            TODO("Not yet implemented")
        }
    }

    TeaApplication(App(glExt), config)
}
