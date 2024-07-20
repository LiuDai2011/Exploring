package Exploring.world.blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.scene.ui.layout.Table;
import arc.util.Nullable;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.entities.TargetPriority;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.content;
import static mindustry.Vars.tilesize;

public class MetaItemBridge extends Block {
    public int range = 4;
    public float speed = 1f;

    public MetaItemBridge(String name) {
        super(name);
        update = true;
        solid = true;
        health = 70;
        hasItems = true;
        configurable = true;
        saveConfig = true;
        noUpdateDisabled = true;
        clearOnDoubleTap = true;
        underBullets = true;
        hasPower = true;
        itemCapacity = 10;
        unloadable = false;
        group = BlockGroup.transportation;
        copyConfig = true;
        allowConfigInventory = false;
        priority = TargetPriority.transport;

        config(Item.class, (MetaItemBridgeBuild tile, Item item) -> tile.sortItem = item);
        config(Point2.class, (MetaItemBridgeBuild tile, Point2 i) -> tile.link = Point2.pack(i.x + tile.tileX(), i.y + tile.tileY()));
        configClear((MetaItemBridgeBuild tile) -> {
            tile.sortItem = null;
            tile.link = -1;
        });

        buildType = MetaItemBridgeBuild::new;
    }

    protected static boolean isValidBuilding(Building build, Team team) {
        return build.block().hasItems && build.team == team;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.speed, 60f / speed, StatUnit.itemsSecond);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);
        Drawf.circles(x, y, range * tilesize);
    }

    public class MetaItemBridgeBuild extends Building {
        private final int itemsLength = content.items().size;
        public int link = -1;
        public float unloadTimer = 0f;
        public int rotations = 0;
        public Item sortItem = null;

        @Nullable
        public Building getLinkingBuilding() {
            return Vars.world.build(link);
        }

        private boolean isPossibleItem(Item item) {
            return link != -1 && getLinkingBuilding().canUnload() && getLinkingBuilding().items != null && getLinkingBuilding().items.has(item);
        }

        @Override
        public void updateTile() {
            if (link != -1 && getLinkingBuilding() == null) link = -1;
            if ((unloadTimer += edelta() * efficiency) < speed) return;

            Item item = null;
            boolean any = false;

            dump();

            if (sortItem != null) {
                if (isPossibleItem(sortItem)) item = sortItem;
            } else {
                for (int i = 0; i < itemsLength; i++) {
                    int total = (rotations + i + 1) % itemsLength;
                    Item possibleItem = content.item(total);

                    if (isPossibleItem(possibleItem)) {
                        item = possibleItem;
                        break;
                    }
                }
            }

            if (item != null) {
                rotations = item.id;

                if (link != -1 && items.total() < itemCapacity) {
                    handleItem(getLinkingBuilding(), item);
                    getLinkingBuilding().removeStack(item, 1);
                    any = true;
                }
            }

            if (any) {
                unloadTimer %= speed;
            } else {
                unloadTimer = Math.min(unloadTimer, speed);
            }
        }

        @Override
        public void draw() {
            super.draw();

            Draw.color(sortItem == null ? Color.clear : sortItem.color);
            Draw.rect(((Unloader) Blocks.unloader).centerRegion, x, y);
            Draw.color();
        }

        @Override
        public void buildConfiguration(Table table) {
            ItemSelection.buildTable(MetaItemBridge.this, table, content.items(), () -> sortItem, this::configure, selectionRows, selectionColumns);
        }

        @Override
        public Item config() {
            return sortItem;
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.str(sortItem == null ? "null-item" : sortItem.name);
            write.i(link);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            sortItem = content.item(read.str());
            link = read.i();
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            if (other == this) {
                link = -1;
                sortItem = null;
                deselect();
                return false;
            }
            if (Mathf.dst(other.x, other.y, x, y) <= range * tilesize && isValidBuilding(other, team)) {
                link = Point2.pack(other.tileX(), other.tileY());
                return false;
            }
            return true;
        }

        @Override
        public void drawConfigure() {
            Drawf.circles(x, y, tile.block().size * tilesize / 2f + 1f + Mathf.absin(Time.time, 4f, 1f));
            Drawf.circles(x, y, range * tilesize);
            if (link != -1)
                Drawf.square(getLinkingBuilding().x, getLinkingBuilding().y, getLinkingBuilding().block.size * tilesize / 2f + 1f, Pal.place);
        }

        @Override
        public void drawSelect() {
            super.drawSelect();

            Lines.stroke(1f);

            Draw.color(Pal.accent);
            Drawf.circles(x, y, range * tilesize);
            Draw.reset();
        }
    }
}
