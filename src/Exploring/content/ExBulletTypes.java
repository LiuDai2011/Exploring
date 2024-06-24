package Exploring.content;

import Exploring.graphics.ExPal;
import Exploring.world.meta.ExFunc;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Interp;
import arc.math.Rand;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Drawf;

public class ExBulletTypes {
    public static BulletType smallEnergyShell,

    reignXDead;

    public static void load() {
        smallEnergyShell = new BulletType() {
            {
                lifetime = 60f;
                trailColor = ExPal.lightBlue;
                trailInterval = 0f;
                trailLength = 15;

                speed = 3;
                damage = 15;
            }

            @Override
            public void draw(Bullet b) {
                super.draw(b);
                Draw.color(trailColor);
                Fill.circle(b.x, b.y, 20f);
                Drawf.light(b.x, b.y, 20f, trailColor, 0.7f);
                Draw.reset();
            }
        };

        reignXDead = new BulletType() {
            {
                speed = 5f;
                lifetime = 180f;
            }

            @Override
            public void draw(Bullet b) {
                Rand rand = ExFunc.rand;

                Draw.color(ExPal.lightBlue, Color.white, b.fin());
                Fill.circle(b.x, b.y, 8f * b.fin(Interp.fastSlow));
                Drawf.light(b.x, b.y, 8f * b.fin(Interp.fastSlow), Draw.getColor(), 0.4f);
                Draw.reset();
            }
        };
    }
}
