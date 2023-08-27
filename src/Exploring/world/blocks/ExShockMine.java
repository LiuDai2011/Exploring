package Exploring.world.blocks;

import arc.Core;
import arc.graphics.Color;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.ShockMine;

public class ExShockMine extends ShockMine {
    public ExShockMine(String name) {
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
