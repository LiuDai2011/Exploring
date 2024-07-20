package Exploring.content;

import Exploring.graphics.ExCacheLayer;
import Exploring.graphics.ExShaders;
import Exploring.graphics.ExTex;
import Exploring.world.entities.EntityRegister;

public class ExContent {
    public static void load() {
        EntityRegister.load();
        ExTeam.load();

        ExTex.load();
        ExShaders.load();
        ExCacheLayer.load();

        ExStatusEffects.load();
        ExItems.load();
        ExBulletTypes.load();
        ExUnitTypes.load();
        ExExtraUnitTypes.load();
        ExBlocks.load();

        ExOverride.set();
    }
}
