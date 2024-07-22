#version 430 core

out vec4 FragColor;

in vec2 TexCoords;

layout(binding = 0) uniform sampler2D u_texture0;

void main() {
    vec3 texCol = texture(u_texture0, TexCoords).rgb;
    FragColor = vec4(texCol, 1.0);
}
