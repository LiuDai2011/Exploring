package Exploring.content;

import Exploring.graphics.ExShaders;
import Exploring.graphics.ExTex;

public class ExContent {
    public static void load() {
        ExTex.load();
        ExShaders.load();
        ExExtraUnitTypes.load();
        ExBlocks.load();
        ExOverride.load();
    }
}
