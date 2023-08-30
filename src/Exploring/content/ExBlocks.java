package Exploring.content;

import Exploring.graphics.ExCacheLayer;
import Exploring.world.blocks.*;
import Exploring.world.blocks.distribution.*;
import Exploring.world.blocks.drills.ExAttributeCrafter;
import Exploring.world.blocks.drills.ExDrill;
import Exploring.world.blocks.drills.ExFracker;
import Exploring.world.blocks.drills.ExSolidPump;
import Exploring.world.blocks.power.*;
import Exploring.world.blocks.storage.ExCoreBlock;
import Exploring.world.blocks.storage.ExStorageBlock;
import Exploring.world.blocks.storage.SubspaceStorageLinker;
import Exploring.world.blocks.turrets.*;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.*;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.consumers.ConsumeItemExplode;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.consumers.ConsumeItemRadioactive;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

//hou ec80fe9aad          qian 93fde537c7
public class ExBlocks {
    public static Block


            liquidHelium,

    oreIron, oreSilicon,

    exCoreShard, exContainer, exVault, subspaceStorageLinker,

    exCopperWall, exCopperWallLarge, exTitaniumWall, exTitaniumWallLarge, exPlastaniumWall, exPlastaniumWallLarge, exThoriumWall, exThoriumWallLarge, exDoor, exDoorLarge,
            exPhaseWall, exPhaseWallLarge, exSurgeWall, exSurgeWallLarge, leadWall, leadWallLarge, testWall,

    siliconPurifier, tubeMakingMachine, siliconRecombiner, titaniumFurnace, liquidHeliumCooler,

    airCollector,

    super_excitedThoriumReactor, excitedThoriumReactor,

    exMechanicalDrill, exPneumaticDrill, exLaserDrill, exBlastDrill, exWaterExtractor, exOilExtractor, exCultivator,

    exCombustionGenerator, exThermalGenerator, exSteamGenerator, exDifferentialGenerator, exRtgGenerator, exSolarPanel, exLargeSolarPanel,
            exThoriumReactor, exImpactReactor, exBattery, exBatteryLarge, exPowerNode, exPowerNodeLarge, exSurgeTower, exDiode,

    exConveyor, exTitaniumConveyor, exPlastaniumConveyor, exArmoredConveyor, exDistributor, exJunction, exItemBridge, exPhaseConveyor, exSorter, exInvertedSorter, exRouter,
            exOverflowGate, exUnderflowGate, exMassDriver,

    sandboxOverdriveDome,

    test_pt, exDuo, exScatter, exScorch, exHail, exArc, exWave, exLancer, exSwarmer, exSalvo, exFuse, exRipple, exCyclone, exForeshadow, exSpectre, exMeltdown, exSegment, exParallax, exTsunami,

    eternity, ballLightning, ion,

    anuken, nianNianYouYu, guiY, RA2, No9527, lyr, oneGamma, zzcQAQ, paoTaiS, AarnMAX, RHN, chiRe, jianBian, boLuo, LiuDai;

    public static void load() {
        Log.info("Loading blocks...");

        loadEnv();
        loadStorage();
        loadDrill();
        loadWall();
        loadDistribution();
        loadFactory();
        loadPower();
        loadEffect();
        loadTurret();
        loadCaiDan();
    }

