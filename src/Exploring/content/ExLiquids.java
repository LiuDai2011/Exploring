package Exploring.content;

import arc.graphics.Color;
import mindustry.type.Liquid;

public class ExLiquids {
    public static Liquid helium, liquidHelium, sandboxLiquid;

    public static void load() {
        helium = new Liquid("helium", Color.white) {{
            lightColor = Color.white;
            gasColor = Color.white;
        }};

        liquidHelium = new Liquid("liquid-helium", Color.white) {{
            heatCapacity = 5.5f;
            temperature = 0.003f;
            effect = ExStatusEffects.freeze;
            lightColor = Color.valueOf("ddeeff").a(0.2f);
            boilPoint = 0.80f;
            viscosity = 0.005f;
            gasColor = Color.valueOf("c1e8f5");
        }};

        sandboxLiquid = new Liquid("sandbox-liquid", Color.white) {{
            heatCapacity = 2147483647f;
            temperature = 0f;
            effect = ExStatusEffects.freeze;
            lightColor = Color.valueOf("ddeeff").a(0.2f);
            boilPoint = 0.80f;
            viscosity = 0.005f;
            gasColor = Color.valueOf("c1e8f5");
        }};
    }
}
