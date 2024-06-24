package Exploring.math;

public class MathDef {
    public static float dx(float px, float r, float angle) {
        return px + r * (float) Math.cos(angle * Math.PI / 180);
    }

    public static float dy(float py, float r, float angle) {
        return py + r * (float) Math.sin(angle * Math.PI / 180);
    }
}
