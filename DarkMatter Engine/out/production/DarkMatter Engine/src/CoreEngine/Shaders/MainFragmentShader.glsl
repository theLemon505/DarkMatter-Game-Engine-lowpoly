#version 330 core

in vec3 passColor;
in vec2 passTextureCoord;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
out vec4 outColor;

uniform sampler2D tex;
uniform vec3 color;
uniform vec3 lightColor;
uniform float intensity;
uniform float shineDamper;
uniform float reflectivity;
void main() {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);
	float nDotl = dot(unitNormal, unitLightVector);
	float brightness = max(nDotl, 0.2);
	vec3 diffuse = brightness * lightColor * vec3(intensity,intensity,intensity);
	vec3 unitVectorToCamera  = normalize(toCameraVector);
	vec3 lightDirectionVector = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirectionVector, unitNormal);
	float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
	specularFactor = max(specularFactor, 0.0);
	float dampedFactor = pow(specularFactor, shineDamper);
	vec3 finalSpecular = dampedFactor * reflectivity * lightColor;
	outColor = vec4(diffuse, 1.0) * texture(tex, passTextureCoord) * vec4(color, 1.0) + vec4(finalSpecular, 1.0);
}