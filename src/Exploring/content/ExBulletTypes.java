package Exploring.content;

import Exploring.graphics.ExColor;
import Exploring.world.entities.bullets.SingularityBulletType;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Drawf;

public class ExBulletTypes {
    public static BulletType smallEnergyShell,

    reignXDead, smallSingularity;

    public static void load() {
        smallEnergyShell = new BulletType() {
            {
                lifetime = 60f;
                trailColor = ExColor.lightBlue;
                trailInterval = 0f;
                trailLength = 15;

                speed = 3;
                damage = 15;
            }

            @Override
            public void draw(Bullet b) {
                super.draw(b);
                Draw.color(trailColor);
                Fill.circle(b.x, b.y, 2f);
                Drawf.light(b.x, b.y, 2f, trailColor, 0.7f);
                Draw.reset();
            }
        };

        reignXDead = new SingularityBulletType() {{
            lifetime = 3600f;
            impulseRange = 160f;
            attract = 9f;
            damage = 12f;
        }};

        smallSingularity = new SingularityBulletType() {{
            lifetime = 900f;
            impulseRange = 48f;
            attract = 3f;
            damage = 1f;
            size = 1;
            triLength = 12;
            triWidth = 2;
            spawnEffect = ExFx.singularitySpawn(4);
            killShooter = true;
        }};
    }
}
