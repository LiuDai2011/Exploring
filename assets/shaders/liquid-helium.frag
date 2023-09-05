#define HIGHP

uniform sampler2D u_texture;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

void main(){
    gl_FragColor = vec4(u_campos.x, u_campos.y + 2 * u_time, u_resolution.x, v_texCoords.y);
}