package Exploring.graphics;

import mindustry.graphics.CacheLayer;
import mindustry.graphics.Shaders;

import static mindustry.graphics.CacheLayer.add;

public class ExCacheLayer {
    public static CacheLayer

    liquidHelium;

    public static void init() {
        add(
                liquidHelium = new CacheLayer.ShaderLayer(ExShaders.liquidHelium)
        );
    }
}
