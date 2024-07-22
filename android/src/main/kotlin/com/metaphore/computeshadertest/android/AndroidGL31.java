package com.metaphore.computeshadertest.android;

import android.annotation.TargetApi;

import android.opengl.GLES31;
import com.badlogic.gdx.backends.android.AndroidGL30;
import com.badlogic.gdx.graphics.GL31;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@TargetApi(21)
public class AndroidGL31 extends AndroidGL30 implements GL31 {

    @Override
    public void glDispatchCompute(int num_groups_x, int num_groups_y, int num_groups_z) {
        GLES31.glDispatchCompute(num_groups_x, num_groups_y, num_groups_z);
    }

    @Override
    public void glDispatchComputeIndirect(long indirect) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glDrawArraysIndirect(int mode, long indirect) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glDrawElementsIndirect(int mode, int type, long indirect) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glFramebufferParameteri(int target, int pname, int param) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glGetFramebufferParameteriv(int target, int pname, IntBuffer params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glGetProgramInterfaceiv(int program, int programInterface, int pname, IntBuffer params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int glGetProgramResourceIndex(int program, int programInterface, String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String glGetProgramResourceName(int program, int programInterface, int index) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glGetProgramResourceiv(int program, int programInterface, int index, IntBuffer props, IntBuffer length, IntBuffer params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int glGetProgramResourceLocation(int program, int programInterface, String name) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glUseProgramStages(int pipeline, int stages, int program) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glActiveShaderProgram(int pipeline, int program) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int glCreateShaderProgramv(int type, String[] strings) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glBindProgramPipeline(int pipeline) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glDeleteProgramPipelines(int n, IntBuffer pipelines) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glGenProgramPipelines(int n, IntBuffer pipelines) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean glIsProgramPipeline(int pipeline) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glGetProgramPipelineiv(int pipeline, int pname, IntBuffer params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform1i(int program, int location, int v0) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform2i(int program, int location, int v0, int v1) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform3i(int program, int location, int v0, int v1, int v2) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform4i(int program, int location, int v0, int v1, int v2, int v3) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform1ui(int program, int location, int v0) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform2ui(int program, int location, int v0, int v1) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform3ui(int program, int location, int v0, int v1, int v2) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform4ui(int program, int location, int v0, int v1, int v2, int v3) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform1f(int program, int location, float v0) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform2f(int program, int location, float v0, float v1) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform3f(int program, int location, float v0, float v1, float v2) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform4f(int program, int location, float v0, float v1, float v2, float v3) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform1iv(int program, int location, IntBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform2iv(int program, int location, IntBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform3iv(int program, int location, IntBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform4iv(int program, int location, IntBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform1uiv(int program, int location, IntBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform2uiv(int program, int location, IntBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform3uiv(int program, int location, IntBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform4uiv(int program, int location, IntBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform1fv(int program, int location, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform2fv(int program, int location, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform3fv(int program, int location, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniform4fv(int program, int location, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniformMatrix2fv(int program, int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniformMatrix3fv(int program, int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniformMatrix4fv(int program, int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniformMatrix2x3fv(int program, int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniformMatrix3x2fv(int program, int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniformMatrix2x4fv(int program, int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniformMatrix4x2fv(int program, int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniformMatrix3x4fv(int program, int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glProgramUniformMatrix4x3fv(int program, int location, boolean transpose, FloatBuffer value) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glValidateProgramPipeline(int pipeline) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String glGetProgramPipelineInfoLog(int program) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glBindImageTexture(int unit, int texture, int level, boolean layered, int layer, int access, int format) {
        GLES31.glBindImageTexture(unit, texture, level, layered, layer, access, format);
    }

    @Override
    public void glGetBooleani_v(int target, int index, IntBuffer data) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glMemoryBarrier(int barriers) {
        GLES31.glMemoryBarrier(barriers);
    }

    @Override
    public void glMemoryBarrierByRegion(int barriers) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glTexStorage2DMultisample(int target, int samples, int internalformat, int width, int height, boolean fixedsamplelocations) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glGetMultisamplefv(int pname, int index, FloatBuffer val) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glSampleMaski(int maskNumber, int mask) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glGetTexLevelParameteriv(int target, int level, int pname, IntBuffer params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glGetTexLevelParameterfv(int target, int level, int pname, FloatBuffer params) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glBindVertexBuffer(int bindingindex, int buffer, long offset, int stride) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glVertexAttribFormat(int attribindex, int size, int type, boolean normalized, int relativeoffset) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glVertexAttribIFormat(int attribindex, int size, int type, int relativeoffset) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glVertexAttribBinding(int attribindex, int bindingindex) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void glVertexBindingDivisor(int bindingindex, int divisor) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

