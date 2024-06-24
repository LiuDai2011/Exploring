package Exploring.graphics;

import arc.Core;
import arc.graphics.gl.Shader;

public class BlackHoleShader {
    public static int maxCount = 4;
    public static HoleShader holeShader;

    public static void createShader() {
        if (maxCount >= 510) return;

        maxCount = Math.min(maxCount * 2, 510);
        if (holeShader != null) holeShader.dispose();
        Shader.prependFragmentCode = "#define MAX_COUNT " + maxCount + "\n";
        holeShader = new HoleShader();
        Shader.prependFragmentCode = "";
    }

    public static class HoleShader extends ExShaders.ModShader {
        public float[] blackHoles;

        public HoleShader() {
            super("screenspace", "tearing-space");
        }

        @Override
        public void apply() {
            setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2, Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);

            setUniformi("u_blackholecount", blackHoles.length / 4);
            setUniform4fv("u_blackholes", blackHoles, 0, blackHoles.length);
        }
    }
}
