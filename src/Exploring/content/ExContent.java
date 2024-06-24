package Exploring.content;

import Exploring.ExSettings;
import Exploring.graphics.ExShaders;
import Exploring.graphics.ExTex;
import Exploring.world.entities.EntityRegister;

public class ExContent {
    public static void load() {
        EntityRegister.load();

        ExTex.load();
        ExShaders.load();
        ExBulletTypes.load();
        ExUnitTypes.load();
        ExExtraUnitTypes.load();
        ExBlocks.load();
        ExOverride.load();
    }
}
