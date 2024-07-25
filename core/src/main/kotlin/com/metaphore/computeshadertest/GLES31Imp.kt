package com.metaphore.computeshadertest

interface GLES31Imp {
    fun onAppCreate() = Unit
    fun onAppDispose() = Unit

    fun glTexStorage2D(target: Int, levels: Int, internalFormat: Int, width: Int, height: Int)
    fun glBindImageTexture(unit: Int, texture: Int, level: Int, layered: Boolean, layer: Int, access: Int, format: Int)
    fun glDispatchCompute(num_groups_x: Int, num_groups_y: Int, num_groups_z: Int)
    fun glMemoryBarrier(barriers: Int)
}
