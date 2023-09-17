package Exploring.world.blocks;

import arc.Core;
import arc.graphics.Color;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;

public class ExGenericCrafter extends GenericCrafter {
    public ExGenericCrafter(String name) {
        super(name);
        buildType = ExGenericCrafterBuild::new;
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

    public class ExGenericCrafterBuild extends GenericCrafterBuild {

    }
}
