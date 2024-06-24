#define HIGHP

#define SCALE 2.1
#define SPEED 1.3

const vec4 base = vec4(1, 1, 1, 1);

varying vec4 v_color;
varying vec4 v_mix_color;
varying vec2 v_texCoords;
varying vec4 res_pos;
uniform sampler2D u_texture;

uniform sampler2D u_noise;

//uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

void main(){
    vec4 c = texture2D(u_texture, v_texCoords);
    vec4 res = v_color * mix(c, vec4(v_mix_color.rgb, c.a), v_mix_color.a);
    float time = u_time * 2.5;
    float rpos = res_pos.x * u_resolution.x * SCALE - res_pos.y * u_resolution.y * SCALE - time * SPEED;
    rpos /= 100;
    float x = 1.0 - rpos + floor(rpos);
    if (c.a > 0.1) res = mix(res, base, 0.6 - pow(x, 0.5) * 0.6);
    gl_FragColor = res;
}
