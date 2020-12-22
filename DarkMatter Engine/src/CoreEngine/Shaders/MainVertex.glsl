#version 460 core

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 model;
uniform mat4 viewMat;
uniform mat4 projection;
void main(){

	gl_Position =  projection * viewMat * model * vec4(position * 10,1.0);
	pass_textureCoords = textureCoords;
}