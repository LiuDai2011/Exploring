package Exploring.world.blocks.storage;

import arc.Core;
import arc.graphics.Color;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.storage.CoreBlock;

import java.util.Objects;

public class ExCoreBlock extends CoreBlock{
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
        public boolean set = false;

        @Override
        public void updateTile() {
            super.updateTile();
            if(!set) {
                for (Tile t : Vars.world.tiles) {
                    if(t.floor().name=="stone") t.setFloor(Blocks.stone.asFloor());
                }
                set = true;
            }
        }
    }
}