    public static void loadEnv() {
        Log.info("Loading env...");


        liquidHelium = new Floor("liquid-helium") {{
            speedMultiplier = 0.2f;
            variants = 0;
            liquidDrop = ExLiquids.liquidHelium;
            liquidMultiplier = 1.5f;
            isLiquid = true;
            status = ExStatusEffects.freeze;
            statusDuration = 120f;
            drownTime = 200f;
            cacheLayer = ExCacheLayer.liquidHelium;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        oreIron = new OreBlock("ore-iron", ExItems.iron) {{
            oreDefault = true;
            oreThreshold = 0.81f;
            oreScale = 22f;
        }};

        oreSilicon = new OreBlock("ore-silicon", Items.silicon) {{
            oreDefault = true;
            oreThreshold = 0.79f;
            oreScale = 18f;
        }};
    }

    public static void loadStorage() {
        Log.info("Loading cores...");

        exCoreShard = new ExCoreBlock("ex-core-shard") {{
            requirements(Category.effect, BuildVisibility.editorOnly, with(Items.copper, 1000, Items.lead, 800));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = UnitTypes.alpha;
            health = 1100;
            itemCapacity = 4000;
            size = 3;

            unitCapModifier = 8;
        }};

        exContainer = new ExStorageBlock("ex-container") {{
            requirements(Category.effect, with(Items.titanium, 100));
            size = 2;
            itemCapacity = 300;
            scaledHealth = 55;
        }};

        exVault = new ExStorageBlock("ex-vault") {{
            requirements(Category.effect, with(Items.titanium, 250, Items.thorium, 125));
            size = 3;
            itemCapacity = 1000;
            scaledHealth = 55;
        }};

        subspaceStorageLinker = new SubspaceStorageLinker("subspace-storage-linker") {{
            requirements(Category.effect, with(ExItems.iron, 750, ExItems.titaniumAlloy, 150, ExItems.quantumChip, 3));
            hasPower = true;
            itemCapacity = 15000;
            scaledHealth = 55;
            runEffect = ExFx.laserBeamP;
            consumePower(20f);
        }};
    }

    public static void loadWall() {
        Log.info("Loading walls...");

        int wallHealthMultiplier = 4;

        exCopperWall = new ExWall("ex-copper-wall") {{
            requirements(Category.defense, with(Items.copper, 6));
            health = 80 * wallHealthMultiplier;
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};

        exCopperWallLarge = new ExWall("ex-copper-wall-large") {{
            requirements(Category.defense, ItemStack.mult(exCopperWall.requirements, 4));
            health = 80 * 4 * wallHealthMultiplier;
            size = 2;
            envDisabled |= Env.scorching;
        }};

        exTitaniumWall = new ExWall("ex-titanium-wall") {{
            requirements(Category.defense, with(Items.titanium, 6));
            health = 110 * wallHealthMultiplier;
            envDisabled |= Env.scorching;
        }};

        exTitaniumWallLarge = new ExWall("ex-titanium-wall-large") {{
            requirements(Category.defense, ItemStack.mult(exTitaniumWall.requirements, 4));
            health = 110 * wallHealthMultiplier * 4;
            size = 2;
            envDisabled |= Env.scorching;
        }};

        exPlastaniumWall = new ExWall("ex-plastanium-wall") {{
            requirements(Category.defense, with(Items.plastanium, 5, Items.metaglass, 2));
            health = 125 * wallHealthMultiplier;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
            envDisabled |= Env.scorching;
        }};

        exPlastaniumWallLarge = new ExWall("ex-plastanium-wall-large") {{
            requirements(Category.defense, ItemStack.mult(exPlastaniumWall.requirements, 4));
            health = 125 * wallHealthMultiplier * 4;
            size = 2;
            insulated = true;
            absorbLasers = true;
            schematicPriority = 10;
            envDisabled |= Env.scorching;
        }};

        exThoriumWall = new ExWall("ex-thorium-wall") {{
            requirements(Category.defense, with(Items.thorium, 6));
            health = 200 * wallHealthMultiplier;
            envDisabled |= Env.scorching;
        }};

        exThoriumWallLarge = new ExWall("ex-thorium-wall-large") {{
            requirements(Category.defense, ItemStack.mult(exThoriumWall.requirements, 4));
            health = 200 * wallHealthMultiplier * 4;
            size = 2;
            envDisabled |= Env.scorching;
        }};

        exPhaseWall = new ExWall("ex-phase-wall") {{
            requirements(Category.defense, with(Items.phaseFabric, 6));
            health = 150 * wallHealthMultiplier;
            chanceDeflect = 10f;
            flashHit = true;
            envDisabled |= Env.scorching;
        }};

        exPhaseWallLarge = new ExWall("ex-phase-wall-large") {{
            requirements(Category.defense, ItemStack.mult(exPhaseWall.requirements, 4));
            health = 150 * 4 * wallHealthMultiplier;
            size = 2;
            chanceDeflect = 10f;
            flashHit = true;
            envDisabled |= Env.scorching;
        }};

        exSurgeWall = new ExWall("ex-surge-wall") {{
            requirements(Category.defense, with(Items.surgeAlloy, 6));
            health = 230 * wallHealthMultiplier;
            lightningChance = 0.05f;
            envDisabled |= Env.scorching;
        }};

        exSurgeWallLarge = new ExWall("ex-surge-wall-large") {{
            requirements(Category.defense, ItemStack.mult(exSurgeWall.requirements, 4));
            health = 230 * 4 * wallHealthMultiplier;
            size = 2;
            lightningChance = 0.05f;
            envDisabled |= Env.scorching;
        }};

        exDoor = new ExDoor("ex-door") {{
            requirements(Category.defense, with(Items.titanium, 6, Items.silicon, 4));
            health = 100 * wallHealthMultiplier;
            envDisabled |= Env.scorching;
        }};

        exDoorLarge = new ExDoor("ex-door-large") {{
            requirements(Category.defense, ItemStack.mult(exDoor.requirements, 4));
            openfx = Fx.dooropenlarge;
            closefx = Fx.doorcloselarge;
            health = 100 * 4 * wallHealthMultiplier;
            size = 2;
            envDisabled |= Env.scorching;
        }};

        leadWall = new ExWall("lead-wall") {{
            requirements(Category.defense, with(Items.lead, 6));
            health = 90 * wallHealthMultiplier;
            researchCostMultiplier = 0.1f;
            envDisabled |= Env.scorching;
        }};

        leadWallLarge = new ExWall("lead-wall-large") {{
            requirements(Category.defense, ItemStack.mult(leadWall.requirements, 4));
            health = 90 * 4 * wallHealthMultiplier;
            size = 2;
            envDisabled |= Env.scorching;
        }};

        testWall = new ExWall("test-wall") {{
            requirements(Category.defense, ItemStack.mult(leadWall.requirements, 4));
            health = 20000 * wallHealthMultiplier;
            size = 2;
            envDisabled |= Env.scorching;
        }};
    }

    public static void loadFactory() {
        Log.info("Loading factories...");

        siliconPurifier = new ExGenericCrafter("silicon-purifier") {{
            requirements(Category.crafting, with(ExItems.iron, 100, Items.silicon, 25, Items.lead, 100, Items.graphite, 50));

            craftEffect = Fx.pulverizeMedium;
            outputItem = new ItemStack(ExItems.siliconBlock, 3);
            craftTime = 30f;
            itemCapacity = 20;
            size = 2;
            hasItems = true;
            hasPower = true;

            consumePower(1f);
            consumeItem(Items.silicon, 4);
        }};

        tubeMakingMachine = new ExGenericCrafter("tube-making-machine") {{
            requirements(Category.crafting, with(Items.titanium, 150, ExItems.iron, 50, Items.lead, 100, Items.graphite, 50, ExItems.siliconBlock, 25));

            craftEffect = ExFx.laserBeam;
            outputItem = new ItemStack(ExItems.vacuumTube, 1);
            craftTime = 300f;
            itemCapacity = 20;
            size = 3;
            hasItems = true;
            hasPower = true;
            hasLiquids = true;

            consumePower(2.5f);
            consumeItems(with(Items.metaglass, 3, Items.copper, 1, ExItems.iron, 2, ExItems.siliconBlock, 3));
            consumeLiquid(Liquids.water, 0.25f);
        }};

        siliconRecombiner = new ExGenericCrafter("silicon-recombiner") {{
            requirements(Category.crafting, with(ExItems.titaniumAlloy, 150, ExItems.iron, 50, ExItems.siliconBlock, 100, Items.graphite, 50, ExItems.vacuumTube, 25));

            craftEffect = ExFx.laserBeam;
            outputItem = new ItemStack(ExItems.pureSilicon, 1);
            craftTime = 180f;
            itemCapacity = 20;
            size = 2;
            hasItems = true;
            hasPower = true;
            hasLiquids = true;

            consumePower(3f);
            consumeItem(ExItems.siliconBlock, 1);
            consumeLiquid(Liquids.water, 2f);
        }};

        titaniumFurnace = new ExGenericCrafter("titanium-furnace") {{
            requirements(Category.crafting, with(Items.titanium, 150, ExItems.iron, 50, ExItems.vacuumTube, 25));

            craftEffect = ExFx.laserBeamR;
            outputItem = new ItemStack(ExItems.titaniumAlloy, 1);
            craftTime = 480f;
            itemCapacity = 20;
            size = 2;
            hasItems = true;
            hasPower = true;

            consumePower(2.5f);
            consumeItems(with(Items.copper, 2, Items.graphite, 1, Items.titanium, 3, ExItems.iron, 2, ExItems.siliconBlock, 1));
        }};

        liquidHeliumCooler = new ExGenericCrafter("liquid-helium-cooler") {{
            requirements(Category.crafting, with(Items.titanium, 150, ExItems.iron, 50, ExItems.vacuumTube, 25));

            craftEffect = Fx.pulverizeMedium;
            outputLiquid = new LiquidStack(ExLiquids.liquidHelium, 0.01f);
            craftTime = 150f;
            liquidCapacity = 35f;
            size = 2;
            hasLiquids = true;
            hasPower = true;

            consumePower(1.2f);
            consumeLiquids(LiquidStack.with(ExLiquids.helium, 0.02f, Liquids.water, 0.01f));
        }};


        airCollector = new ExGenericCrafter("air-collector") {{
            requirements(Category.crafting, with(ExItems.iron, 90, Items.metaglass, 50, Items.silicon, 50, Items.graphite, 40));
            size = 3;

            researchCostMultiplier = 1.2f;
            craftTime = 10f;
            rotate = true;
            invertFlip = true;
            group = BlockGroup.liquids;

            liquidCapacity = 50f;

            consumeLiquid(Liquids.water, 10f / 60f);
            consumePower(1f);

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.water, 2f),
                    new DrawBubbles(Color.valueOf("7693e3")) {{
                        sides = 10;
                        recurrence = 3f;
                        spread = 6;
                        radius = 1.5f;
                        amount = 20;
                    }},
                    new DrawRegion(),
                    new DrawLiquidOutputs(),
                    new DrawGlowRegion() {{
                        alpha = 0.7f;
                        color = Color.valueOf("c4bdf3");
                        glowIntensity = 0.3f;
                        glowScale = 6f;
                    }}
            );

            ambientSound = Sounds.electricHum;
            ambientSoundVolume = 0.08f;

            regionRotated1 = 3;
            outputLiquids = LiquidStack.with(ExLiquids.helium, 0.5f / 60, Liquids.hydrogen, 0.5f / 60);
            liquidOutputDirections = new int[]{1, 3};
        }};
    }

    public static void loadPower() {
        Log.info("Loading power...");

        exPowerNode = new ExPowerNode("ex-power-node") {{
            requirements(Category.power, with(Items.copper, 1, Items.lead, 3));
            maxNodes = 10;
            laserRange = 6;
        }};

        exPowerNodeLarge = new ExPowerNode("ex-power-node-large") {{
            requirements(Category.power, with(Items.titanium, 5, Items.lead, 10, Items.silicon, 3));
            size = 2;
            maxNodes = 15;
            laserRange = 15f;
        }};

        exSurgeTower = new ExPowerNode("ex-surge-tower") {{
            requirements(Category.power, with(Items.titanium, 7, Items.lead, 10, Items.silicon, 15, Items.surgeAlloy, 15));
            size = 2;
            maxNodes = 2;
            laserRange = 40f;
            schematicPriority = -15;
        }};

        exDiode = new ExPowerDiode("ex-diode") {{
            requirements(Category.power, with(Items.silicon, 10, Items.plastanium, 5, Items.metaglass, 10));
        }};

        exBattery = new ExBattery("ex-battery") {{
            requirements(Category.power, with(Items.copper, 5, Items.lead, 20));
            consumePowerBuffered(4000f);
            baseExplosiveness = 1f;
        }};

        exBatteryLarge = new ExBattery("ex-battery-large") {{
            requirements(Category.power, with(Items.titanium, 20, Items.lead, 50, Items.silicon, 30));
            size = 3;
            consumePowerBuffered(50000f);
            baseExplosiveness = 5f;
        }};

        exCombustionGenerator = new ExConsumeGenerator("ex-combustion-generator") {{
            requirements(Category.power, with(Items.copper, 25, Items.lead, 15));
            powerProduction = 1f;
            itemDuration = 120f;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.03f;
            generateEffect = Fx.generatespark;

            consume(new ConsumeItemFlammable());
            consume(new ConsumeItemExplode());

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
        }};

        exThermalGenerator = new ExThermalGenerator("ex-thermal-generator") {{
            requirements(Category.power, with(Items.copper, 40, Items.graphite, 35, Items.lead, 50, Items.silicon, 35, Items.metaglass, 40));
            powerProduction = 1.8f;
            generateEffect = Fx.redgeneratespark;
            effectChance = 0.011f;
            size = 2;
            floating = true;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
        }};

        exSteamGenerator = new ExConsumeGenerator("ex-steam-generator") {{
            requirements(Category.power, with(Items.copper, 35, Items.graphite, 25, Items.lead, 40, Items.silicon, 30));
            powerProduction = 5.5f;
            itemDuration = 90f;
            consumeLiquid(Liquids.water, 0.1f);
            hasLiquids = true;
            size = 2;
            generateEffect = Fx.generatespark;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.06f;

            consume(new ConsumeItemFlammable());
            consume(new ConsumeItemExplode());

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawWarmupRegion(),
                    new DrawRegion("-turbine") {{
                        rotateSpeed = 2f;
                    }},
                    new DrawRegion("-turbine") {{
                        rotateSpeed = -2f;
                        rotation = 45f;
                    }},
                    new DrawRegion("-cap"),
                    new DrawLiquidRegion()
            );
        }};

        exDifferentialGenerator = new ExConsumeGenerator("ex-differential-generator") {{
            requirements(Category.power, with(Items.copper, 70, Items.titanium, 50, Items.lead, 100, Items.silicon, 65, Items.metaglass, 50));
            powerProduction = 18f;
            itemDuration = 220f;
            hasLiquids = true;
            hasItems = true;
            size = 3;
            ambientSound = Sounds.steam;
            generateEffect = Fx.generatespark;
            ambientSoundVolume = 0.03f;

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion(), new DrawLiquidRegion());

            consumeItem(Items.pyratite);
            consumeLiquid(Liquids.cryofluid, 0.1f);
        }};

        exRtgGenerator = new ExConsumeGenerator("ex-rtg-generator") {{
            requirements(Category.power, with(Items.lead, 100, Items.silicon, 75, Items.phaseFabric, 25, Items.plastanium, 75, Items.thorium, 50));
            size = 2;
            powerProduction = 4.5f;
            itemDuration = 60 * 14f;
            envEnabled = Env.any;
            generateEffect = Fx.generatespark;

            drawer = new DrawMulti(new DrawDefault(), new DrawWarmupRegion());
            consume(new ConsumeItemRadioactive());
        }};

