package Exploring.graphics;

import arc.graphics.Color;

public class ExPal {
    public static final Color blueBase = Color.valueOf("00a6de");
    public static final Color lightBlue = blueBase.cpy().lerp(Color.white, 0.81f);// Color.valueOf("ceeaf4");
    public static final Color author = Color.valueOf("ceeaf4");
}
