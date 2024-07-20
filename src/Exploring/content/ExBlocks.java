package Exploring.content;

import Exploring.ExSettings;
import Exploring.graphics.ExCacheLayer;
import Exploring.world.blocks.MetaItemBridge;
import Exploring.world.blocks.test.AbyssCore;
import Exploring.world.blocks.test.DPSWall;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawFlame;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;

import static mindustry.type.ItemStack.empty;
import static mindustry.type.ItemStack.with;

public class ExBlocks {
    public static Block abyssFloor,

    metaItemBridge,

    highSiliconSmelter,

    test, dps1, dps2, dps3;

    public static void load() {
        metaItemBridge = new MetaItemBridge("meta-item-bridge") {{
            requirements(Category.effect, with(Items.lead, 6, Items.copper, 6, Items.titanium, 25, Items.silicon, 30, ExItems.basicItemInterface, 2));
            speed = 60f / 11f;
            group = BlockGroup.transportation;
            range = 12;
            hasPower = true;
            envEnabled |= Env.space;
            consumePower(0.30f);
        }};

        if (!ExSettings.devEnv) return;

        abyssFloor = new Floor("abyss-floor") {{
            speedMultiplier = 0.5f;
            variants = 0;
            status = ExStatusEffects.abyss;
            statusDuration = 90f;
            isLiquid = true;
            cacheLayer = ExCacheLayer.abyss;
            albedo = 0f;
            supportsOverlay = true;
        }};

        highSiliconSmelter = new GenericCrafter("high-silicon-smelter") {{
            requirements(Category.crafting, with(Items.copper, 120, Items.titanium, 60, Items.silicon, 20));
            craftEffect = Fx.smeltsmoke;
            outputItem = new ItemStack(Items.silicon, 4);
            craftTime = 120f;
            size = 3;
            hasPower = true;
            hasLiquids = false;
            drawer = new DrawMulti(new DrawDefault(), new DrawFlame(Color.valueOf("ffef99")));
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.12f;

            consumeItem(Items.sand, 5);
            consumePower(1.6f);
        }};

//        test = new Wall("test") {
//            {
//                requirements(Category.effect, BuildVisibility.sandboxOnly, ItemStack.empty);
//                health = 100;
//            }
//
//            @Override
//            public void setBars() {
//                super.setBars();
//
//                addBar("test", entity -> new DynamicBar(
//                        "a",
//                        Color.red,
//                        () -> 0.99f
//                ));
//            }
//        };

        test = new AbyssCore("abyss-core") {{
            requirements(Category.effect, empty);
            health = 4000000;
        }};

        dps1 = new DPSWall("dps-1", 1);
        dps2 = new DPSWall("dps-2", 2);
        dps3 = new DPSWall("dps-3", 3);
    }
}
