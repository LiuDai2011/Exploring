package Exploring.content;

import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.world.blocks.environment.OreBlock;

public class ExOverride {
    public static void load() {
        Items.silicon.hardness = 3;

        Blocks.stone = new OreBlock("stone", ExItems.stone);
    }
}
