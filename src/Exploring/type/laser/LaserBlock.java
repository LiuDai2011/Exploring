package Exploring.type.laser;

import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;

public class LaserBlock extends Block {
    public boolean hasLaser = false;

    public LaserBlock(String name) {
        super(name);
        solid = true;
        update = true;
        group = BlockGroup.power;
    }

    @Override
    public boolean canReplace(Block other) {
        return !(other instanceof LaserBlock);
    }
}
