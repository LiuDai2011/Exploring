package Exploring.content;

import Exploring.ExSettings;
import Exploring.graphics.ExCacheLayer;
import Exploring.world.blocks.MetaItemBridge;
import Exploring.world.blocks.test.AbyssCore;
import Exploring.world.blocks.test.DPSWall;
import arc.Core;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawFlame;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;

import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.empty;
import static mindustry.type.ItemStack.with;

public class ExBlocks {
    public static Block abyssFloor,

    metaItemBridge,

    highSiliconSmelter,

    coreLinker,

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

        coreLinker = new StorageBlock("core-linker") {{
            requirements(Category.effect, with(Items.titanium, 50, Items.thorium, 30, ExItems.basicItemInterface, 5));// TODO
            size = 1;
            itemCapacity = 0;
            scaledHealth = 12;
            update = true;

            buildType = () -> new StorageBuild() {
                @Override
                public void created() {
                    super.created();
                    Core.app.post(() -> {
                        ExGroups.checkCoreLinkerKey(team);
                        ExGroups.coreLinkers.get(team).add(this);
                    });
                }

                @Override
                public void onRemoved() {
                    super.onRemoved();
                    ExGroups.coreLinkers.get(team).remove(this);
                }

                @Override
                public void updateTile() {
                    super.updateTile();
                    if (linkedCore == null) {
                        linkedCore = core();
                    } else {
                        items = linkedCore.items;
                    }
                }

                @Override
                public boolean canPickup() {
                    return false;
                }

//                @Override
//                public void drawSelect() {
//                    super.drawSelect();
//
//                    Lines.stroke(1f, Pal.accent);
//                    Cons<Building> outline = b -> {
//                        for(int i = 0; i < 4; i++){
//                            Point2 p = Geometry.d8edge[i];
//                            float offset = -Math.max(b.block.size - 1, 0) / 2f * tilesize;
//                            Draw.rect("block-select", b.x + offset * p.x, b.y + offset * p.y, i * 90);
//                        }
//                    };
//
//                    outline.get(this);
//                    Draw.reset();
//                }
            };
        }};

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
