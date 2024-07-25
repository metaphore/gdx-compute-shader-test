package com.metaphore.computeshadertest

import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.TextureData
import com.badlogic.gdx.graphics.TextureData.TextureDataType
import com.badlogic.gdx.utils.GdxRuntimeException

/**
 * This TextureData is a required for GL compute shader textures.
 * The data is not managed.
 */
class ImmutableGLTextureData(
    private val width: Int,
    private val height: Int,
    private val mipLevel: Int,
    private val internalFormat: Int) :
    TextureData {

    private var isPrepared: Boolean = false

    override fun getType(): TextureDataType {
        return TextureDataType.Custom
    }

    override fun isPrepared(): Boolean {
        return isPrepared
    }

    override fun prepare() {
        if (isPrepared) throw GdxRuntimeException("Already prepared")
        isPrepared = true
    }

    override fun consumeCustomData(target: Int) {
        App.Inst.gl31Ext.glTexStorage2D(target, mipLevel, internalFormat, width, height)
//        Gdx.gl.glTexImage2D(target, mipLevel, internalFormat, width, height, 0, format, type, null)
    }

    override fun consumePixmap(): Pixmap {
        throw GdxRuntimeException("This TextureData implementation does not return a Pixmap")
    }

    override fun disposePixmap(): Boolean {
        throw GdxRuntimeException("This TextureData implementation does not return a Pixmap")
    }

    override fun getWidth(): Int {
        return this.width
    }

    override fun getHeight(): Int {
        return this.height
    }

    override fun getFormat(): Pixmap.Format {
        return Pixmap.Format.RGBA8888
    }

    override fun useMipMaps(): Boolean {
        return false
    }

    override fun isManaged(): Boolean {
        return false
    }
}
