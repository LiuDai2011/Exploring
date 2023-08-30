package Exploring.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Item;

public class ExItems {
    public static Item
            titaniumAlloy, siliconBlock, iron, vacuumTube, pureSilicon, chip, quantumChip, battery, fullBattery,

            stone;

    public static void load() {
        Items.silicon.hardness = 3;
        titaniumAlloy = new Item("titanium-alloy", Color.valueOf("9eb0eb")) {{
            cost = 1f;
        }};
        siliconBlock = new Item("silicon-block", Color.valueOf("9edaac")) {{
            cost = 1f;
        }};
        iron = new Item("iron", Color.valueOf("d9d9d9")) {{
            hardness = 1;
            cost = 1f;
            alwaysUnlocked = true;
        }};
        vacuumTube = new Item("vacuum-tube", Color.valueOf("f3f3f3")) {{
            cost = 1f;
        }};
        pureSilicon = new Item("pure-silicon", Color.valueOf("44ff44")){{
            cost = 1f;
        }};
        chip = new Item("chip", Color.valueOf("bbd0e2")){{
            cost = 1f;
        }};
        quantumChip = new Item("quantum-chip", Color.valueOf("dbefff")){{
            cost = 1f;
        }};
        battery = new Item("battery", Color.valueOf("ffe257")){{
            cost = 1f;
        }};
        fullBattery = new Item("full-battery", Color.valueOf("ffee9e")){{
            cost = 1f;
            charge = 5f;
        }};
    }
}
