package Exploring.world.blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.geom.Point2;
import arc.scene.ui.layout.Table;
import arc.util.Eachable;
import arc.util.Nullable;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.entities.TargetPriority;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.content;

public class MetaItemBridge extends Block {
    public final int timerCheckMoved = timers++;
    public int range;
    public float transportTime = 2f;
    public boolean fadeIn = true;
    public boolean moveArrows = true;
    public boolean pulse = false;
    public float arrowSpacing = 4f, arrowOffset = 2f, arrowPeriod = 0.4f;
    public float arrowTimeScl = 6.2f;
    public float bridgeWidth = 6.5f;
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
        copyConfig = false;
        allowConfigInventory = false;
        priority = TargetPriority.transport;

        config(Item.class, (MetaItemBridgeBuild tile, Item item) -> tile.sortItem = item);
        configClear((MetaItemBridgeBuild tile) -> tile.sortItem = null);
        config(Point2.class, (MetaItemBridgeBuild tile, Point2 i) -> tile.link = Point2.pack(i.x + tile.tileX(), i.y + tile.tileY()));
        config(Integer.class, (MetaItemBridgeBuild tile, Integer i) -> tile.link = i);

        buildType = MetaItemBridgeBuild::new;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.speed, 60f / speed, StatUnit.itemsSecond);
    }

    @Override
    public void drawPlanConfig(BuildPlan plan, Eachable<BuildPlan> list) {
        super.drawPlanConfig(plan, list);
        // TODO todo
    }

    @Override
    public void setBars() {
        super.setBars();
        // TODO todo
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
            return getLinkingBuilding().canUnload() && getLinkingBuilding().items != null && getLinkingBuilding().items.has(item);
        }

        @Override
        public void updateTile() {
            if (link == -1) {
                link = Vars.state.teams.cores(Team.sharded).get(0).tile.pos();
            }
            if (link != -1 && getLinkingBuilding() == null) link = -1;
            if ((unloadTimer += delta()) < speed) return;
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

                if (getLinkingBuilding() != null && items.total() < itemCapacity) {
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
            return super.onConfigureBuildTapped(other);
        }
    }
}
