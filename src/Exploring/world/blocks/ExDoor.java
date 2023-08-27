package Exploring.world.blocks;

import arc.Core;
import arc.graphics.Color;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.Door;

public class ExDoor extends Door {
    public ExDoor(String name) {
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
