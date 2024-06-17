package Exploring.content;

import Exploring.ExSettings;
import Exploring.ui.elements.DynamicBar;
import Exploring.world.blocks.MetaItemBridge;
import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.BuildVisibility;
import mindustry.world.meta.Env;

import static mindustry.type.ItemStack.with;

public class ExBlocks {
    public static Block
            metaItemBridge,

    test;

    public static void load() {
        metaItemBridge = new MetaItemBridge("meta-item-bridge") {{
            requirements(Category.effect, with(Items.phaseFabric, 5, Items.silicon, 7, Items.lead, 10, Items.graphite, 10, Items.titanium, 25, Items.silicon, 30));
            speed = 60f / 11f;
            group = BlockGroup.transportation;
            range = 12;
            arrowPeriod = 0.9f;
            arrowTimeScl = 2.75f;
            hasPower = true;
            pulse = true;
            envEnabled |= Env.space;
            consumePower(0.30f);
        }};

        if (!ExSettings.devEnv) return;

        test = new Wall("test") {
            {
                requirements(Category.effect, BuildVisibility.sandboxOnly, ItemStack.empty);
                health = 100;
            }

            @Override
            public void setBars() {
                super.setBars();

                addBar("test", entity -> new DynamicBar(
                        "a",
                        Color.red,
                        () -> 0.99f
                ));
            }
        };
    }
}
