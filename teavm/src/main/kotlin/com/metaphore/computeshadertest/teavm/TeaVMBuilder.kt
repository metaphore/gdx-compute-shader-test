package com.metaphore.computeshadertest.teavm

import com.github.xpenatan.gdx.backends.teavm.config.AssetFileHandle
import java.io.File
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder
import com.github.xpenatan.gdx.backends.teavm.config.plugins.TeaReflectionSupplier
import com.github.xpenatan.gdx.backends.teavm.gen.SkipClass

/** Builds the TeaVM/HTML application. */
@SkipClass
object TeaVMBuilder {
    @JvmStatic fun main(arguments: Array<String>) {
        val teaBuildConfiguration = TeaBuildConfiguration().apply {
//            assetsPath.add(File("../assets"))
            assetsPath.add(AssetFileHandle("../assets"))
            webappPath = File("build/dist").canonicalPath
            // Register any extra classpath assets here:
            // additionalAssetsClasspathFiles += "com/metaphore/computeshadertest/asset.extension"
        }

        // Register any classes or packages that require reflection here:
         TeaReflectionSupplier.addReflectionClass("com.github.xpenatan.gdx.backends.teavm.TeaGL30")

        val tool = TeaBuilder.config(teaBuildConfiguration)
        tool.mainClass = "com.metaphore.computeshadertest.teavm.TeaVMLauncher"
        TeaBuilder.build(tool)
    }
}
