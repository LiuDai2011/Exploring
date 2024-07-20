package Exploring.content;

import Exploring.graphics.ExColor;
import Exploring.world.meta.ExFunc;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.util.Tmp;
import arc.util.pooling.Pool;
import mindustry.entities.Effect;
import mindustry.gen.Bullet;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;

import static Exploring.math.MathDef.dx;
import static Exploring.math.MathDef.dy;
import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.headless;
import static mindustry.Vars.state;

public class ExFx {
    public static final Effect shootMidFlame = new Effect(44f, 90f, e -> {
        color(Pal.lightFlame, Pal.darkFlame, Color.gray, e.fin());

        randLenVectors(e.id, 8, e.finpow() * 80f, e.rotation, 10f, (x, y) -> {
            Fill.circle(e.x + x, e.y + y, 0.65f + e.fout() * 1.8f);
        });
    });

    public static final Effect accretionDiskEffect = new Effect(60, e -> {
        if (headless || !(e.data instanceof ateData data) || data.owner == null) return;

        float fin = data.out ? e.finpow() : e.foutpow();
        float fout = data.out ? e.foutpow() : e.finpow();

        float start = Mathf.randomSeed(e.id, 360f);
        var b = data.owner;

        float ioRad = data.outRad - (data.outRad - data.inRad) * fin;
        float rad = data.speed * e.time * 6;
        float dx = dx(b.x, ioRad, start - rad),
                dy = dy(b.y, ioRad, start - rad);

        if (data.trail == null) data.trail = new Trail(data.length);
        float dzin = data.out && e.time > e.lifetime - 10 ? Interp.pow2Out.apply((e.lifetime - e.time) / 10) : fin;
        data.trail.length = data.length;

        if (!state.isPaused()) data.trail.update(dx, dy, 1);

        float z = Draw.z();
        Draw.z(Layer.effect - 19 * fout);
        data.trail.draw(Tmp.c3.set(e.color).shiftValue(-e.color.value() * fout), data.width * dzin);
        Draw.z(z);
    });

    public static Effect singularitySpawn(float rad) {
        return new Effect(300f, 1600f, e -> {
            Rand rand = ExFunc.rand;
            rand.setSeed(e.id);

            Draw.color(Color.white, ExColor.lightBlue, e.fin() + 0.6f);
            float circleRad = e.fin(Interp.circleOut) * rad * 4f;
            Lines.stroke(12 * e.fout() / 1.5f);
            Lines.circle(e.x, e.y, circleRad);
            for (int i = 0; i < 16 * 8; i++) {
                Tmp.v1.set(1, 0).setToRandomDirection(rand).scl(circleRad);
                Fill.tri(e.x + Tmp.v1.x + Angles.trnsx(Tmp.v1.angle() - 180 + 90, rand.random(circleRad / 16, circleRad / 12) * e.fout()), e.y + Tmp.v1.y + Angles.trnsy(Tmp.v1.angle() - 180 + 90, rand.random(circleRad / 16, circleRad / 12) * e.fout()),
                        e.x + Tmp.v1.x - Angles.trnsx(Tmp.v1.angle() - 180 + 90, rand.random(circleRad / 16, circleRad / 12) * e.fout()), e.y + Tmp.v1.y - Angles.trnsy(Tmp.v1.angle() - 180 + 90, rand.random(circleRad / 16, circleRad / 12) * e.fout()),
                        Angles.trnsx(Tmp.v1.angle() - 180, rand.random(circleRad / 4, circleRad / 1.5f) * (1 + e.fin()) / 2) + e.x + Tmp.v1.x, Angles.trnsy(Tmp.v1.angle() - 180, rand.random(circleRad / 4, circleRad / 1.5f) * (1 + e.fin()) / 2) + e.y + Tmp.v1.y);
            }
            Drawf.light(e.x, e.y, rad * e.fslope() * 4f, ExColor.lightBlue, 0.7f);
        }).layer(Layer.effect + 0.001f);
    }

    public static class ateData implements Pool.Poolable {
        public float width;
        public int length;
        public float inRad, outRad, speed;

        public transient Trail trail;

        public Bullet owner;

        public boolean out = false;

        @Override
        public void reset() {
            width = 0;
            length = 0;
            inRad = outRad = speed = 0;

            trail = null;
            owner = null;

            out = false;
        }
    }
}
