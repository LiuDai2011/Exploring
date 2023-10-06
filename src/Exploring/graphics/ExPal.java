package Exploring.graphics;

import arc.graphics.Color;

public class ExPal {
    public static Color laser = Color.valueOf("ceeaf4");
    public static Color author = Color.valueOf("ceeaf4");

    public static Color laserBar(Color t, float p) {
        return laser.cpy().lerp(t, p);
    }
}
