package Exploring.content;

import Exploring.ExSettings;
import arc.Events;
import mindustry.content.Blocks;
import mindustry.content.Liquids;
import mindustry.game.EventType;
import mindustry.type.ItemStack;
import mindustry.world.Block;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ExOverride {
    public static void load() {
        if (!ExSettings.devEnv) return;

        loadDev();
    }

    private static void loadDev() {
        show(Blocks.powerSource);
        show(Blocks.powerVoid);
        show(Blocks.itemSource);
        show(Blocks.itemVoid);
        show(Blocks.liquidSource);
        show(Blocks.liquidVoid);
        show(Blocks.payloadSource);
        show(Blocks.payloadVoid);
        show(Blocks.heatSource);

        Events.on(EventType.ClientLoadEvent.class, e -> {
            overrideBlocks();
        });

        Liquids.cryofluid.temperature = -1e4f;
        Liquids.cryofluid.heatCapacity = 1e4f;

        Blocks.coreShard.unitCapModifier *= 100;
        Blocks.coreAcropolis.unitCapModifier *= 100;
        Blocks.coreBastion.unitCapModifier *= 100;
        Blocks.coreCitadel.unitCapModifier *= 100;
        Blocks.coreZone.unitCapModifier *= 100;
        Blocks.coreFoundation.unitCapModifier *= 100;
        Blocks.coreNucleus.unitCapModifier *= 100;

//        ((ItemTurret) Blocks.foreshadow).ammo(Items.surgeAlloy, new PointBulletType(){{
//            shootEffect = Fx.instShoot;
//            hitEffect = Fx.instHit;
//            smokeEffect = Fx.smokeCloud;
//            trailEffect = Fx.instTrail;
//            despawnEffect = Fx.instBomb;
//            trailSpacing = 20f;
//            damage = 1350;
//            splashDamage = damage;
//            splashDamageRadius = 20f;
//            buildingDamageMultiplier = 0.2f;
//            speed = ((ItemTurret) Blocks.foreshadow).range;
//            hitShake = 6f;
//            ammoMultiplier = 1f;
//        }});
        // Blocks.foreshadow.ammoTypes.get(Items.surgeAlloy).splashDamage = Blocks.foreshadow.ammoTypes.get(Items.surgeAlloy).damage = 1000000;
    }

    private static void show(Block block) {
        block.requirements(block.category, block.requirements);
    }

    private static void overrideBlocks() {
        try {
            Field[] fields = Blocks.class.getDeclaredFields();
            for (var field : fields) {
                field.setAccessible(true);
                if (!Modifier.isStatic(field.getModifiers())) continue;
                Block block = (Block) field.get(null);
                overrideBlock(block);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void overrideBlock(Block block) {
        if (block == null) return;
        block.requirements = ItemStack.empty;
        block.researchCostMultiplier = -1f;
        block.researchCost = ItemStack.empty;
        block.buildCost = 1f;
        block.buildCostMultiplier = 0.1f;
    }
}
