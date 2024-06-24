package Exploring.graphics;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.gl.FrameBuffer;
import arc.struct.Seq;
import arc.util.Tmp;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.game.EventType.Trigger;
import mindustry.graphics.Layer;

import static arc.Core.graphics;

public class BlackHoleRenderer {
    private static final int len = 1000;
    private static BlackHoleRenderer renderer;
    private final Seq<BlackHole> holes = new Seq<>();
    private FrameBuffer buffer;

    protected BlackHoleRenderer() {
        if (!Vars.headless) {
            BlackHoleShader.createShader();

            buffer = new FrameBuffer();
            Events.run(Trigger.draw, this::advancedDraw);
        }
    }

    public static void init() {
        if (renderer == null) renderer = new BlackHoleRenderer();
    }

    public static void addBlackHole(float x, float y, float inRadius, float outRadius, float alpha, boolean circle) {
//        if (!Vars.headless) renderer.addHole(x, y, inRadius, outRadius, alpha, circle);
    }

    public static void addBlackHole(float x, float y, float inRadius, float outRadius, boolean circle) {
        addBlackHole(x, y, inRadius, outRadius, 1, circle);
    }

    private void advancedDraw() {
        Draw.draw(Layer.background - 0.1f, () -> {
            buffer.resize(graphics.getWidth(), graphics.getHeight());
            buffer.begin();
        });

        Draw.draw(Layer.max - 4f, () -> {
            buffer.end();

            if (holes.size >= BlackHoleShader.maxCount) BlackHoleShader.createShader();

            float[] blackholes = new float[holes.size * 4];
            for (int i = 0; i < holes.size; i++) {
                BlackHole hole = holes.get(i);
                blackholes[i * 4] = hole.x;
                blackholes[i * 4 + 1] = hole.y;
                blackholes[i * 4 + 2] = hole.inRadius;
                blackholes[i * 4 + 3] = hole.outRadius;

                if (hole.circle) {
                    Draw.color(Tmp.c2.set(Color.black).a(hole.alpha));
                    Fill.circle(hole.x, hole.y, hole.inRadius);
                    Draw.color();
                }
            }
            BlackHoleShader.holeShader.blackHoles = blackholes;
            buffer.blit(BlackHoleShader.holeShader);

            buffer.begin();
            Draw.rect();
            buffer.end();

            holes.clear();
        });
    }

    private void addHole(float x, float y, float inRadius, float outRadius, float alpha, boolean circle) {
        if (inRadius > outRadius || outRadius <= 0) return;

        holes.add(Pools.obtain(BlackHole.class, BlackHole::new).set(x, y, inRadius, outRadius, alpha, circle));
    }

    private static class BlackHole {
        float x, y, inRadius, outRadius, alpha;
        boolean circle;

        public BlackHole() {

        }

        public BlackHole set(float x, float y, float inRadius, float outRadius, float alpha, boolean circle) {
            this.x = x;
            this.y = y;
            this.inRadius = inRadius;
            this.outRadius = outRadius;
            this.alpha = alpha;
            this.circle = circle;
            return this;
        }
    }
}
