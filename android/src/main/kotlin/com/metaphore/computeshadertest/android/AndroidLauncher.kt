package com.metaphore.computeshadertest.android

import android.opengl.GLES30
import android.opengl.GLES31
import android.os.Bundle
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.android.AndroidApplication

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.metaphore.computeshadertest.App
import com.metaphore.computeshadertest.GLES31Imp

/** Launches the Android application. */
class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gl31Imp = object : GLES31Imp {
            override fun glTexStorage2D(target: Int, levels: Int, internalFormat: Int, width: Int, height: Int) {
                GLES30.glTexStorage2D(target, levels, internalFormat, width, height)
            }

            override fun glBindImageTexture(unit: Int, texture: Int, level: Int, layered: Boolean, layer: Int, access: Int, format: Int) {
                GLES31.glBindImageTexture(unit, texture, level, layered, layer, access, format)
            }

            override fun glDispatchCompute(num_groups_x: Int, num_groups_y: Int, num_groups_z: Int) {
                GLES31.glDispatchCompute(num_groups_x, num_groups_y, num_groups_z)
            }

            override fun glMemoryBarrier(barriers: Int) {
                GLES31.glMemoryBarrier(barriers)
            }
        }

        initialize(App(gl31Imp), AndroidApplicationConfiguration().apply {
            useGL30 = true
            useImmersiveMode = true // Recommended, but not required.
        })

        val gl31 = AndroidGL31()
        graphics.gL31 = gl31
        Gdx.gl31 = gl31
    }
}
