package Exploring.math;

import arc.math.Mathf;

public class MathDef {
    public static final Object[] empty = {};

    public static float dx(float px, float r, float angle) {
        return px + r * (float) Math.cos(angle * Math.PI / 180);
    }

    public static float dy(float py, float r, float angle) {
        return py + r * (float) Math.sin(angle * Math.PI / 180);
    }

    /**
     * @param a
     * @param b
     * @return {@code Mathf.round(a, 1 / b)}
     */
    public static float exactlyRound(float a, float b) {
        return Mathf.floor(a * b) / b;
    }
}
