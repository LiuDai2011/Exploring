package Exploring.content;

import Exploring.ExVars;
import Exploring.ExploringMain;
import Exploring.graphics.ExShaders;
import Exploring.util.HashMapMaker;
import Exploring.util.Pair;
import Exploring.util.Tuple;
import arc.func.Cons;
import arc.graphics.Texture;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.graphics.CacheLayer;
import mindustry.type.Liquid;
import mindustry.type.LiquidStack;

import java.util.HashMap;

import static mindustry.type.ItemStack.with;

public class ExContent {
    public static final HashMapMaker<
            Liquid,
            Pair<
                    Float,
                    Float>
            > hashMapMaker = new HashMapMaker<>();

    public static Texture LHNoise;

    public static CacheLayer LHLayer;

    static Texture loadTex(String name, Cons<Texture> modifier){
        Texture tex = new Texture(ExploringMain.MOD.root.child("textures").child(name + (name.endsWith(".png") ? "" : ".png")));
        modifier.get(tex);

        return tex;
    }

    public static void load() {
        ExStatusEffects.load();
        ExLiquids.load();
        ExItems.load();
        ExUnitTypes.load();
        ExBlocks.load();
        ExPlanets.load();
        ExSectorPresets.load();
        ExOverride.load();
        ExSerpuloTechTree.load();
    }

    public static void loadVars() {
        ExVars.molecularWeight = new HashMap<>();

        ExVars.molecularWeight.put(
                ExItems.stone,
                new Tuple<>(
                        new Tuple<>(
                                2f,
                                with(ExItems.stone, 9),
                                LiquidStack.empty
                        ),
                        new Tuple<>(
                                0f,
                                with(Items.silicon, 8, Items.scrap, 1),
                                LiquidStack.empty
                        ),
                        new Pair<>(
                            1,
                            hashMapMaker.make(
                                    Liquids.water,
                                    new Pair<>(
                                            0.3f,
                                            1.3333f
                                    )
                            )
                        )
                )
        );
    }

    private static void loadTexture() {
         LHNoise = loadTex("LH-noise", t -> {
            t.setFilter(Texture.TextureFilter.linear);
            t.setWrap(Texture.TextureWrap.repeat);
        });
    }

    public static void loadBeforeContent() {
        loadTexture();

        CacheLayer.add(
                LHLayer = new CacheLayer.ShaderLayer(ExShaders.liquidHelium)
        );
    }
}
