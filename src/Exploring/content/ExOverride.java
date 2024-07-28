package Exploring.content;

import Exploring.ExSettings;
import Exploring.ExploringMain;
import Exploring.world.meta.ExEventType;
import Exploring.world.meta.ExStatValues;
import arc.Core;
import arc.Events;
import arc.files.Fi;
import arc.func.Cons;
import arc.func.Func;
import arc.func.Prov;
import arc.graphics.Color;
import arc.graphics.PixmapIO;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ButtonGroup;
import arc.scene.ui.ImageButton;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.TextField;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.struct.OrderedMap;
import arc.struct.Seq;
import arc.util.Http;
import arc.util.Log;
import arc.util.Strings;
import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.serialization.Jval;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.ctype.UnlockableContent;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.net.ServerGroup;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Liquid;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.blocks.sandbox.LiquidSource;
import mindustry.world.blocks.sandbox.PowerSource;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
        if (ExSettings.overrideUI) {
            createUIOverrider();
            overrideUI();
        }

        if (!ExSettings.devEnv) return;
        overrideDeveloper();
    }

    public static void overrideUI() {
        String hex = Core.settings.getString("ex-orui-stylesbasecolor", "undefined");
        if (hex.equals("undefined")) return;
        Color color = Color.valueOf(hex);
        Field[] fields = TextureRegionDrawable.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!field.getName().equals("tint")) continue;
                Log.info("tint: @", hex);
                field.setAccessible(true);
                ((Color) field.get(Styles.black)).set(color.cpy().a(1));
                ((Color) field.get(Styles.black9)).set(color.cpy().a(0.9f));
                ((Color) field.get(Styles.black8)).set(color.cpy().a(0.8f));
                ((Color) field.get(Styles.black6)).set(color.cpy().a(0.6f));
                ((Color) field.get(Styles.black5)).set(color.cpy().a(0.5f));
                ((Color) field.get(Styles.black3)).set(color.cpy().a(0.3f));
                ((Color) field.get(Styles.accentDrawable)).set(Pal.accent);
                break;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createUIOverrider() {
        if (mods.getMod(ExploringMain.MOD.meta.name + "-orui") != null) {
            if (!mods.getMod(ExploringMain.MOD.meta.name + "-orui").enabled())
                mods.setEnabled(mods.getMod(ExploringMain.MOD.meta.name + "-orui"), true);
            return;
        }

        String name = ExploringMain.MOD.meta.name + "-orui.zip";
        String temp = modDirectory.child("temp.png").path();
        File out = new File(modDirectory.child(name).path());
        Fi orui = ExploringMain.MOD.root.child("optional-override-sprites").child("ui");
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(out));
            ZipEntry entry = new ZipEntry("mod.json");
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write((
                    "{ \"name\": \"" + ExploringMain.MOD.meta.name + "-orui" + "\", \"displayName\": \"" +
                            Core.bundle.get("mod.exploring.orui-name") + "\", " +
                            "\"author\": \"" + ExploringMain.MOD.meta.author + "\", " +
                            "\"version\": \"" + 0 + "\", \"minGameVersion\": \"" + 146 +
                            "\" }"
            ).getBytes());
            zipOutputStream.closeEntry();

            entry = new ZipEntry("scripts/main.js");
            zipOutputStream.putNextEntry(entry);
            zipOutputStream.write("""
                    Core.settings.put("ex-orui-stylesbasecolor", "8cb4c3");
                    Events.on(EventType.ClientLoadEvent, () => {
                        Pal.accent = Color.valueOf("ceeaf4");
                    })
                    """.getBytes());
            zipOutputStream.closeEntry();

            Fi[] all = orui.list();
            for (int i = 0; i < all.length; i++) {
                entry = new ZipEntry("sprites-override/ui/" + all[i].name());
                PixmapIO.writePng(modDirectory.child("temp.png"),
                        PixmapIO.readPNG(all[i]));
                File file = new File(temp);
                FileInputStream fileInputStream = new FileInputStream(file);
                zipOutputStream.putNextEntry(entry);

                byte[] buffer = new byte[(int) file.length() + 1024];
                int len;
                while ((len = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }

                zipOutputStream.closeEntry();
            }

            zipOutputStream.close();
            modDirectory.child("temp.png").delete();

            Events.on(EventType.ClientLoadEvent.class, e -> {
                Core.settings.put("mod-" + ExploringMain.MOD.meta.name + "-orui-enabled", true);
                ExSettings.tip.show();
            });
        } catch (IOException e) {
//            throw new RuntimeException(e);
            err(e);
        }
    }

    public static void overridePublic() {
        removeImmunities();
        overrideTurrets();
        sandbox();
        cores();
    }

    public static void cores() {
        Cons<CoreBlock> builder = core -> {
            core.buildType = () -> core.new CoreBuild() {
                @Override
                public void drawSelect() {
                    Lines.stroke(1f, Pal.accent);
                    Cons<Building> outline = b -> {
                        for(int i = 0; i < 4; i++){
                            Point2 p = Geometry.d8edge[i];
                            float offset = -Math.max(b.block.size - 1, 0) / 2f * tilesize;
                            Draw.rect("block-select", b.x + offset * p.x, b.y + offset * p.y, i * 90);
                        }
                    };
                    team.cores().each(core -> {
                        outline.get(core);
                        core.proximity.each(storage -> storage.items == items, outline);
                    });
                    ExGroups.checkCoreLinkerKey(team);
                    ExGroups.coreLinkers.get(team).each(core -> {
                        outline.get(core);
                        core.proximity.each(storage -> storage.items == items, outline);
                    });
                    Draw.reset();
                }
            };
        };

        Seq<Block> blocks = content.blocks();
        for (var b : blocks) {
            if (b instanceof CoreBlock coreBlock) builder.get(coreBlock);
        }
    }

    public static void sandbox() {
        Events.on(ExEventType.ContentInfoInitEvent.class, e -> {
            if (e.content.name.equals(Blocks.itemSource.name)) {
                Blocks.itemSource.stats.remove(Stat.output);
                Blocks.itemSource.stats.add(Stat.output, ExStatValues.sNumber(ExChars.infinity, StatUnit.itemsSecond, false));
            }
            if (e.content.name.equals(Blocks.liquidSource.name)) {
                Blocks.liquidSource.stats.add(Stat.output, ExStatValues.sNumber(ExChars.infinity, StatUnit.liquidSecond, false));
            }
            if (e.content.name.equals(Blocks.powerSource.name)) {
                Blocks.powerSource.stats.add(Stat.output, ExStatValues.sNumber(ExChars.infinity, StatUnit.powerSecond, false));
            }
        });

        addOverrideTag(Blocks.itemSource);
        Blocks.itemSource.buildType = () -> ((ItemSource) Blocks.itemSource).new ItemSourceBuild() {
            public int outputAmount = 1;
            private TextField search;
            private TextField amount;
            private int rowCount;

            @Override
            public byte version() {
                return 1;
            }

            @Override
            public void write(Writes write) {
                super.write(write);
                write.str(outputItem == null ? "null-item" : outputItem.name);
                write.i(outputAmount);
            }

            @Override
            public void read(Reads read, byte revision) {
                super.read(read, revision);
                if (revision == 0) {
                    outputItem = content.item(read.s());
                } else {
                    outputItem = content.item(read.str());
                    outputAmount = read.i();
                }
            }

            @Override
            public void buildConfiguration(Table table) {
                Block block = Blocks.itemSource;
                Seq<Item> items = content.items();
                Prov<Item> holder = () -> outputItem;
                Cons<Item> consumer = this::configure;
                boolean closeSelect = false;
                int rows = Blocks.itemSource.selectionRows, columns = Blocks.itemSource.selectionColumns;

                ButtonGroup<ImageButton> group = new ButtonGroup<>();
                group.setMinCheckCount(0);
                Table cont = new Table().top();
                cont.defaults().size(40);

                if (search != null) search.clearText();

                Runnable rebuild = () -> {
                    group.clear();
                    cont.clearChildren();

                    var text = search != null ? search.getText() : "";
                    int i = 0;
                    rowCount = 0;

                    Seq<Item> list = items.select(u -> (text.isEmpty() || u.localizedName.toLowerCase().contains(text.toLowerCase())));
                    for (Item item : list) {
                        if (!item.unlockedNow() || state.rules.hiddenBuildItems.contains(item) || item.isHidden())
                            continue;

                        ImageButton button = cont.button(Tex.whiteui, Styles.clearNoneTogglei, Mathf.clamp(item.selectionSize, 0f, 40f), () -> {
                            if (closeSelect) control.input.config.hideConfig();
                        }).tooltip(item.localizedName).group(group).get();
                        button.changed(() -> consumer.get(button.isChecked() ? item : null));
                        button.getStyle().imageUp = new TextureRegionDrawable(item.uiIcon);
                        button.update(() -> button.setChecked(holder.get() == item));

                        if (i++ % columns == (columns - 1)) {
                            cont.row();
                            rowCount++;
                        }
                    }
                };

                rebuild.run();

                Table main = new Table().background(Styles.black6);
                if (rowCount > rows * 1.5f) {
                    main.table(s -> {
//                        t.left();
//                        t.add("text").left().padRight(5)
//                                .update(a -> a.setColor(true/*condition.get()*/ ? Color.white : Color.gray));
//                        t.field(String.valueOf(outputAmount), s -> outputAmount = Strings.parseInt(s))
////                            .update(a -> a.setDisabled(!condition.get()))
//                                .padRight(100f)
//                                .valid(f -> Strings.parseInt(f) >= 1 && Strings.parseInt(f) <= 0x007f_ffff).width(120f).left();
                        s.image(Icon.addSmall).size(8).padLeft(4f);
                        amount = s.field(String.valueOf(outputAmount), i -> outputAmount = Strings.parseInt(i))
                                .valid(f -> Strings.parseInt(f) >= 1 && Strings.parseInt(f) <= 0x007f_ffff)
                                .padBottom(4).left().growX().get();
                        amount.setMessageText("@ex-item-source-amount");
                    }).fillX().row();
                    main.table(s -> {
                        s.image(Icon.zoom).padLeft(4f);
                        search = s.field(null, text -> rebuild.run()).padBottom(4).left().growX().get();
                        search.setMessageText("@players.search");
                    }).fillX().row();
                }

                ScrollPane pane = new ScrollPane(cont, Styles.smallPane);
                pane.setScrollingDisabled(true, false);

                if (block != null) {
                    pane.setScrollYForce(block.selectScroll);
                    pane.update(() -> {
                        block.selectScroll = pane.getScrollY();
                    });
                }

                pane.setOverscroll(false, false);
                main.add(pane).maxHeight(40 * rows);
                table.top().add(main);
            }

            @Override
            public void updateTile() {
                if (outputItem == null) return;

                items.set(outputItem, 0x007f_ffff);// 8388607
                for (int i = 0; i < outputAmount; ++i) {
                    dump(outputItem);
                }
//                items.set(outputItem, 0);
            }
        };

        addOverrideTag(Blocks.liquidSource);
        Blocks.liquidSource.buildType = () -> ((LiquidSource) Blocks.liquidSource).new LiquidSourceBuild() {
            @Override
            public void dumpLiquid(Liquid liquid) {
                int dump = cdump;
                if (liquids.get(liquid) > 1.0e-4f) {
                    if (!Vars.net.client() && Vars.state.isCampaign() && team == Vars.state.rules.defaultTeam) {
                        liquid.unlock();
                    }
                    for (int i = 0; i < proximity.size; ++i) {
                        incrementDump(proximity.size);
                        Building other = proximity.get((i + dump) % proximity.size);
                        other = other.getLiquidDestination(this, liquid);
                        if (other != null && other.block.hasLiquids && canDumpLiquid(other, liquid) && other.liquids != null) {
                            transferLiquid(other, other.block.liquidCapacity - other.liquids.get(liquid), liquid);
                        }
                    }
                }
            }

            @Override
            public byte version() {
                return 2;
            }

            @Override
            public void write(Writes write) {
                super.write(write);
                write.str(source == null ? "null-item" : source.name);
            }

            @Override
            public void read(Reads read, byte revision) {
                super.read(read, revision);
                if (revision != 2) {
                    int id = revision == 1 ? read.s() : read.b();
                    source = id == -1 ? null : content.liquid(id);
                } else {
                    source = content.liquid(read.str());
                }
            }
        };

        addOverrideTag(Blocks.powerSource);
        Blocks.powerSource.buildType = () -> ((PowerSource) Blocks.powerSource).new PowerSourceBuild() {
            @Override
            public void displayBars(Table table) {
                try {
                    Field field = Block.class.getDeclaredField("barMap");
                    field.setAccessible(true);
                    for (ObjectMap.Entry<String, Func<Building, Bar>> entry : (OrderedMap<String, Func<Building, Bar>>) field.get(Blocks.powerSource)) {
                        if (!entry.key.equals("power")) {
                            Func<Building, Bar> bar = entry.value;
                            Bar result = bar.get(this);
                            if (result != null) {
                                table.add(result).growX();
                                table.row();
                            }
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public float getPowerProduction() {
                return power.graph.getPowerNeeded() * 2f +
                        power.graph.getLastCapacity() +
                        10;
            }
        };
    }

    public static void overrideTurrets() {
        overrideContent(((ItemTurret) Blocks.scorch), turret -> {
            var bt = new BulletType(2f, 80f) {{
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
            if (child.has("addresses") || (child.has("address") && child.get("address").isArray())) {
                addresses = (child.has("addresses") ? child.get("addresses") : child.get("address")).asArray().map(Jval::asString).toArray(String.class);
            } else {
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
