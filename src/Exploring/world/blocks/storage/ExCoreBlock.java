package Exploring.world.blocks.storage;

import Exploring.content.ExBlocks;
import arc.Core;
import arc.graphics.Color;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;

public class ExCoreBlock extends CoreBlock {
    public ExCoreBlock(String name) {
        super(name);
        update = true;
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("health", entity -> new Bar(
                () -> Core.bundle.format("bar.health", Math.floor(entity.health), entity.maxHealth),
                () -> Color.valueOf("ff0000"),
                () -> entity.health / entity.maxHealth
        ));
    }

    public class ExCoreBuild extends CoreBuild {
        public float timer = 2300f;

        @Override
        public void updateTile() {
            super.updateTile();
            timer += Time.delta;
            if (timer >= 2000f) {
                for (Tile t : Vars.world.tiles) {
                    if (t.floor().name == Blocks.stone.name && t.overlay() != Blocks.spawn && t.drop() == null)
                        t.setFloor(ExBlocks.stone.asFloor());
                }
                timer %= 2000f;
            }
        }
    }
}