        exSolarPanel = new ExSolarGenerator("ex-solar-panel") {{
            requirements(Category.power, with(Items.lead, 10, Items.silicon, 15));
            powerProduction = 0.1f;
        }};

        exLargeSolarPanel = new ExSolarGenerator("ex-solar-panel-large") {{
            requirements(Category.power, with(Items.lead, 80, Items.silicon, 110, Items.phaseFabric, 15));
            size = 3;
            powerProduction = 1.3f;
        }};

        exThoriumReactor = new ExNuclearReactor("ex-thorium-reactor") {{
            requirements(Category.power, with(Items.lead, 300, Items.silicon, 200, Items.graphite, 150, Items.thorium, 150, Items.metaglass, 50));
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;
            size = 3;
            health = 700;
            itemDuration = 360f;
            powerProduction = 15f;
            heating = 0.02f;

            consumeItem(Items.thorium);
            consumeLiquid(Liquids.cryofluid, heating / coolantPower).update(false);
        }};

        exImpactReactor = new ExImpactReactor("ex-impact-reactor") {{
            requirements(Category.power, with(Items.lead, 500, Items.silicon, 300, Items.graphite, 400, Items.thorium, 100, Items.surgeAlloy, 250, Items.metaglass, 250));
            size = 4;
            health = 900;
            powerProduction = 130f;
            itemDuration = 140f;
            ambientSound = Sounds.pulse;
            ambientSoundVolume = 0.07f;

            consumePower(25f);
            consumeItem(Items.blastCompound);
            consumeLiquid(Liquids.cryofluid, 0.25f);
        }};

        excitedThoriumReactor = new ExNuclearReactor("excited-thorium-reactor") {{
            requirements(Category.power, with(ExItems.iron, 500, ExItems.siliconBlock, 25, Items.graphite, 100, Items.metaglass, 50));
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;
            size = 3;
            health = 700;
            itemDuration = 30f;
            powerProduction = 95f;
            heating = 0.06f;

            consumeItem(Items.thorium);
            consumeLiquid(Liquids.cryofluid, heating / coolantPower * 3f).update(false);
        }};

