@file:JvmName("Lwjgl3Launcher")

package com.metaphore.computeshadertest.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.metaphore.computeshadertest.App
import com.metaphore.computeshadertest.GLES31Imp
import org.lwjgl.opengl.GL42
import org.lwjgl.opengl.GL43

/** Launches the desktop (LWJGL3) application. */
fun main() {
    // This handles macOS support and helps on Windows.
    if (StartupHelper.startNewJvmIfRequired())
      return

    val gl31Imp = object : GLES31Imp {
        override fun glTexStorage2D(target: Int, levels: Int, internalFormat: Int, width: Int, height: Int) {
            GL42.glTexStorage2D(target, levels, internalFormat, width, height)
        }

        override fun glBindImageTexture(unit: Int, texture: Int, level: Int, layered: Boolean, layer: Int, access: Int, format: Int) {
            GL42.glBindImageTexture(unit, texture, level, layered, layer, access, format)
        }

        override fun glDispatchCompute(num_groups_x: Int, num_groups_y: Int, num_groups_z: Int) {
            GL43.glDispatchCompute(num_groups_x, num_groups_y, num_groups_z)
        }

        override fun glMemoryBarrier(barriers: Int) {
            GL42.glMemoryBarrier(barriers)
        }
    }

    Lwjgl3Application(App(gl31Imp), Lwjgl3ApplicationConfiguration().apply {
        setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.GL32, 4, 3)
        setTitle("compute-shader-test")
        setWindowedMode(480, 480)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
