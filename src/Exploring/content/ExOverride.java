package Exploring.content;

import Exploring.ExSettings;
import Exploring.world.meta.ExEventType;
import Exploring.world.meta.ExStatValues;
import arc.Core;
import arc.Events;
import arc.func.Cons;
import arc.struct.ObjectMap;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Http;
import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.serialization.Jval;
import mindustry.content.*;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.net.ServerGroup;
import mindustry.type.ItemStack;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValue;

import java.util.Objects;

import static Exploring.ExploringMain.err;
import static Exploring.ExploringMain.info;
import static Exploring.content.DefaultServerUrl.serverBe;
import static Exploring.content.DefaultServerUrl.serverV7;
import static mindustry.Vars.*;

public class ExOverride {
    public static void set() {
        Events.on(EventType.ClientLoadEvent.class, e -> {
            load();
        });
    }

    public static void load() {
        overridePublic();

        if (ExSettings.overrideServer) loadServers();

        if (!ExSettings.devEnv) return;
        overrideDeveloper();
    }

    public static void overridePublic() {
        removeImmunities();
        overrideTurrets();
        sandbox();
    }

    public static void sandbox() {
        addOverrideTag(Blocks.itemSource);
        Events.on(ExEventType.ContentInfoInitEvent.class, e -> {
            if (e.content.name.equals(Blocks.itemSource.name)) {
                info(Blocks.itemSource.stats.toMap().size);
                for (ObjectMap.Entry<StatCat, OrderedMap<Stat, Seq<StatValue>>> entry : Blocks.itemSource.stats.toMap().entries()) {
                    info(entry);
//                    if (Objects.equals(entry.key.name, StatCat.crafting.name)) {
//                        info(entry.value.get(Stat.output).clear().size);
//                    }
                }
                Blocks.itemSource.stats.remove(Stat.output);
                Blocks.itemSource.stats.add(Stat.output, ExStatValues.sNumber(ExChars.infinity, StatUnit.itemsSecond, false));
            }
        });
        Blocks.itemSource.buildType = () -> ((ItemSource) Blocks.itemSource).new ItemSourceBuild() {
            @Override
            public byte version() {
                return 1;
            }

            @Override
            public void write(Writes write){
                super.write(write);
                write.str(outputItem == null ? "null-item" : outputItem.name);
            }

            @Override
            public void read(Reads read, byte revision) {
                super.read(read, revision);
                if (revision == 0) {
                    outputItem = content.item(read.s());
                } else {
                    outputItem = content.item(read.str());
                }
            }

            @Override
            public void updateTile(){
                if(outputItem == null) return;

                items.set(outputItem, 0x007f_ffff);// 8388607
                dump(outputItem);
                items.set(outputItem, 0);
            }
        };
    }

    public static void overrideTurrets() {
        overrideContent(((ItemTurret) Blocks.scorch), turret -> {
            var bt = new BulletType(2f, 80f){{
                ammoMultiplier = 10f;
                hitSize = 10f;
                lifetime = 24f;
                pierce = true;
                collidesAir = false;
                statusDuration = 60f * 15;
                shootEffect = ExFx.shootMidFlame;
                hitEffect = Fx.hitFlameSmall;
                despawnEffect = Fx.none;
                status = StatusEffects.burning;
                keepVelocity = false;
                hittable = false;
            }};
            bt.load();
            turret.ammoTypes.put(ExItems.fuelUnitI, bt);
        });
        overrideContent(((ItemTurret) Blocks.hail), turret -> {
            var bt = turret.ammoTypes.get(Items.pyratite).copy();
            bt.load();
            bt.knockback = 1.5f;
            bt.lifetime = 90;
            bt.rangeChange = 10f;
            ((ArtilleryBulletType) bt).width = ((ArtilleryBulletType) bt).height = 14f;
            bt.splashDamageRadius = 25f;
            bt.splashDamage = 59;
            bt.statusDuration = 60f * 16f;
            bt.ammoMultiplier = 5;
            turret.ammoTypes.put(ExItems.fuelUnitI, bt);
        });
    }

    public static <T extends UnlockableContent> void overrideContent(T content, Cons<T> builder) {
        addOverrideTag(content);
        builder.get(content);
    }

    public static void addOverrideTag(UnlockableContent content) {
        if (content.description != null) {
            content.description += ExSettings.overrideTag;
        }
    }

    public static void removeImmunities() {
        Seq<UnitType> units = content.units();
        for (UnitType type : units) {
            if (type.immunities.contains(ExStatusEffects.abyss))
                type.immunities.remove(ExStatusEffects.abyss);
        }
    }

    public static void loadServers() {
        var url = becontrol.active() ? serverJsonBeURL : serverJsonURL;
        info("Fetching community servers at @", url);

        Http.get(url)
                .error(t -> {
                    err("Failed to fetch community servers");
                    info("Overriding servers");
                    overrideServers(getServerString(becontrol.active()));
                })
                .submit(result -> {
                    overrideServers(result.getResultAsString());
                });
    }

    private static void overrideServers(String json) {
        Jval val = Jval.read(json);
        Seq<ServerGroup> servers = new Seq<>();
        val.asArray().each(child -> {
            String name = child.getString("name", "");
            boolean prioritized = child.getBool("prioritized", false);
            String[] addresses;
            if(child.has("addresses") || (child.has("address") && child.get("address").isArray())){
                addresses = (child.has("addresses") ? child.get("addresses") : child.get("address")).asArray().map(Jval::asString).toArray(String.class);
            }else{
                addresses = new String[]{child.getString("address", "<invalid>")};
            }
            servers.add(new ServerGroup(name, addresses, prioritized));
        });
        Core.app.post(() -> {
            servers.sort(s -> s.name == null ? Integer.MAX_VALUE : s.name.hashCode());
            defaultServers.addAll(servers);
            info("Fetched @ community servers.", defaultServers.size);
        });
    }

    private static String getServerString(boolean be) {
        return be ? serverBe : serverV7;
    }

    // region dev

    public static void overrideDeveloper() {
        Cons<Block> show = block -> block.requirements(block.category, block.requirements);

        show.get(Blocks.powerSource);
        show.get(Blocks.powerVoid);
        show.get(Blocks.itemSource);
        show.get(Blocks.itemVoid);
        show.get(Blocks.liquidSource);
        show.get(Blocks.liquidVoid);
        show.get(Blocks.payloadSource);
        show.get(Blocks.payloadVoid);
        show.get(Blocks.heatSource);

        Seq<Block> blocks = content.blocks();
        for (var b : blocks) {
            overrideBlockDev(b);
        }

        Liquids.cryofluid.temperature = -1e4f;
        Liquids.cryofluid.heatCapacity = 1e4f;

        Blocks.coreShard.unitCapModifier *= 100;
        Blocks.coreAcropolis.unitCapModifier *= 100;
        Blocks.coreBastion.unitCapModifier *= 100;
        Blocks.coreCitadel.unitCapModifier *= 100;
        Blocks.coreZone.unitCapModifier *= 100;
        Blocks.coreFoundation.unitCapModifier *= 100;
        Blocks.coreNucleus.unitCapModifier *= 100;
    }

    private static void overrideBlockDev(Block block) {
        if (block == null) return;
        block.requirements = ItemStack.empty;
        block.researchCostMultiplier = -1f;
        block.researchCost = ItemStack.empty;
        block.buildCost = 1f;
        block.buildCostMultiplier = 0.1f;
    }

    // endregion
}
