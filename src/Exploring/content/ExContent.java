package Exploring.content;

import Exploring.ExploringMain;
import Exploring.graphics.ExShaders;
import arc.func.Cons;
import arc.graphics.Texture;
import mindustry.graphics.CacheLayer;

public class ExContent {
    public static Texture LHNoise;

    public static CacheLayer LHLayer;

    Texture loadTex(String name, Cons<Texture> modifier){
        Texture tex = new Texture(ExploringMain.MOD.root.child("textures").child(name + (name.endsWith(".png") ? "" : ".png")));
        modifier.get(tex);

        return tex;
    }

    public void load() {
        loadBLC();

        ExShaders.init();

        ExStatusEffects.load();
        ExLiquids.load();
        ExItems.load();
//        ExUnitTypes.load();
        ExBlocks.load();
        ExPlanets.load();
        ExSectorPresets.load();
        ExSerpuloTechTree.load();

    }

    private void loadTexture() {
         LHNoise = loadTex("LH-noise", t -> {
            t.setFilter(Texture.TextureFilter.linear);
            t.setWrap(Texture.TextureWrap.repeat);
        });
    }

    private void loadBLC() {
        loadTexture();

        CacheLayer.add(
                LHLayer = new CacheLayer.ShaderLayer(ExShaders.liquidHelium)
        );
    }
}
