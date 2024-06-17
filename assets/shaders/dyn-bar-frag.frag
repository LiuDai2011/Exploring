#define HIGHP

#define SCALE 2.1f
#define SPEED 1.3f

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
    float time = u_time * 2.5f;
    float a = res_pos.x * u_resolution.x * SCALE - res_pos.y * u_resolution.y * SCALE + time * SPEED;
    a /= 100;
    float x = a - floor(a);
    if (c.a > 0.1f) res = mix(res, base, 0.6f * (1 - pow(x, 0.5f)));
    gl_FragColor = res;
}
