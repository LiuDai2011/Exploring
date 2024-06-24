package Exploring.world.entities.abilities;

import arc.Core;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import mindustry.entities.abilities.Ability;

import static mindustry.Vars.tilesize;

public class SelfbombAbility extends Ability {
    public float mul = 1f, rad = 0f;


    public SelfbombAbility(float mul, float rad) {
        this.mul = mul;
        this.rad = rad;
    }

    @Override
    public void addStats(Table t) {
        t.add("[lightgray]" + Core.bundle.format("stat.self-bomb", mul, Strings.fixed(rad / tilesize, 1)) + "[white]");
        t.row();
    }
}
