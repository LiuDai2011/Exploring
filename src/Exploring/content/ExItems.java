package Exploring.content;

import Exploring.graphics.ExColor;
import Exploring.world.meta.interf.ItemInterface;
import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Item;

public class ExItems {
    public static Item

            basicItemInterface,

    singularityEncapsulation,

    fuelUnitI;

    public static void load() {
        float interfCost = 0.005f,
                unitCost = 0.003f;

        basicItemInterface = new ItemInterface("basic-item-interface", Color.valueOf("ffd37f")) {{
            cost = interfCost;
        }};

        singularityEncapsulation = new Item("singularity-encapsulation", ExColor.test) {{
            cost = 1500f;
        }};

        fuelUnitI = new Item("fuel-unit-i", Items.coal.color) {{
            cost = unitCost;
            flammability = 1f;
        }};
    }
}
