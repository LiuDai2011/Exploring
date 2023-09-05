package Exploring.world.blocks.turrets;

import arc.Core;
import arc.graphics.Color;
import mindustry.entities.bullet.BulletType;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;

public class ExItemTurret extends ItemTurret {
    public ExItemTurret(String name) {
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

    public class ExItemTurretBuild extends ItemTurretBuild {
    }
}
