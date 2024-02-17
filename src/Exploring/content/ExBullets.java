package Exploring.content;

import Exploring.ExploringMain;
import Exploring.entities.bullets.AccelBulletType;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;

public class ExBullets {
    public static BulletType starFire;

    public static void load() {
        starFire = new AccelBulletType(0.1f, 55, ExploringMain.name("star-fire")) {{
            frontColor = Color.white;
            backColor = lightningColor = trailColor = lightColor = ExItems.blastUnit.color.cpy().lerp(Color.white, 0.15F);
            lifetime = 51;
            ammoMultiplier = 4;
            accelerateBegin = 0.05f;
            accelerateEnd = 1;
            velocityIncrease = 13;
            hitShake = despawnShake = 2;
            width = height = 9;
            splashDamageRadius = 27;
            splashDamage = 0.65f * damage;
            shrinkX = shrinkY = 0;
//            hitEffect = NHFx.crossBlast(backColor);
//            despawnEffect = NHFx.hyperBlast(backColor);
//            shootEffect = NHFx.shootCircleSmall(backColor);
            smokeEffect = Fx.shootBigSmoke;
//            trailEffect = NHFx.trailToGray;
            // TODO
            trailChance = 0.13f;
            trailParam = 2.7f;
            trailLength = 15;
            trailWidth = 2.2f;
            drawSize = 300;
        }};
    }
}
