package Exploring.content;

import arc.graphics.g2d.Draw;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Tmp;
import arc.util.pooling.Pool;
import mindustry.entities.Effect;
import mindustry.gen.Bullet;
import mindustry.graphics.Layer;
import mindustry.graphics.Trail;

import static Exploring.math.MathDef.dx;
import static Exploring.math.MathDef.dy;
import static mindustry.Vars.headless;
import static mindustry.Vars.state;

public class ExFx {
    public static Effect AccretionDiskEffect = new Effect(60, e -> {
        if (headless || !(e.data instanceof ateData data) || data.owner == null) return;

        float fin = data.out ? e.finpow() : e.foutpow();
        float fout = data.out ? e.foutpow() : e.finpow();
        //float fout = 1 - fin;

        float start = Mathf.randomSeed(e.id, 360f);
        var b = data.owner;

        float ioRad = data.outRad - (data.outRad - data.inRad) * fin;
        float rad = data.speed * e.time * 6;
        float dx = dx(b.x, ioRad, start - rad),
                dy = dy(b.y, ioRad, start - rad);

        if (data.trail == null) data.trail = new Trail(data.length);
        float dzin = data.out && e.time > e.lifetime - 10 ? Interp.pow2Out.apply((e.lifetime - e.time) / 10) : fin;
        data.trail.length = data.length;
        //data.trail.length = (int) (data.length * dzin);

        if (!state.isPaused()) data.trail.update(dx, dy, 1);

        float z = Draw.z();
        Draw.z(Layer.effect - 19 * fout);
        //Draw.z(Layer.max - 1);
        data.trail.draw(Tmp.c3.set(e.color).shiftValue(-e.color.value() * fout), data.width * dzin);
        //data.trail.draw(e.color, data.width);
        Draw.z(z);
    });

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
