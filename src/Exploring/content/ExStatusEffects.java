package Exploring.content;

import Exploring.graphics.ExColor;
import Exploring.world.status.TransTeamStatusEffect;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.type.StatusEffect;

public class ExStatusEffects {
    public static StatusEffect slow, fast, speed, phased, gravitationalTearing, abyss;

    public static void load() {
        slow = new StatusEffect("slow") {{
            color = ExColor.lightBlue;
            speedMultiplier = reloadMultiplier = 0.65f;

            init(() -> {
                opposite(fast);
            });
        }};

        fast = new StatusEffect("fast") {{
            color = Color.valueOf("f1b754");
            speedMultiplier = reloadMultiplier = 1.4f;

            init(() -> {
                opposite(slow);
            });
        }};

        speed = new StatusEffect("speed") {{
            color = Color.valueOf("f15454");
            speedMultiplier = reloadMultiplier = 1.85f;

            init(() -> {
                opposite(slow);
            });
        }};

        phased = new StatusEffect("phased") {{
            color = Items.phaseFabric.color;
            speedMultiplier = reloadMultiplier = 1.2f;
            damageMultiplier = 1.15f;
        }};

        gravitationalTearing = new StatusEffect("gravitational-tearing") {{
            color = Color.valueOf("7130ad");
            damage = 840 / 60f;
            speedMultiplier = buildSpeedMultiplier = 0;
            reloadMultiplier = 66.7f;
        }};

        abyss = new TransTeamStatusEffect("abyss") {{
            damage = 500f;
            effectChance = 0.5f;
            effect = Fx.chainEmp;
            team = ExTeam.abyss;
            statusEffect = abyss;
            chance = 0.1f;
        }};
    }
}
