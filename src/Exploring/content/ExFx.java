package Exploring.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public class ExFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();

    public static final Effect

            laserBeam = new Effect(300f, e -> {
        randLenVectors(e.id, 5, 3f + e.fin() * 8f, (x, y) -> {
            color(Color.white, Color.valueOf("888800"), (float) Math.sin(e.fin() * Math.PI * 2) / 2f + 0.5f);
            Fill.circle(e.x, e.y, 1f);
            Fill.poly(e.x + (float) Math.cos(e.fin() * Math.PI * 2) * 0.9f, e.y + (float) Math.sin(e.fin() * Math.PI * 2) * 0.9f, 3, 1f, e.fin() * 360f);
        });
    }),

    laserBeamR = new Effect(300f, e -> {
        randLenVectors(e.id, 5, 3f + e.fin() * 8f, (x, y) -> {
            color(Color.white, Color.valueOf("880000"), (float) Math.sin(e.fin() * Math.PI * 3) / 2f + 0.5f);
            Fill.circle(e.x, e.y, 1f);
            Fill.poly(e.x + (float) Math.cos(e.fin() * Math.PI * 5) * 0.9f, e.y + (float) Math.sin(e.fin() * Math.PI * 5) * 0.9f, 3, 1f, e.fin() * 360f);
        });
    }),

    laserBeamP = new Effect(300f, 100f, e -> {
        Color c = Color.HSVtoRGB(e.fin() * 360f * 3f, 100f, 100f, 1f);
        color(c);
        stroke(e.fout() * 4f);
        Lines.circle(e.x, e.y, 4f + e.finpow() * 20f);

        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, 6f, 80f * e.fout(), i * 90 + e.fin() * 360 + 45f);
        }

        color();
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, 3f, 30f * e.fout(), i * 90 + e.fin() * 360 + 45f);
        }

        Drawf.light(e.x, e.y, 150f, c, 0.9f * e.fout());
    }),

    instTrailRainbow = new Effect(300f, e -> {
        Color c = Color.HSVtoRGB(e.fin() * 360f * 3f, 100f, 100f, 1f);
        Color s = Color.HSVtoRGB(e.fin() * 360f * 3f + 180f, 100f, 100f, 0.45f);

        for (int i = 0; i < 2; i++) {
            color(c);

            float m = i == 0 ? 1f : 0.5f;

            float rot = e.rotation + 180f;
            float w = 15f * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
            Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
        }

        for (int i = 0; i < 2; i++) {
            color(s);

            float m = i == 0 ? 1f : 0.5f;

            float rot = e.rotation + 180f;
            float w = 15f * e.fout() * m;
            Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m * 0.6f, rot);
            Drawf.tri(e.x, e.y, w, 10f * m * 0.6f, rot + 180f);
        }

        Drawf.light(e.x, e.y, 60f, c.a(0.5f).add(s.a(0.5f)), 0.6f * e.fout());
    }),

    smokeCloudB = new Effect(70, e -> {
        randLenVectors(e.id, e.fin(), 30, 30f, (x, y, fin, fout) -> {
            color(Color.valueOf("ffe900"));
            alpha((0.5f - Math.abs(fin - 0.5f)) * 2f);
            Fill.circle(e.x + x, e.y + y, 0.5f + fout * 4f);
        });
    });
}
