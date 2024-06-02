package Exploring.content;

import Exploring.world.blocks.MetaItemBridge;
import mindustry.world.Block;

public class ExBlocks {
    public static Block metaItemBridge;

    public static void load() {
        metaItemBridge = new MetaItemBridge("meta-item-bridge") {{
            ;
        }};
    }
}
