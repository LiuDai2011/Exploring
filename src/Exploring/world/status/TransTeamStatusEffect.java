package Exploring.world.status;

import Exploring.world.util.Change;
import arc.func.Cons;
import arc.math.Mathf;
import arc.math.geom.Rect;
import arc.util.Time;
import mindustry.content.StatusEffects;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.Unit;
import mindustry.type.StatusEffect;

public class TransTeamStatusEffect extends StatusEffect {
    private static final Rect rect = new Rect();
    public Team team = Team.derelict;
    public StatusEffect statusEffect = StatusEffects.none;
    public float chance = 0f;

    public TransTeamStatusEffect(String name) {
        super(name);
    }

    public Team team() {
        return team;
    }

    @Override
    public void update(Unit unit, float time) {
        super.update(unit, time);

        if (unit.team.id != team().id) unit.damageContinuousPierce(damage);
        else unit.heal(-1.5f * damage * Time.delta);

        Change.team(unit, team());

        unit.apply(statusEffect, 120f);

        if (Mathf.chance(0.1)) {
            change(team(), unit.x, unit.y, unit.hitSize * 1.8f);
        }
    }

    public void change(Team team, float x, float y, float radius) {
        Cons<Unit> cons = unit -> {
            if (unit.team == team || !unit.hittable() || !unit.within(x, y, radius)) {
                return;
            }

            unit.apply(statusEffect, 120f);
        };

        rect.setSize(radius * 2).setCenter(x, y);
        if (team != null) {
            Units.nearbyEnemies(team, rect, cons);
        } else {
            Units.nearby(rect, cons);
        }
    }
}
