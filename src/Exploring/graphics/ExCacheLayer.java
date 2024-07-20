package Exploring.graphics;

import mindustry.graphics.CacheLayer.ShaderLayer;

import static mindustry.graphics.CacheLayer.add;

public class ExCacheLayer {
    public static ShaderLayer abyss;

    public static void load() {
        add(
                abyss = new ShaderLayer(ExShaders.abyss)
        );
    }
}