        super_excitedThoriumReactor = new ExNuclearReactor("super-excited-thorium-reactor") {{
            requirements(Category.power, with(ExItems.titaniumAlloy, 150, ExItems.iron, 500, ExItems.vacuumTube, 100, ExItems.pureSilicon, 25, Items.metaglass, 50));
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.24f;
            size = 4;
            health = 700;
            itemDuration = 3f;
            powerProduction = 300f;
            heating = 0.11f;

            consumeItem(Items.thorium);
            consumeLiquid(ExLiquids.liquidHelium, heating / coolantPower * 7f).update(false);
        }};
    }

    public static void loadEffect() {
        Log.info("Loading effects...");

        sandboxOverdriveDome = new ExOverdriveProjector("sandbox-overdrive-dome") {{
            requirements(Category.effect, with(Items.copper, 1));
            size = 3;
            range = 2000f;
            speedBoost = 10000f;
            useTime = 300f;
        }};
    }

    public static void loadTurret() {
        Log.info("Loading turrets...");

        test_pt = new ExItemTurret("test_pt") {{
            requirements(Category.turret, with(Items.copper, 1));
            ammo(
                    Items.copper, new BasicBulletType(2.5f, 9) {{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 2;
                    }},
                    Items.lead, new LaserBulletType(140) {{
                        colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                        chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);

                        buildingDamageMultiplier = 0.25f;
                        hitEffect = Fx.hitLancer;
                        hitSize = 4;
                        lifetime = 16f;
                        drawSize = 400f;
                        collidesAir = false;
                        length = 173f;
                        ammoMultiplier = 1f;
                        pierceCap = 4;
                    }},
                    Items.graphite, new BasicBulletType() {
                        {
                            shootEffect = Fx.instShoot;
                            hitEffect = Fx.instHit;
                            smokeEffect = Fx.smokeCloud;
                            trailEffect = Fx.instTrail;
                            despawnEffect = Fx.instBomb;
//                        trailSpacing = 20f;
                            damage = 1;
                            buildingDamageMultiplier = 0.2f;
                            speed = 25f;
                            hitShake = 6f;
                            ammoMultiplier = 1f;

                            fragBullets = 5;
                            fragBullet = new BasicBulletType(25f, 1) {
                                {
                                    width = 7f;
                                    height = 9f;
                                    lifetime = 60f;
                                    ammoMultiplier = 2;

                                    fragBullets = 2;
                                    fragBullet = new PointBulletType() {{
                                        shootEffect = Fx.instShoot;
                                        hitEffect = Fx.instBomb;
                                        smokeEffect = Fx.smokeCloud;
                                        trailEffect = Fx.instBomb;
                                        despawnEffect = Fx.instBomb;
                                        trailSpacing = 20f;
                                        damage = 1350;
                                        buildingDamageMultiplier = 0.2f;
                                        speed = 0.5f;
                                        hitShake = 6f;
                                        ammoMultiplier = 1f;
                                        homingPower = 1800f;
                                        homingRange = 1800f;
                                        splashDamageRadius = 80f;
                                        splashDamage = 350f;
                                    }};
                                }

                                @Override
                                public void draw(Bullet b) {
                                }
                            };
                        }

                        @Override
                        public void draw(Bullet b) {
                        }
                    },
                    Items.coal, new PointBulletType() {{
                        shootEffect = Fx.instShoot;
                        hitEffect = ExFx.laserBeamP;
                        smokeEffect = ExFx.laserBeamP;
                        trailEffect = ExFx.instTrailRainbow;
                        despawnEffect = ExFx.laserBeamP;
                        trailSpacing = 20f;
                        damage = 1350;
                        buildingDamageMultiplier = 0.2f;
                        speed = 100f;
                        homingPower = 1800f;
                        homingRange = 1800f;
                        hitShake = 6f;
                        ammoMultiplier = 24f;
                        splashDamageRadius = 400f;
                        splashDamage = 350f;
                    }},
                    Items.sand, new BasicBulletType() {
                        {
                            width = 7f;
                            height = 9f;
                            lifetime = 60f;
                            damage = 100f;
                            speed = 15f;

                            ammoMultiplier = 10;

                            fragBullets = 2;
                            fragBullet = new BasicBulletType() {
                                {
                                    width = 7f;
                                    height = 9f;
                                    lifetime = 60f;
                                    damage = 100f;
                                    speed = 15f;

                                    fragBullets = 2;
                                    fragBullet = new BasicBulletType() {
                                        {
                                            width = 7f;
                                            height = 9f;
                                            lifetime = 60f;
                                            damage = 100f;
                                            speed = 15f;

                                            fragBullets = 2;
                                            fragBullet = new BasicBulletType() {
                                                {
                                                    width = 7f;
                                                    height = 9f;
                                                    lifetime = 60f;
                                                    damage = 100f;
                                                    speed = 15f;

                                                    fragBullets = 2;
                                                    fragBullet = new BasicBulletType() {
                                                        {
                                                            width = 7f;
                                                            height = 9f;
                                                            lifetime = 60f;
                                                            damage = 100f;
                                                            speed = 15f;

                                                            fragBullets = 2;
                                                            fragBullet = new PointBulletType() {{
                                                                shootEffect = Fx.instShoot;
                                                                hitEffect = ExFx.laserBeamP;
                                                                smokeEffect = Fx.smokeCloud;
                                                                trailEffect = ExFx.laserBeamP;
                                                                despawnEffect = ExFx.laserBeamP;
                                                                trailSpacing = 20f;
                                                                damage = 1350;
                                                                buildingDamageMultiplier = 0.2f;
                                                                speed = 0.3f;
                                                                homingPower = 1800f;
                                                                homingRange = 1800f;
                                                                hitShake = 6f;
                                                                splashDamageRadius = 1350f;
                                                                splashDamage = 1350f;
                                                            }};
                                                        }

                                                        @Override
                                                        public void draw(Bullet b) {
                                                        }
                                                    };
                                                }

                                                @Override
                                                public void draw(Bullet b) {
                                                }
                                            };
                                        }

                                        @Override
                                        public void draw(Bullet b) {
                                        }
                                    };
                                }

                                @Override
                                public void draw(Bullet b) {
                                }
                            };
                        }

                        @Override
                        public void draw(Bullet b) {
                        }
                    },
                    Items.titanium, new BasicBulletType() {{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        damage = 100f;
                        speed = 15f;

                        ammoMultiplier = 10;

                        fragBullets = 2;
                        fragBullet = new BasicBulletType() {{
                            width = 7f;
                            height = 9f;
                            lifetime = 60f;
                            damage = 100f;
                            speed = 15f;

                            fragBullets = 2;
                            fragBullet = new BasicBulletType() {{
                                width = 7f;
                                height = 9f;
                                lifetime = 60f;
                                damage = 100f;
                                speed = 15f;

                                fragBullets = 2;
                                fragBullet = new BasicBulletType() {{
                                    width = 7f;
                                    height = 9f;
                                    lifetime = 60f;
                                    damage = 100f;
                                    speed = 15f;

                                    fragBullets = 2;
                                    fragBullet = new BasicBulletType() {{
                                        width = 7f;
                                        height = 9f;
                                        lifetime = 60f;
                                        damage = 100f;
                                        speed = 15f;

                                        fragBullets = 2;
                                        fragBullet = new PointBulletType() {{
                                            shootEffect = Fx.instShoot;
                                            hitEffect = ExFx.laserBeamP;
                                            smokeEffect = Fx.smokeCloud;
                                            trailEffect = ExFx.laserBeamP;
                                            despawnEffect = ExFx.laserBeamP;
                                            trailSpacing = 20f;
                                            damage = 1350;
                                            buildingDamageMultiplier = 0.2f;
                                            speed = 5f;
                                            homingPower = 1800f;
                                            homingRange = 1800f;
                                            hitShake = 6f;
                                            splashDamageRadius = 1350f;
                                            splashDamage = 1350f;
                                        }};
                                    }};
                                }};
                            }};
                        }};
                    }},
                    Items.scrap, new BasicBulletType() {{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        damage = 100f;
                        splashDamageRadius = 15f;
                        splashDamage = 150f;
                        speed = 15f;

                        ammoMultiplier = 10;

                        fragBullets = 2;
                        fragBullet = new BasicBulletType() {{
                            width = 7f;
                            height = 9f;
                            lifetime = 60f;
                            damage = 100f;
                            splashDamageRadius = 15f;
                            splashDamage = 150f;
                            speed = 15f;

                            fragBullets = 2;
                            fragBullet = new BasicBulletType() {{
                                width = 7f;
                                height = 9f;
                                lifetime = 60f;
                                damage = 100f;
                                splashDamageRadius = 15f;
                                splashDamage = 150f;
                                speed = 15f;

                                fragBullets = 2;
                                fragBullet = new BasicBulletType() {{
                                    width = 7f;
                                    height = 9f;
                                    lifetime = 60f;
                                    damage = 100f;
                                    splashDamageRadius = 15f;
                                    splashDamage = 150f;
                                    speed = 15f;

                                    fragBullets = 2;
                                    fragBullet = new BasicBulletType() {{
                                        width = 7f;
                                        height = 9f;
                                        lifetime = 60f;
                                        damage = 100f;
                                        splashDamageRadius = 15f;
                                        splashDamage = 150f;
                                        speed = 15f;
                                    }};
                                }};
                            }};
                        }};
                    }}
            );

            shoot = new ShootAlternate(3.5f);

            recoils = 2;
            drawer = new DrawTurret() {{
                for (int i = 0; i < 2; i++) {
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")) {{
                        progress = PartProgress.recoil;
                        recoilIndex = f;
                        under = true;
                        moves.add(new PartMove(PartProgress.recoil, 0f, -1.5f, 0f));
                    }});
                }
            }};

            recoil = 0.5f;
            shootY = 3f;
            reload = 20f;
            range = 11000;
            shootCone = 15f;
            ammoUseEffect = Fx.casing1;
            health = 250;
            inaccuracy = 2f;
            rotateSpeed = 10f;
            coolant = consumeCoolant(0.1f);
            researchCostMultiplier = 0.05f;

            limitRange();
        }};

        exDuo = new ExItemTurret("ex-duo") {{
            requirements(Category.turret, with(Items.copper, 35));
            ammo(
                    Items.copper, new BasicBulletType(2.5f, 9) {{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 2;
                    }},
                    Items.graphite, new BasicBulletType(3.5f, 18) {{
                        width = 9f;
                        height = 12f;
                        reloadMultiplier = 0.6f;
                        ammoMultiplier = 4;
                        lifetime = 60f;
                    }},
                    Items.silicon, new BasicBulletType(3f, 12) {{
                        width = 7f;
                        height = 9f;
                        homingPower = 0.1f;
                        reloadMultiplier = 1.5f;
                        ammoMultiplier = 5;
                        lifetime = 60f;
                    }}
            );

            shoot = new ShootAlternate(3.5f);

            recoils = 2;
            drawer = new DrawTurret() {{
                for (int i = 0; i < 2; i++) {
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")) {{
                        progress = PartProgress.recoil;
                        recoilIndex = f;
                        under = true;
                        moves.add(new PartMove(PartProgress.recoil, 0f, -1.5f, 0f));
                    }});
                }
            }};

            recoil = 0.5f;
            shootY = 3f;
            reload = 20f;
            range = 110;
            shootCone = 15f;
            ammoUseEffect = Fx.casing1;
            health = 250;
            inaccuracy = 2f;
            rotateSpeed = 10f;
            coolant = consumeCoolant(0.1f);
            researchCostMultiplier = 0.05f;

            limitRange();
        }};

        exScatter = new ExItemTurret("ex-scatter") {{
            requirements(Category.turret, with(Items.copper, 85, Items.lead, 45));
            ammo(
                    Items.scrap, new FlakBulletType(4f, 3) {{
                        lifetime = 60f;
                        ammoMultiplier = 5f;
                        shootEffect = Fx.shootSmall;
                        reloadMultiplier = 0.5f;
                        width = 6f;
                        height = 8f;
                        hitEffect = Fx.flakExplosion;
                        splashDamage = 22f * 1.5f;
                        splashDamageRadius = 24f;
                    }},
                    Items.lead, new FlakBulletType(4.2f, 3) {{
                        lifetime = 60f;
                        ammoMultiplier = 4f;
                        shootEffect = Fx.shootSmall;
                        width = 6f;
                        height = 8f;
                        hitEffect = Fx.flakExplosion;
                        splashDamage = 27f * 1.5f;
                        splashDamageRadius = 15f;
                    }},
                    Items.metaglass, new FlakBulletType(4f, 3) {{
                        lifetime = 60f;
                        ammoMultiplier = 5f;
                        shootEffect = Fx.shootSmall;
                        reloadMultiplier = 0.8f;
                        width = 6f;
                        height = 8f;
                        hitEffect = Fx.flakExplosion;
                        splashDamage = 30f * 1.5f;
                        splashDamageRadius = 20f;
                        fragBullets = 6;
                        fragBullet = new BasicBulletType(3f, 5) {{
                            width = 5f;
                            height = 12f;
                            shrinkY = 1f;
                            lifetime = 20f;
                            backColor = Pal.gray;
                            frontColor = Color.white;
                            despawnEffect = Fx.none;
                            collidesGround = false;
                        }};
                    }}
            );

            drawer = new DrawTurret() {{
                parts.add(new RegionPart("-mid") {{
                    progress = PartProgress.recoil;
                    under = false;
                    moveY = -1.25f;
                }});
            }};

            reload = 18f;
            range = 220f;
            size = 2;
            targetGround = false;

            shoot.shotDelay = 5f;
            shoot.shots = 2;

            recoil = 1f;
            rotateSpeed = 15f;
            inaccuracy = 17f;
            shootCone = 35f;

            scaledHealth = 200;
            shootSound = Sounds.shootSnap;
            coolant = consumeCoolant(0.2f);
            researchCostMultiplier = 0.05f;

            limitRange(2);
        }};

        exScorch = new ExItemTurret("ex-scorch") {{
            requirements(Category.turret, with(Items.copper, 25, Items.graphite, 22));
            ammo(
                    Items.coal, new BulletType(3.35f, 17f) {{
                        ammoMultiplier = 3f;
                        hitSize = 7f;
                        lifetime = 18f;
                        pierce = true;
                        collidesAir = false;
                        statusDuration = 60f * 4;
                        shootEffect = Fx.shootSmallFlame;
                        hitEffect = Fx.hitFlameSmall;
                        despawnEffect = Fx.none;
                        status = StatusEffects.burning;
                        keepVelocity = false;
                        hittable = false;
                    }},
                    Items.pyratite, new BulletType(4f, 60f) {{
                        ammoMultiplier = 6f;
                        hitSize = 7f;
                        lifetime = 18f;
                        pierce = true;
                        collidesAir = false;
                        statusDuration = 60f * 10;
                        shootEffect = Fx.shootPyraFlame;
                        hitEffect = Fx.hitFlameSmall;
                        despawnEffect = Fx.none;
                        status = StatusEffects.burning;
                        hittable = false;
                    }}
            );
            recoil = 0f;
            reload = 6f;
            coolantMultiplier = 1.5f;
            range = 60f;
            shootCone = 50f;
            targetAir = false;
            ammoUseEffect = Fx.none;
            health = 400;
            shootSound = Sounds.flame;
            coolant = consumeCoolant(0.1f);
        }};

        exHail = new ExItemTurret("ex-hail") {{
            requirements(Category.turret, with(Items.copper, 40, Items.graphite, 17));
            ammo(
                    Items.graphite, new ArtilleryBulletType(3f, 20) {{
                        knockback = 0.8f;
                        lifetime = 80f;
                        width = height = 11f;
                        collidesTiles = false;
                        splashDamageRadius = 25f * 0.75f;
                        splashDamage = 33f;
                    }},
                    Items.silicon, new ArtilleryBulletType(3f, 20) {{
                        knockback = 0.8f;
                        lifetime = 80f;
                        width = height = 11f;
                        collidesTiles = false;
                        splashDamageRadius = 25f * 0.75f;
                        splashDamage = 33f;
                        reloadMultiplier = 1.2f;
                        ammoMultiplier = 3f;
                        homingPower = 0.08f;
                        homingRange = 50f;
                    }},
                    Items.pyratite, new ArtilleryBulletType(3f, 25) {{
                        hitEffect = Fx.blastExplosion;
                        knockback = 0.8f;
                        lifetime = 80f;
                        width = height = 13f;
                        collidesTiles = false;
                        splashDamageRadius = 25f * 0.75f;
                        splashDamage = 45f;
                        status = StatusEffects.burning;
                        statusDuration = 60f * 12f;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        makeFire = true;
                        trailEffect = Fx.incendTrail;
                        ammoMultiplier = 4f;
                    }}
            );
            targetAir = false;
            reload = 60f;
            recoil = 2f;
            range = 235f;
            inaccuracy = 1f;
            shootCone = 10f;
            health = 260;
            shootSound = Sounds.bang;
            coolant = consumeCoolant(0.1f);
            limitRange(0f);
        }};

        exWave = new ExLiquidTurret("ex-wave") {{
            requirements(Category.turret, with(Items.metaglass, 45, Items.lead, 75, Items.copper, 25));
            ammo(
                    Liquids.water, new LiquidBulletType(Liquids.water) {{
                        knockback = 0.7f;
                        drag = 0.01f;
                        layer = Layer.bullet - 2f;
                    }},
                    Liquids.slag, new LiquidBulletType(Liquids.slag) {{
                        damage = 4;
                        drag = 0.01f;
                    }},
                    Liquids.cryofluid, new LiquidBulletType(Liquids.cryofluid) {{
                        drag = 0.01f;
                    }},
                    Liquids.oil, new LiquidBulletType(Liquids.oil) {{
                        drag = 0.01f;
                        layer = Layer.bullet - 2f;
                    }},
                    ExLiquids.helium, new LiquidBulletType(ExLiquids.helium) {{
                        knockback = 1f;
                        damage = 0.1f;
                        drag = 0.01f;
                        layer = Layer.bullet - 2f;
                    }},
                    ExLiquids.liquidHelium, new LiquidBulletType(ExLiquids.liquidHelium) {{
                        knockback = 0.35f;
                        damage = 729.53f;
                        drag = 0.01f;
                        layer = Layer.bullet - 2f;
                    }}
            );
            size = 2;
            recoil = 0f;
            reload = 3f;
            inaccuracy = 5f;
            shootCone = 50f;
            liquidCapacity = 10f;
            shootEffect = Fx.shootLiquid;
            range = 110f;
            scaledHealth = 250;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};

        //TODO these may work in space, but what's the point?
        exLancer = new ExPowerTurret("ex-lancer") {{
            requirements(Category.turret, with(Items.copper, 60, Items.lead, 70, Items.silicon, 60, Items.titanium, 30));
            range = 165f;

            shoot.firstShotDelay = 40f;

            recoil = 2f;
            reload = 80f;
            shake = 2f;
            shootEffect = Fx.lancerLaserShoot;
            smokeEffect = Fx.none;
            heatColor = Color.red;
            size = 2;
            scaledHealth = 280;
            targetAir = false;
            moveWhileCharging = false;
            accurateDelay = false;
            shootSound = Sounds.laser;
            coolant = consumeCoolant(0.2f);

            consumePower(6f);

            shootType = new LaserBulletType(140) {{
                colors = new Color[]{Pal.lancerLaser.cpy().a(0.4f), Pal.lancerLaser, Color.white};
                //TODO merge
                chargeEffect = new MultiEffect(Fx.lancerLaserCharge, Fx.lancerLaserChargeBegin);

                buildingDamageMultiplier = 0.25f;
                hitEffect = Fx.hitLancer;
                hitSize = 4;
                lifetime = 16f;
                drawSize = 400f;
                collidesAir = false;
                length = 173f;
                ammoMultiplier = 1f;
                pierceCap = 4;
            }};
        }};

        exArc = new ExPowerTurret("ex-arc") {{
            requirements(Category.turret, with(Items.copper, 50, Items.lead, 50));
            shootType = new LightningBulletType() {{
                damage = 20;
                lightningLength = 25;
                collidesAir = false;
                ammoMultiplier = 1f;

                //for visual stats only.
                buildingDamageMultiplier = 0.25f;

                lightningType = new BulletType(0.0001f, 0f) {{
                    lifetime = Fx.lightning.lifetime;
                    hitEffect = Fx.hitLancer;
                    despawnEffect = Fx.none;
                    status = StatusEffects.shocked;
                    statusDuration = 10f;
                    hittable = false;
                    lightColor = Color.white;
                    collidesAir = false;
                    buildingDamageMultiplier = 0.25f;
                }};
            }};
            reload = 35f;
            shootCone = 40f;
            rotateSpeed = 8f;
            targetAir = false;
            range = 90f;
            shootEffect = Fx.lightningShoot;
            heatColor = Color.red;
            recoil = 1f;
            size = 1;
            health = 260;
            shootSound = Sounds.spark;
            consumePower(3.3f);
            coolant = consumeCoolant(0.1f);
        }};

        exParallax = new ExTractorBeamTurret("ex-parallax") {{
            requirements(Category.turret, with(Items.silicon, 120, Items.titanium, 90, Items.graphite, 30));

            hasPower = true;
            size = 2;
            force = 12f;
            scaledForce = 6f;
            range = 240f;
            damage = 0.3f;
            scaledHealth = 160;
            rotateSpeed = 10;

            consumePower(3f);
        }};

        exSwarmer = new ExItemTurret("ex-swarmer") {{
            requirements(Category.turret, with(Items.graphite, 35, Items.titanium, 35, Items.plastanium, 45, Items.silicon, 30));
            ammo(
                    Items.blastCompound, new MissileBulletType(3.7f, 10) {{
                        width = 8f;
                        height = 8f;
                        shrinkY = 0f;
                        splashDamageRadius = 30f;
                        splashDamage = 30f * 1.5f;
                        ammoMultiplier = 5f;
                        hitEffect = Fx.blastExplosion;
                        despawnEffect = Fx.blastExplosion;

                        status = StatusEffects.blasted;
                        statusDuration = 60f;
                    }},
                    Items.pyratite, new MissileBulletType(3.7f, 12) {{
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        width = 7f;
                        height = 8f;
                        shrinkY = 0f;
                        homingPower = 0.08f;
                        splashDamageRadius = 20f;
                        splashDamage = 30f * 1.5f;
                        makeFire = true;
                        ammoMultiplier = 5f;
                        hitEffect = Fx.blastExplosion;
                        status = StatusEffects.burning;
                    }},
                    Items.surgeAlloy, new MissileBulletType(3.7f, 18) {{
                        width = 8f;
                        height = 8f;
                        shrinkY = 0f;
                        splashDamageRadius = 25f;
                        splashDamage = 25f * 1.4f;
                        hitEffect = Fx.blastExplosion;
                        despawnEffect = Fx.blastExplosion;
                        ammoMultiplier = 4f;
                        lightningDamage = 10;
                        lightning = 2;
                        lightningLength = 10;
                    }}
            );

            shoot = new ShootAlternate() {{
                shots = 4;
                barrels = 3;
                spread = 3.5f;
                shotDelay = 5f;
            }};

            shootY = 7f;
            reload = 30f;
            inaccuracy = 10f;
            range = 240f;
            consumeAmmoOnce = false;
            size = 2;
            scaledHealth = 300;
            shootSound = Sounds.missile;
            envEnabled |= Env.space;

            limitRange(5f);
            coolant = consumeCoolant(0.3f);
        }};

        exSalvo = new ExItemTurret("ex-salvo") {{
            requirements(Category.turret, with(Items.copper, 100, Items.graphite, 80, Items.titanium, 50));
            ammo(
                    Items.copper, new BasicBulletType(2.5f, 11) {{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 2;
                    }},
                    Items.graphite, new BasicBulletType(3.5f, 20) {{
                        width = 9f;
                        height = 12f;
                        reloadMultiplier = 0.6f;
                        ammoMultiplier = 4;
                        lifetime = 60f;
                    }},
                    Items.pyratite, new BasicBulletType(3.2f, 18) {{
                        width = 10f;
                        height = 12f;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        status = StatusEffects.burning;
                        hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);

                        ammoMultiplier = 5;

                        splashDamage = 12f;
                        splashDamageRadius = 22f;

                        makeFire = true;
                        lifetime = 60f;
                    }},
                    Items.silicon, new BasicBulletType(3f, 15, "bullet") {{
                        width = 7f;
                        height = 9f;
                        homingPower = 0.1f;
                        reloadMultiplier = 1.5f;
                        ammoMultiplier = 5;
                        lifetime = 60f;
                    }},
                    Items.thorium, new BasicBulletType(4f, 29, "bullet") {{
                        width = 10f;
                        height = 13f;
                        shootEffect = Fx.shootBig;
                        smokeEffect = Fx.shootBigSmoke;
                        ammoMultiplier = 4;
                        lifetime = 60f;
                    }}
            );

            drawer = new DrawTurret() {{
                parts.add(new RegionPart("-side") {{
                    progress = PartProgress.warmup;
                    moveX = 0.6f;
                    moveRot = -15f;
                    mirror = true;
                    layerOffset = 0.001f;
                    moves.add(new PartMove(PartProgress.recoil, 0.5f, -0.5f, -8f));
                }}, new RegionPart("-barrel") {{
                    progress = PartProgress.recoil;
                    moveY = -2.5f;
                }});
            }};

            size = 2;
            range = 190f;
            reload = 31f;
            consumeAmmoOnce = false;
            ammoEjectBack = 3f;
            recoil = 0f;
            shake = 1f;
            shoot.shots = 4;
            shoot.shotDelay = 3f;

            ammoUseEffect = Fx.casing2;
            scaledHealth = 240;
            shootSound = Sounds.shootBig;

            limitRange();
            coolant = consumeCoolant(0.2f);
        }};

        exSegment = new ExPointDefenseTurret("ex-segment") {{
            requirements(Category.turret, with(Items.silicon, 130, Items.thorium, 80, Items.phaseFabric, 40, Items.titanium, 40));

            scaledHealth = 250;
            range = 180f;
            hasPower = true;
            consumePower(8f);
            size = 2;
            shootLength = 5f;
            bulletDamage = 30f;
            reload = 8f;
            envEnabled |= Env.space;
        }};

        exTsunami = new ExLiquidTurret("ex-tsunami") {{
            requirements(Category.turret, with(Items.metaglass, 100, Items.lead, 400, Items.titanium, 250, Items.thorium, 100));
            ammo(
                    Liquids.water, new LiquidBulletType(Liquids.water) {{
                        lifetime = 49f;
                        speed = 4f;
                        knockback = 1.7f;
                        puddleSize = 8f;
                        orbSize = 4f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 4f;
                        damage = 0.2f;
                        layer = Layer.bullet - 2f;
                    }},
                    Liquids.slag, new LiquidBulletType(Liquids.slag) {{
                        lifetime = 49f;
                        speed = 4f;
                        knockback = 1.3f;
                        puddleSize = 8f;
                        orbSize = 4f;
                        damage = 4.75f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 4f;
                    }},
                    Liquids.cryofluid, new LiquidBulletType(Liquids.cryofluid) {{
                        lifetime = 49f;
                        speed = 4f;
                        knockback = 1.3f;
                        puddleSize = 8f;
                        orbSize = 4f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 4f;
                        damage = 0.2f;
                    }},
                    Liquids.oil, new LiquidBulletType(Liquids.oil) {{
                        lifetime = 49f;
                        speed = 4f;
                        knockback = 1.3f;
                        puddleSize = 8f;
                        orbSize = 4f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 4f;
                        damage = 0.2f;
                        layer = Layer.bullet - 2f;
                    }},
                    ExLiquids.helium, new LiquidBulletType(ExLiquids.helium) {{
                        lifetime = 49f;
                        speed = 4f;
                        knockback = 3.5f;
                        puddleSize = 8f;
                        orbSize = 4f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 4f;
                        damage = 0.05f;
                        layer = Layer.bullet - 2f;
                    }},
                    ExLiquids.liquidHelium, new LiquidBulletType(ExLiquids.liquidHelium) {{
                        lifetime = 49f;
                        speed = 4f;
                        knockback = 0.45f;
                        puddleSize = 8f;
                        orbSize = 4f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 4f;
                        damage = 1385.43f;
                        layer = Layer.bullet - 2f;
                    }}
            );
            size = 3;
            reload = 3f;
            shoot.shots = 2;
            velocityRnd = 0.1f;
            inaccuracy = 4f;
            recoil = 1f;
            shootCone = 45f;
            liquidCapacity = 40f;
            shootEffect = Fx.shootLiquid;
            range = 190f;
            scaledHealth = 250;
            flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
        }};

        exFuse = new ExItemTurret("ex-fuse") {{
            requirements(Category.turret, with(Items.copper, 225, Items.graphite, 225, Items.thorium, 100));

            reload = 35f;
            shake = 4f;
            range = 90f;
            recoil = 5f;

            shoot = new ShootSpread(3, 20f);

            shootCone = 30;
            size = 3;
            envEnabled |= Env.space;

            scaledHealth = 220;
            shootSound = Sounds.shotgun;
            coolant = consumeCoolant(0.3f);

            float brange = range + 10f;

            ammo(
                    Items.titanium, new ShrapnelBulletType() {{
                        length = brange;
                        damage = 66f;
                        ammoMultiplier = 4f;
                        width = 17f;
                        reloadMultiplier = 1.3f;
                    }},
                    Items.thorium, new ShrapnelBulletType() {{
                        length = brange;
                        damage = 105f;
                        ammoMultiplier = 5f;
                        toColor = Pal.thoriumPink;
                        shootEffect = smokeEffect = Fx.thoriumShoot;
                    }}
            );
        }};

        exRipple = new ExItemTurret("ex-ripple") {{
            requirements(Category.turret, with(Items.copper, 150, Items.graphite, 135, Items.titanium, 60));
            ammo(
                    Items.graphite, new ArtilleryBulletType(3f, 20) {{
                        knockback = 0.8f;
                        lifetime = 80f;
                        width = height = 11f;
                        collidesTiles = false;
                        splashDamageRadius = 25f * 0.75f;
                        splashDamage = 33f;
                    }},
                    Items.silicon, new ArtilleryBulletType(3f, 20) {{
                        knockback = 0.8f;
                        lifetime = 80f;
                        width = height = 11f;
                        collidesTiles = false;
                        splashDamageRadius = 25f * 0.75f;
                        splashDamage = 33f;
                        reloadMultiplier = 1.2f;
                        ammoMultiplier = 3f;
                        homingPower = 0.08f;
                        homingRange = 50f;
                    }},
                    Items.pyratite, new ArtilleryBulletType(3f, 24) {{
                        hitEffect = Fx.blastExplosion;
                        knockback = 0.8f;
                        lifetime = 80f;
                        width = height = 13f;
                        collidesTiles = false;
                        splashDamageRadius = 25f * 0.75f;
                        splashDamage = 45f;
                        status = StatusEffects.burning;
                        statusDuration = 60f * 12f;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        makeFire = true;
                        trailEffect = Fx.incendTrail;
                        ammoMultiplier = 4f;
                    }},
                    Items.blastCompound, new ArtilleryBulletType(2f, 20, "shell") {{
                        hitEffect = Fx.blastExplosion;
                        knockback = 0.8f;
                        lifetime = 80f;
                        width = height = 14f;
                        collidesTiles = false;
                        ammoMultiplier = 4f;
                        splashDamageRadius = 45f * 0.75f;
                        splashDamage = 55f;
                        backColor = Pal.missileYellowBack;
                        frontColor = Pal.missileYellow;

                        status = StatusEffects.blasted;
                    }},
                    Items.plastanium, new ArtilleryBulletType(3.4f, 20, "shell") {{
                        hitEffect = Fx.plasticExplosion;
                        knockback = 1f;
                        lifetime = 80f;
                        width = height = 13f;
                        collidesTiles = false;
                        splashDamageRadius = 35f * 0.75f;
                        splashDamage = 45f;
                        fragBullet = new BasicBulletType(2.5f, 10, "bullet") {{
                            width = 10f;
                            height = 12f;
                            shrinkY = 1f;
                            lifetime = 15f;
                            backColor = Pal.plastaniumBack;
                            frontColor = Pal.plastaniumFront;
                            despawnEffect = Fx.none;
                            collidesAir = false;
                        }};
                        fragBullets = 10;
                        backColor = Pal.plastaniumBack;
                        frontColor = Pal.plastaniumFront;
                    }}
            );

            targetAir = false;
            size = 3;
            shoot.shots = 4;
            inaccuracy = 12f;
            reload = 60f;
            ammoEjectBack = 5f;
            ammoUseEffect = Fx.casing3Double;
            ammoPerShot = 2;
            velocityRnd = 0.2f;
            recoil = 6f;
            shake = 2f;
            range = 290f;
            minRange = 50f;
            coolant = consumeCoolant(0.3f);

            scaledHealth = 130;
            shootSound = Sounds.artillery;
        }};

        exCyclone = new ExItemTurret("ex-cyclone") {{
            requirements(Category.turret, with(Items.copper, 200, Items.titanium, 125, Items.plastanium, 80));
            ammo(
                    Items.metaglass, new FlakBulletType(4f, 6) {{
                        ammoMultiplier = 2f;
                        shootEffect = Fx.shootSmall;
                        reloadMultiplier = 0.8f;
                        width = 6f;
                        height = 8f;
                        hitEffect = Fx.flakExplosion;
                        splashDamage = 45f;
                        splashDamageRadius = 25f;
                        fragBullet = new BasicBulletType(3f, 12, "bullet") {{
                            width = 5f;
                            height = 12f;
                            shrinkY = 1f;
                            lifetime = 20f;
                            backColor = Pal.gray;
                            frontColor = Color.white;
                            despawnEffect = Fx.none;
                        }};
                        fragBullets = 4;
                        explodeRange = 20f;
                        collidesGround = true;
                    }},
                    Items.blastCompound, new FlakBulletType(4f, 8) {{
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 5f;
                        splashDamage = 45f;
                        splashDamageRadius = 60f;
                        collidesGround = true;

                        status = StatusEffects.blasted;
                        statusDuration = 60f;
                    }},
                    Items.plastanium, new FlakBulletType(4f, 8) {{
                        ammoMultiplier = 4f;
                        splashDamageRadius = 40f;
                        splashDamage = 37.5f;
                        fragBullet = new BasicBulletType(2.5f, 12, "bullet") {{
                            width = 10f;
                            height = 12f;
                            shrinkY = 1f;
                            lifetime = 15f;
                            backColor = Pal.plastaniumBack;
                            frontColor = Pal.plastaniumFront;
                            despawnEffect = Fx.none;
                        }};
                        fragBullets = 6;
                        hitEffect = Fx.plasticExplosion;
                        frontColor = Pal.plastaniumFront;
                        backColor = Pal.plastaniumBack;
                        shootEffect = Fx.shootBig;
                        collidesGround = true;
                        explodeRange = 20f;
                    }},
                    Items.surgeAlloy, new FlakBulletType(4.5f, 13) {{
                        ammoMultiplier = 5f;
                        splashDamage = 50f * 1.5f;
                        splashDamageRadius = 38f;
                        lightning = 2;
                        lightningLength = 7;
                        shootEffect = Fx.shootBig;
                        collidesGround = true;
                        explodeRange = 20f;
                    }}
            );
            shootY = 10f;

            shoot = new ShootBarrel() {{
                barrels = new float[]{
                        0f, 1f, 0f,
                        3f, 0f, 0f,
                        -3f, 0f, 0f,
                };
            }};

            recoils = 3;
            drawer = new DrawTurret() {{
                for (int i = 3; i > 0; i--) {
                    int f = i;
                    parts.add(new RegionPart("-barrel-" + i) {{
                        progress = PartProgress.recoil;
                        recoilIndex = f - 1;
                        under = true;
                        moveY = -2f;
                    }});
                }
            }};

            reload = 8f;
            range = 200f;
            size = 3;
            recoil = 1.5f;
            recoilTime = 10;
            rotateSpeed = 10f;
            inaccuracy = 10f;
            shootCone = 30f;
            shootSound = Sounds.shootSnap;
            coolant = consumeCoolant(0.3f);

            scaledHealth = 145;
            limitRange();
        }};

        exForeshadow = new ExItemTurret("ex-foreshadow") {{
            float brange = range = 500f;

            requirements(Category.turret, with(Items.copper, 1000, Items.metaglass, 600, Items.surgeAlloy, 300, Items.plastanium, 200, Items.silicon, 600));
            ammo(
                    Items.surgeAlloy, new PointBulletType() {{
                        shootEffect = Fx.instShoot;
                        hitEffect = Fx.instHit;
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = Fx.instTrail;
                        despawnEffect = Fx.instBomb;
                        trailSpacing = 20f;
                        damage = 1350;
                        buildingDamageMultiplier = 0.2f;
                        speed = brange;
                        hitShake = 6f;
                        ammoMultiplier = 1f;
                    }}
            );

            maxAmmo = 40;
            ammoPerShot = 5;
            rotateSpeed = 2f;
            reload = 200f;
            ammoUseEffect = Fx.casing3Double;
            recoil = 5f;
            cooldownTime = reload;
            shake = 4f;
            size = 4;
            shootCone = 2f;
            shootSound = Sounds.railgun;
            unitSort = UnitSorts.strongest;
            envEnabled |= Env.space;

            coolantMultiplier = 0.4f;
            scaledHealth = 150;

            coolant = consumeCoolant(1f);
            consumePower(10f);
        }};

        exSpectre = new ExItemTurret("ex-spectre") {{
            requirements(Category.turret, with(Items.copper, 900, Items.graphite, 300, Items.surgeAlloy, 250, Items.plastanium, 175, Items.thorium, 250));
            ammo(
                    Items.graphite, new BasicBulletType(7.5f, 50) {{
                        hitSize = 4.8f;
                        width = 15f;
                        height = 21f;
                        shootEffect = Fx.shootBig;
                        ammoMultiplier = 4;
                        reloadMultiplier = 1.7f;
                        knockback = 0.3f;
                    }},
                    Items.thorium, new BasicBulletType(8f, 80) {{
                        hitSize = 5;
                        width = 16f;
                        height = 23f;
                        shootEffect = Fx.shootBig;
                        pierceCap = 2;
                        pierceBuilding = true;
                        knockback = 0.7f;
                    }},
                    Items.pyratite, new BasicBulletType(7f, 70) {{
                        hitSize = 5;
                        width = 16f;
                        height = 21f;
                        frontColor = Pal.lightishOrange;
                        backColor = Pal.lightOrange;
                        status = StatusEffects.burning;
                        hitEffect = new MultiEffect(Fx.hitBulletSmall, Fx.fireHit);
                        shootEffect = Fx.shootBig;
                        makeFire = true;
                        pierceCap = 2;
                        pierceBuilding = true;
                        knockback = 0.6f;
                        ammoMultiplier = 3;
                        splashDamage = 20f;
                        splashDamageRadius = 25f;
                    }}
            );
            reload = 7f;
            recoilTime = reload * 2f;
            coolantMultiplier = 0.5f;
            ammoUseEffect = Fx.casing3;
            range = 260f;
            inaccuracy = 3f;
            recoil = 3f;
            shoot = new ShootAlternate(8f);
            shake = 2f;
            size = 4;
            shootCone = 24f;
            shootSound = Sounds.shootBig;

            scaledHealth = 160;
            coolant = consumeCoolant(1f);

            limitRange();
        }};

        exMeltdown = new ExLaserTurret("ex-meltdown") {{
            requirements(Category.turret, with(Items.copper, 1200, Items.lead, 350, Items.graphite, 300, Items.surgeAlloy, 325, Items.silicon, 325));
            shootEffect = Fx.shootBigSmoke2;
            shootCone = 40f;
            recoil = 4f;
            size = 4;
            shake = 2f;
            range = 195f;
            reload = 90f;
            firingMoveFract = 0.5f;
            shootDuration = 230f;
            shootSound = Sounds.laserbig;
            loopSound = Sounds.beam;
            loopSoundVolume = 2f;
            envEnabled |= Env.space;

            shootType = new ContinuousLaserBulletType(78) {{
                length = 200f;
                hitEffect = Fx.hitMeltdown;
                hitColor = Pal.meltdownHit;
                status = StatusEffects.melting;
                drawSize = 420f;

                incendChance = 0.4f;
                incendSpread = 5f;
                incendAmount = 1;
                ammoMultiplier = 1f;
            }};

            scaledHealth = 200;
            coolant = consumeCoolant(0.5f);
            consumePower(17f);
        }};

        ballLightning = new ExItemTurret("ball-lightning") {{
            requirements(Category.turret, with(Items.copper, 1));

            ammo(
                    ExItems.fullBattery, new BasicBulletType() {
                        {
                            width = 14.5f;
                            height = 14.5f;

                            speed = 0.8f;
                            lifetime = 3000f;
                            ammoMultiplier = 1;

                            intervalDelay = 1f;
                            intervalSpread = 300f;
                            intervalBullets = 60;
                            intervalBullet = new LightningBulletType() {{
                                damage = 150;
                                lightningLength = 8;
                                collidesAir = false;
                                ammoMultiplier = 1f;

                                //for visual stats only.
                                buildingDamageMultiplier = 0.9f;

                                lightningType = new BulletType(0.0001f, 0f) {{
                                    lifetime = Fx.lightning.lifetime;
                                    hitEffect = Fx.hitLancer;
                                    despawnEffect = Fx.none;
                                    status = StatusEffects.blasted;
                                    statusDuration = 10f;
                                    hittable = false;
                                    lightColor = Color.white;
                                    collidesAir = false;
                                    buildingDamageMultiplier = 0.9f;
                                }};
                            }};

                            fragBullets = 5;
                            fragBullet = new BulletType(0.0001f, 0f) {{
                                fragBullets = 15;
                                fragBullet = new LightningBulletType() {{
                                    damage = 350;
                                    lightningLength = 40;
                                    collidesAir = false;
                                    ammoMultiplier = 1f;

                                    lightningType = new BulletType(0.0001f, 0f) {{
                                        lifetime = Fx.lightning.lifetime;
                                        hitEffect = Fx.hitLancer;
                                        despawnEffect = Fx.none;
                                        status = StatusEffects.blasted;
                                        statusDuration = 35f;
                                        hittable = false;
                                        lightColor = Color.white;
                                        collidesAir = false;
                                    }};
                                }};
                            }};

                            damage = 850f;

                            splashDamageRadius = 38f;
                            splashDamage = 705f;

                            homingDelay = 1f;
                            homingRange = 6000f;
                            homingPower = 0.081f;
                        }

                        @Override
                        public void draw(Bullet b) {
                            Color c = Color.valueOf("cff2ff");

                            Draw.color(c);
                            Fill.circle(b.x, b.y, 7.4f);
                            Fill.light(b.x, b.y, 50, 8.3f, 0, c, c);

                            Draw.reset();
                        }
                    }
            );

            range = 254f;

            maxAmmo = 40;
            ammoPerShot = 8;
            rotateSpeed = 15f;
            reload = 150f;
            ammoUseEffect = Fx.casing3Double;
            recoil = 2.5f;
            shake = 24f;
            size = 5;
            shootCone = 1f;
            shootSound = Sounds.spark;
            envEnabled |= Env.space;

            coolantMultiplier = 0.4f;
            scaledHealth = 210f;

            coolant = consumeCoolant(0.8f);
            consumePower(9f);
        }};

        ion = new ExItemTurret("ion") {
            {
                requirements(Category.turret, with(Items.copper, 1));

                ammo(
                        ExItems.fullBattery, new BasicBulletType() {
                            {
                                pierce = true;
                                damage = 50f;
                                speed = 23f;
                                width = 0.15f;
                                height = 16f;
                                lifetime = 1000f;
                                ammoMultiplier = 1000;
                            }

                            @Override
                            public void draw(Bullet b) {
                                Draw.color();

                                float
                                        x1 = b.x - 8f * Mathf.sinDeg(b.rotation()),
                                        y1 = b.y - 8f * Mathf.sinDeg(b.rotation()),
                                        x2 = b.x + 8f * Mathf.sinDeg(b.rotation()),
                                        y2 = b.y + 8f * Mathf.sinDeg(b.rotation());

                                Drawf.line(Color.valueOf("ceeaf4"), x1, y1, x2, y2);

                                Draw.reset();
                            }
                        }
                );

                range = 254f;

                maxAmmo = 4000;
                ammoPerShot = 5;
                rotateSpeed = 15f;
                reload = 1f;
                ammoUseEffect = Fx.casing3Double;
                recoil = 2.5f;
                shake = 24f;
                size = 2;
                shootCone = 1f;
                shootY = 16f + 5.1f;
                envEnabled |= Env.space;

                coolantMultiplier = 0.4f;
                scaledHealth = 59f;

                coolant = consumeCoolant(0.8f);
                consumePower(1f);
            }

            public class ExItemTurretBuild extends ItemTurretBuild {
                @Override
                protected void shoot(BulletType type) {
                    float X = Mathf.random(-5f, 5f);

                    float
                            bulletX = x + Angles.trnsx(rotation - 90, X, shootY),
                            bulletY = y + Angles.trnsy(rotation - 90, X, shootY);

                    if(shoot.firstShotDelay > 0){
                        chargeSound.at(bulletX, bulletY, Mathf.random(soundPitchMin, soundPitchMax));
                        type.chargeEffect.at(bulletX, bulletY, rotation);
                    }

                    shoot.shoot(barrelCounter, (xOffset, yOffset, angle, delay, mover) -> {
                        queuedBullets++;
                        if(delay > 0f){
                            Time.run(delay, () -> bullet(type, xOffset, yOffset, angle, mover));
                        }else{
                            bullet(type, xOffset, yOffset, angle, mover);
                        }
                    }, () -> barrelCounter++);

                    if(consumeAmmoOnce){
                        useAmmo();
                    }
                }
            }
        };
    }

    public static void loadDrill() {
        exMechanicalDrill = new ExDrill("ex-mechanical-drill") {{
            requirements(Category.production, with(Items.copper, 12));
            tier = 2;
            drillTime = 600;
            size = 2;
            //mechanical drill doesn't work in space
            envEnabled ^= Env.space;
            researchCost = with(Items.copper, 10);

            consumeLiquid(Liquids.water, 0.05f).boost();
        }};

        exPneumaticDrill = new ExDrill("ex-pneumatic-drill") {{
            requirements(Category.production, with(Items.copper, 18, Items.graphite, 10));
            tier = 3;
            drillTime = 400;
            size = 2;

            consumeLiquid(Liquids.water, 0.06f).boost();
        }};

        exLaserDrill = new ExDrill("ex-laser-drill") {{
            requirements(Category.production, with(Items.copper, 35, Items.graphite, 30, Items.silicon, 30, Items.titanium, 20));
            drillTime = 280;
            size = 3;
            hasPower = true;
            tier = 4;
            updateEffect = Fx.pulverizeMedium;
            drillEffect = Fx.mineBig;

            consumePower(1.10f);
            consumeLiquid(Liquids.water, 0.08f).boost();
        }};

        exBlastDrill = new ExDrill("ex-blast-drill") {{
            requirements(Category.production, with(Items.copper, 65, Items.silicon, 60, Items.titanium, 50, Items.thorium, 75));
            drillTime = 280;
            size = 4;
            drawRim = true;
            hasPower = true;
            tier = 5;
            updateEffect = Fx.pulverizeRed;
            updateEffectChance = 0.03f;
            drillEffect = Fx.mineHuge;
            rotateSpeed = 6f;
            warmupSpeed = 0.01f;
            itemCapacity = 20;

            //more than the laser drill
            liquidBoostIntensity = 1.8f;

            consumePower(3f);
            consumeLiquid(Liquids.water, 0.1f).boost();
        }};

        exWaterExtractor = new ExSolidPump("ex-water-extractor") {{
            requirements(Category.production, with(Items.metaglass, 30, Items.graphite, 30, Items.lead, 30, Items.copper, 30));
            result = Liquids.water;
            pumpAmount = 0.11f;
            size = 2;
            liquidCapacity = 30f;
            rotateSpeed = 1.4f;
            attribute = Attribute.water;
            envRequired |= Env.groundWater;

            consumePower(1.5f);
        }};

        exCultivator = new ExAttributeCrafter("ex-cultivator") {{
            requirements(Category.production, with(Items.copper, 25, Items.lead, 25, Items.silicon, 10));
            outputItem = new ItemStack(Items.sporePod, 1);
            craftTime = 100;
            size = 2;
            hasLiquids = true;
            hasPower = true;
            hasItems = true;

            craftEffect = Fx.none;
            envRequired |= Env.spores;
            attribute = Attribute.spores;

            legacyReadWarmup = true;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.water),
                    new DrawDefault(),
                    new DrawCultivator(),
                    new DrawRegion("-top")
            );
            maxBoost = 2f;

            consumePower(80f / 60f);
            consumeLiquid(Liquids.water, 18f / 60f);
        }};

        exOilExtractor = new ExFracker("ex-oil-extractor") {{
            requirements(Category.production, with(Items.copper, 150, Items.graphite, 175, Items.lead, 115, Items.thorium, 115, Items.silicon, 75));
            result = Liquids.oil;
            updateEffect = Fx.pulverize;
            updateEffectChance = 0.05f;
            pumpAmount = 0.25f;
            size = 3;
            liquidCapacity = 30f;
            attribute = Attribute.oil;
            baseEfficiency = 0f;
            itemUseTime = 60f;

            consumeItem(Items.sand);
            consumePower(3f);
            consumeLiquid(Liquids.water, 0.15f);
        }};
    }

    public static void loadDistribution() {
        exConveyor = new ExConveyor("ex-conveyor") {{
            requirements(Category.distribution, with(Items.copper, 1));
            health = 45;
            speed = 0.03f;
            displayedSpeed = 4.2f;
            buildCostMultiplier = 2f;
            researchCost = with(Items.copper, 5);
        }};

        exTitaniumConveyor = new ExConveyor("ex-titanium-conveyor") {{
            requirements(Category.distribution, with(Items.copper, 1, Items.lead, 1, Items.titanium, 1));
            health = 65;
            speed = 0.08f;
            displayedSpeed = 11f;
        }};

        exPlastaniumConveyor = new ExStackConveyor("ex-plastanium-conveyor") {{
            requirements(Category.distribution, with(Items.plastanium, 1, Items.silicon, 1, Items.graphite, 1));
            health = 75;
            speed = 4f / 60f;
            itemCapacity = 10;
        }};

        exArmoredConveyor = new ExArmoredConveyor("ex-armored-conveyor") {{
            requirements(Category.distribution, with(Items.plastanium, 1, Items.thorium, 1, Items.metaglass, 1));
            health = 180;
            speed = 0.08f;
            displayedSpeed = 11f;
        }};

        exJunction = new ExJunction("ex-junction") {{
            requirements(Category.distribution, with(Items.copper, 2));
            speed = 26;
            capacity = 6;
            health = 30;
            buildCostMultiplier = 6f;
        }};

        exItemBridge = new ExBufferedItemBridge("ex-bridge-conveyor") {{
            requirements(Category.distribution, with(Items.lead, 6, Items.copper, 6));
            fadeIn = moveArrows = false;
            range = 4;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 14;
        }};

        exPhaseConveyor = new ExItemBridge("ex-phase-conveyor") {{
            requirements(Category.distribution, with(Items.phaseFabric, 5, Items.silicon, 7, Items.lead, 10, Items.graphite, 10));
            range = 12;
            arrowPeriod = 0.9f;
            arrowTimeScl = 2.75f;
            hasPower = true;
            pulse = true;
            envEnabled |= Env.space;
            consumePower(0.30f);
        }};

        exSorter = new ExSorter("ex-sorter") {{
            requirements(Category.distribution, with(Items.lead, 2, Items.copper, 2));
            buildCostMultiplier = 3f;
        }};

        exInvertedSorter = new ExSorter("ex-inverted-sorter") {{
            requirements(Category.distribution, with(Items.lead, 2, Items.copper, 2));
            buildCostMultiplier = 3f;
            invert = true;
        }};

        exRouter = new ExRouter("ex-router") {{
            requirements(Category.distribution, with(Items.copper, 3));
            buildCostMultiplier = 4f;
        }};

        exDistributor = new ExRouter("ex-distributor") {{
            requirements(Category.distribution, with(Items.lead, 4, Items.copper, 4));
            buildCostMultiplier = 3f;
            size = 2;
        }};

        exOverflowGate = new ExOverflowGate("ex-overflow-gate") {{
            requirements(Category.distribution, with(Items.lead, 2, Items.copper, 4));
            buildCostMultiplier = 3f;
        }};

        exUnderflowGate = new ExOverflowGate("ex-underflow-gate") {{
            requirements(Category.distribution, with(Items.lead, 2, Items.copper, 4));
            buildCostMultiplier = 3f;
            invert = true;
        }};

        exMassDriver = new ExMassDriver("ex-mass-driver") {{
            requirements(Category.distribution, with(Items.titanium, 125, Items.silicon, 75, Items.lead, 125, Items.thorium, 50));
            size = 3;
            itemCapacity = 120;
            reload = 200f;
            range = 440f;
            consumePower(1.75f);
        }};
    }

    public static void loadCaiDan() {
        Log.info("Loading CaiDan...");

        RA2 = new ExItemTurret("RA2") {{
            requirements(Category.turret, with(Items.copper, 35));
            ammo(
                    Items.copper, new BasicBulletType(15f, 150) {{
                        width = 7f;
                        height = 9f;
                        lifetime = 60f;
                        ammoMultiplier = 24;

                        fragBullets = 4;
                        fragBullet = new BasicBulletType(15f, 150) {{
                            width = 7f;
                            height = 9f;
                            lifetime = 60f;

                            fragBullets = 12;
                            fragBullet = new BasicBulletType(15f, 150) {{
                                width = 7f;
                                height = 9f;
                                lifetime = 600f;

                                splashDamage = 25f;
                                splashDamageRadius = 100f;
                            }};
                        }};
                    }}
            );

            shoot = new ShootAlternate(3.5f);

            recoils = 2;

            recoil = 0.5f;
            shootY = 3f;
            reload = 1f;
            range = 11000;
            shootCone = 15f;
            ammoUseEffect = Fx.casing2;
            health = 2;
            inaccuracy = 2f;
            rotateSpeed = 10f;
            coolant = consumeCoolant(0.1f);
            researchCostMultiplier = 0.05f;

            limitRange();
        }};

        oneGamma = new ExShockMine("one-gamma") {{
            requirements(Category.effect, with(Items.copper, 1));
            size = 8;
            hasShadow = false;
            health = 2147483647;
            damage = 1048576f;
            tileDamage = 7f;
            length = 1000;
            tendrils = 4;
        }};
    }
}
