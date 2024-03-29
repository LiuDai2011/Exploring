package Exploring.content;

import Exploring.ExploringMain;
import Exploring.entities.bullets.AccelBulletType;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;

public class ExBullets {
    public static BulletType starFire, starFirePro, starFireProMaxUltra;

    public static void load() {
        starFire = new AccelBulletType(0.1f, 55, ExploringMain.name("star-fire")) {{
            frontColor = Color.white;
            backColor = lightningColor = trailColor = lightColor = ExItems.fireUnit.color.cpy().lerp(Color.white, 0.15F);
            lifetime = 51;
            ammoMultiplier = 4;
            accelerateBegin = 0.03f;
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
            status = StatusEffects.burning;
            statusDuration = 120;
        }};
        starFirePro = new AccelBulletType(0.12f, 103, ExploringMain.name("star-fire")) {{
            frontColor = Color.white;
            backColor = lightningColor = trailColor = lightColor = ExItems.blastUnit.color.cpy().lerp(Color.white, 0.15F);
            lifetime = 60;
            ammoMultiplier = 3;
            accelerateBegin = 0.02f;
            accelerateEnd = 1;
            velocityIncrease = 16;
            hitShake = despawnShake = 3;
            width = height = 9;
            splashDamageRadius = 42;
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
            status = StatusEffects.blasted;
            statusDuration = 120;
        }};
        starFireProMaxUltra = new AccelBulletType(1f, 105, ExploringMain.name("star-fire")) {
            {
                frontColor = Color.white;
                backColor = lightningColor = trailColor = lightColor = Color.valueOf("00ff00").lerp(Color.white, 0.15F);
                lifetime = 600;
                ammoMultiplier = 3;
                accelerateBegin = 0f;
                accelerateEnd = 2f;
                velocityIncrease = 58;
                hitShake = despawnShake = 3;
                width = height = 9;
                splashDamageRadius = 420;
                splashDamage = 0.65f * damage;
                shrinkX = shrinkY = 0;
//            hitEffect = NHFx.crossBlast(backColor);
//            despawnEffect = NHFx.hyperBlast(backColor);
//            shootEffect = NHFx.shootCircleSmall(backColor);
                smokeEffect = Fx.shootBigSmoke;
//            trailEffect = NHFx.trailToGray;
                // TODO
                trailLength = 15;
                trailWidth = 2.2f;
                drawSize = 300;
                pierce = true;
                pierceArmor = true;
                homingDelay = 0f;
                homingPower = 0.09f;
                homingRange = 99999;
                fragBullet = new AccelBulletType(1f, 50, ExploringMain.name("star-fire")) {{

                    frontColor = Color.white;
                    backColor = lightningColor = trailColor = lightColor = Color.valueOf("0000ff").lerp(Color.white, 0.15F);
                    lifetime = 1500;
                    ammoMultiplier = 3;
                    accelerateBegin = 0.15f;
                    accelerateEnd = 2f;
                    velocityIncrease = 58;
                    hitShake = despawnShake = 3;
                    width = height = 9;
                    splashDamageRadius = 420;
                    splashDamage = 0.65f * damage;
                    shrinkX = shrinkY = 0;
//            hitEffect = NHFx.crossBlast(backColor);
//            despawnEffect = NHFx.hyperBlast(backColor);
//            shootEffect = NHFx.shootCircleSmall(backColor);
                    smokeEffect = Fx.shootBigSmoke;
//            trailEffect = NHFx.trailToGray;
                    // TODO
                    trailLength = 15;
                    trailWidth = 2.2f;
                    drawSize = 300;
                    pierce = true;
                    pierceArmor = true;
                    homingDelay = 0f;
                    homingPower = 0.09f;
                    homingRange = 99999;
                    fragBullets = 1;
                    fragBullet = new AccelBulletType(1f, 50, ExploringMain.name("star-fire")) {{

                        frontColor = Color.white;
                        backColor = lightningColor = trailColor = lightColor = Color.valueOf("0000ff").lerp(Color.white, 0.15F);
                        lifetime = 1500;
                        ammoMultiplier = 3;
                        accelerateBegin = 0.3f;
                        accelerateEnd = 4f;
                        velocityIncrease = 76;
                        hitShake = despawnShake = 3;
                        width = height = 9;
                        splashDamageRadius = 420;
                        splashDamage = 0.65f * damage;
                        shrinkX = shrinkY = 0;
                        smokeEffect = Fx.shootBigSmoke;
                        // TODO
                        trailLength = 15;
                        trailWidth = 2.2f;
                        drawSize = 300;
                        pierce = true;
                        pierceArmor = true;
                        homingDelay = 0f;
                        homingPower = 0.09f;
                        homingRange = 99999;
                    }};
                }};
            }

            @Override
            public void createFrags(Bullet b, float x, float y) {
                fragBullet.create(b, b.x, b.y, b.rotation());
            }
        };
    }
}
