package Exploring.graphics;

import Exploring.ExploringMain;
import arc.Core;
import arc.files.Fi;
import arc.graphics.Texture;
import arc.graphics.gl.Shader;
import arc.util.Time;
import mindustry.graphics.Shaders;
import mindustry.mod.Mods;

import static mindustry.Vars.renderer;

public class ExShaders {
    public static ModSurfaceShader dynamicBarShader;

    public static void load() {
        dynamicBarShader = new ModSurfaceShader("dyn-bar-vert", "dyn-bar-frag") {
            @Override
            public String textureName() {
                return "noise";
            }

            @Override
            public void loadNoise() {
                super.loadNoise();

                noiseTex = ExTex.dynBarNoise;
            }

            @Override
            public Texture getTexture() {
                return ExTex.dynBarNoise;
            }
        };
    }

    public static Fi getShaderFi(String file) {
        Mods.LoadedMod mod = ExploringMain.MOD;

        Fi shaders = mod.root.child("shaders");
        if (shaders.exists()) {
            if (shaders.child(file).exists()) return shaders.child(file);
        }

        return Shaders.getShaderFi(file);
    }

    public static class ModShader extends Shader {
        public ModShader(String vert, String frag) {
            super(getShaderFi(vert + ".vert"), getShaderFi(frag + ".frag"));
        }
    }

    public static class ModSurfaceShader extends ModShader {
        Texture noiseTex;

        public ModSurfaceShader(String vert, String frag) {
            super(vert, frag);
            loadNoise();
        }

        public String textureName() {
            return "noise";
        }

        public Texture getTexture() {
            return null;
        }

        public void loadNoise() {
            Core.assets.load("sprites/" + textureName() + ".png", Texture.class).loaded = t -> {
                t.setFilter(Texture.TextureFilter.linear);
                t.setWrap(Texture.TextureWrap.repeat);
            };
        }

        @Override
        public void apply() {
            setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2, Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);
            setUniformf("u_time", Time.time);

            if (hasUniform("u_noise")) {
                if (noiseTex == null) {
                    noiseTex = Core.assets.get("sprites/" + textureName() + ".png", Texture.class);
                }

                noiseTex.bind(1);
                renderer.effectBuffer.getTexture().bind(0);

                setUniformi("u_noise", 1);
            }
        }
    }
}
