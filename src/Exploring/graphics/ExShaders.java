package Exploring.graphics;

import mindustry.graphics.Shaders;

public class ExShaders {
    public static Shaders.SurfaceShader

            liquidHelium;

    public static void init() {
        liquidHelium = new Shaders.SurfaceShader("liquid-helium");
    }
}
