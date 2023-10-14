package Exploring.graphics;

import arc.graphics.Color;

import static java.lang.Math.max;

public class ExPal {
    public static Color laser = Color.valueOf("ceeaf4");
    public static Color author = Color.valueOf("ceeaf4");

    public static Color laserBar(Color t, float p) {
        return laser.cpy().lerp(t, p);
    }

    public static Color laser(float laserAmount) {
        if (laserAmount <= 50f) {
            return Color.red.cpy();
        } else if (laserAmount <= 400f) {
            return Color.red.cpy().lerp(Color.yellow.cpy(), (laserAmount - 50f) / 350f);
        } else if (laserAmount <= 2000f) {
            return Color.yellow.cpy().lerp(laser.cpy(), (laserAmount - 400f) / 1600f);
        } else if (laserAmount <= 15000f) {
            return Color.blue.cpy().lerp(Color.white.cpy(), (laserAmount - 2000f) / 13000f);
        } else {
            return Color.white.cpy();
        }
    }
}
