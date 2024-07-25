#version 310 es
//#version 430 core

//#ifdef GL_ES
//#define LOWP lowp
//precision mediump float;
//precision mediump int;
//precision lowp sampler2D;
//precision lowp samplerCube;
//#else
//#define LOWP
//#endif

layout (local_size_x = 16, local_size_y = 16, local_size_z = 1) in;

uniform layout(binding = 0, rgba32f) writeonly mediump image2D u_outputImage;

layout(std430, binding = 0) buffer u_testBuffer
{
    int TestBuffer[];
};

void main() {
    ivec2 texelCoord = ivec2(gl_GlobalInvocationID.xy);
    ivec2 textureSize = imageSize(u_outputImage);
    if (texelCoord.x >= textureSize.x || texelCoord.y >= textureSize.y) {
        return;
    }

    vec4 color = vec4(0.0, 0.85, 0.0, 1.0);

    color.r = float(texelCoord.x) * (1.0 / float(textureSize.x));
    color.b = float(texelCoord.y) * (1.0 / float(textureSize.y));

    imageStore(u_outputImage, texelCoord, color);

    int bufferIdx = int(gl_GlobalInvocationID.x) * textureSize.x + int(gl_GlobalInvocationID.y);
    TestBuffer[bufferIdx] = int(gl_GlobalInvocationID.x * 100u + gl_GlobalInvocationID.y);
}

