package Exploring.graphics;

import Exploring.content.ExContent;
import arc.Core;
import arc.graphics.Texture;
import arc.graphics.gl.Shader;
import arc.util.Time;

import static mindustry.Vars.renderer;
import static mindustry.graphics.Shaders.getShaderFi;

public class ExShaders {
    public static ModSurfaceShader

            liquidHelium;

    public static void init() {
        liquidHelium = new ModSurfaceShader("liquid-helium") {
            @Override
            public String textureName(){
                return "LH-noise";
            }

            @Override
            public void loadNoise(){
                super.loadNoise();

                noiseTex2 = ExContent.LHNoise;
                noiseTex1 = ExContent.LHNoise;
            }

            @Override
            public Texture getTexture(){
                return ExContent.LHNoise;
            }
        };
    }

    public static class ModSurfaceShader extends ModShader{
        protected Texture noiseTex1, noiseTex2;

        public ModSurfaceShader(String frag){
            super("screenspace", frag);
            loadNoise();
        }

        public ModSurfaceShader(String vertRaw, String fragRaw){
            super(vertRaw, fragRaw);
            loadNoise();
        }

        public Texture getTexture(){
            return null;
        }

        public String textureName(){
            return "noise";
        }

        public void loadNoise(){
            Core.assets.load("sprites/" + textureName() + ".png", Texture.class).loaded = t -> {
                t.setFilter(Texture.TextureFilter.linear);
                t.setWrap(Texture.TextureWrap.repeat);
            };
        }

        @Override
        public void apply(){
            setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2, Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);
            setUniformf("u_time", Time.time);

            if(hasUniform("u_noise")){
                if(noiseTex1 == null){
                    noiseTex1 = getTexture() == null ? Core.assets.get("sprites/" + textureName() + ".png", Texture.class) : getTexture();
                }

                noiseTex1.bind(1);
                renderer.effectBuffer.getTexture().bind(0);

                setUniformi("u_noise", 1);
            }

            if(hasUniform("u_noise_2")){
                if(noiseTex2 == null){
                    noiseTex2 = Core.assets.get("sprites/" + "noise" + ".png", Texture.class);
                }

                noiseTex2.bind(1);
                renderer.effectBuffer.getTexture().bind(0);

                setUniformi("u_noise_2", 1);
            }
        }
    }

    public static class ModShader extends Shader {
        public ModShader(String vert, String frag){
            super(getShaderFi(vert + ".vert"), getShaderFi(frag + ".frag"));
        }
    }
}
