package Exploring.world.entities.abilities;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.entities.abilities.Ability;

public class NonurgentRepairAbility extends Ability {
    @Override
    public void addStats(Table t) {
        t.add("[lightgray]" + Core.bundle.get("stat.nonurgent-repair") + "[white]");
        t.row();
    }
}
