package Exploring.world.blocks.distribution;

import arc.Core;
import arc.graphics.Color;
import mindustry.ui.Bar;
import mindustry.world.blocks.distribution.BufferedItemBridge;

public class ExBufferedItemBridge extends BufferedItemBridge {
    public ExBufferedItemBridge(String name) {
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
