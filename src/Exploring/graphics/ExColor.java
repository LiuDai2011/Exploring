package Exploring.graphics;

import Exploring.content.ExTeam;
import arc.graphics.Color;

public class ExColor {
    public static final Color blueBase = Color.valueOf("00a6de");
    public static final Color lightBlue = blueBase.cpy().lerp(Color.white, 0.81f);// Color.valueOf("ceeaf4");
    public static final Color author = Color.valueOf("ceeaf4");
    public static final Color abyss = ExTeam.abyss.color;
    public static final Color Void = ExTeam.Void.color;
    public static final Color changeFrom = Color.valueOf("fffefd");
    public static Color test = Color.valueOf("000000");// TODO delete
}
