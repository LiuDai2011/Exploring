package Exploring.world.blocks.drills;

import arc.Core;
import arc.graphics.Color;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.Fracker;

public class ExFracker extends Fracker {
    public ExFracker(String name) {
        super(name);
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
}
