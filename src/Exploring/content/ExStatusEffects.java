package Exploring.content;

import mindustry.graphics.Pal;
import mindustry.type.StatusEffect;

public class ExStatusEffects {
    public static StatusEffect freeze;

    public static void load() {
        freeze = new StatusEffect("freeze") {{
            color = Pal.lightishGray;
            healthMultiplier = 0.5f;
            speedMultiplier = 0f;
            reloadMultiplier = 0f;
            buildSpeedMultiplier = 0f;
            disarm = true;
            damage = 25f;
        }};
    }
}
