package Exploring.content;

import Exploring.graphics.BlackHoleRenderer;
import Exploring.graphics.ExPal;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Interp;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;

public class ExExtraUnitTypes {
    public static UnitType daggerX, maceX, fortressX, scepterX;

    public static void load() {
        daggerX = new UnitType("dagger-x") {{
            constructor = UnitEntity::create;

            speed = 0.6f;
            hitSize = 8f;
            health = 350;
            weapons.add(new Weapon("large-weapon") {{
                reload = 9f;
                x = 4f;
                y = 2f;
                top = false;
                ejectEffect = Fx.casing1;
                bullet = new BulletType() {
                    {
                        lifetime = 60f;
                        trailColor = ExPal.lightBlue;
//                        trailChance = 1f;
                        trailInterval = 0f;
                        trailLength = 15;

                        speed = 3;
                        damage = 15;
                    }

                    @Override
                    public void draw(Bullet b) {
                        super.draw(b);
                        Draw.color(trailColor);
                        Fill.circle(x, y, 20f);
                        Drawf.light(x, y, 20f, trailColor, 0.7f);
                        Draw.reset();
                    }
                };
            }});

            abilities.add(new ForceFieldAbility(20f, 0.1f, 50f, 60f * 1));
        }};

        maceX = new UnitType("mace-x") {{
            constructor = UnitEntity::create;

            speed = 0.7f;
            hitSize = 10f;
            health = 1120;
            armor = 6f;
            ammoType = new ItemAmmoType(Items.coal);

            immunities.add(StatusEffects.burning);

            weapons.add(new Weapon("flamethrower") {{
                top = false;
                shootSound = Sounds.flame;
                shootY = 2f;
                reload = 7f;
                recoil = 1f;
                ejectEffect = Fx.none;
                bullet = new BulletType(4.2f, 37f) {{
                    ammoMultiplier = 3f;
                    hitSize = 7f;
                    lifetime = 13f;
                    pierce = true;
                    pierceBuilding = true;
                    pierceCap = Integer.MAX_VALUE;
                    statusDuration = 60f * 4;
                    shootEffect = Fx.shootSmallFlame;
                    hitEffect = Fx.hitFlameSmall;
                    despawnEffect = Fx.none;
                    status = StatusEffects.burning;
                    keepVelocity = false;
                    hittable = false;
                    splashDamage = 19f;
                    splashDamagePierce = true;
                    splashDamageRadius = 12f;
                }};
            }});

            abilities.add(new ForceFieldAbility(30f, 2f, 230f, 60f * 2));
        }};

        fortressX = new UnitType("fortress-x") {{
            constructor = UnitEntity::create;

            speed = 0.53f;
            hitSize = 13f;
            rotateSpeed = 4f;
            health = 2010;
            armor = 11f;
            mechFrontSway = 0.55f;
            ammoType = new ItemAmmoType(Items.graphite);

            weapons.add(
                    new Weapon("large-weapon") {{
                        shake = 4f;
                        shootY = 9f;
                        x = 4f;
                        y = 0f;
                        rotateSpeed = 2f;
                        reload = 45f;
                        recoil = 4f;
                        shootSound = Sounds.laser;
                        shadow = 20f;
                        rotate = true;

                        bullet = new LaserBulletType() {{
                            damage = 40f;
                            sideAngle = 20f;
                            sideWidth = 1.5f;
                            sideLength = 80f;
                            width = 25f;
                            length = 40f;
                            shootEffect = Fx.shockwave;
                            colors = new Color[]{ExPal.lightBlue, ExPal.lightBlue.cpy().lerp(Color.white, 0.5f), Color.white};
                        }};
                    }},
                    new Weapon("artillery") {{
                        top = false;
                        y = 1f;
                        x = 10f;
                        reload = 30f;
                        recoil = 4f;
                        shake = 2f;
                        ejectEffect = Fx.casing2;
                        shootSound = Sounds.artillery;
                        bullet = new ArtilleryBulletType(2f, 40, "shell") {{
                            hitEffect = Fx.blastExplosion;
                            knockback = 0.8f;
                            lifetime = 180f;
                            width = height = 14f;
                            collides = true;
                            collidesTiles = true;
                            splashDamageRadius = 55f;
                            splashDamage = 120f;
                            backColor = Pal.bulletYellowBack;
                            frontColor = Pal.bulletYellow;
                        }};
                    }}
            );

            abilities.add(new ForceFieldAbility(30f, 3f, 620f, 60f * 3));
        }};

        scepterX = new UnitType("scepter-x") {{
            constructor = UnitEntity::create;

            speed = 0.4f;
            hitSize = 22f;
            rotateSpeed = 2.1f;
            health = 19000;
            armor = 10f;
            mechFrontSway = 1f;
            ammoType = new ItemAmmoType(Items.thorium);

            mechStepParticles = true;
            stepShake = 0.15f;
            singleTarget = true;
            drownTimeMultiplier = 4f;

            BulletType smallBullet = new BasicBulletType(3f, 25) {{
                width = 7f;
                height = 9f;
                lifetime = 70f;
            }};

            weapons.add(
                    new Weapon("scepter-weapon") {{
                        top = false;
                        y = 1f;
                        x = 16f;
                        shootY = 8f;
                        reload = 30f;
                        recoil = 5f;
                        shake = 2f;
                        ejectEffect = Fx.casing3;
                        shootSound = Sounds.bang;
                        inaccuracy = 3f;

//                        shoot.shots = 5;
//                        shoot.shotDelay = 3f;

                        bullet = new BasicBulletType(7f, 50, "shell") {
                            {
                                float inRad = 8 * 1f;
                                float outRad = 8 * 8f;

                                lifetime = 35f;
                                shootEffect = Fx.shootBig;
                                splashDamage = 20;
                                splashDamageRadius = 15f;

                                trailColor = ExPal.lightBlue;
                                trailLength = 15;

                                fragBullet = new BulletType() {
                                    {
                                        speed = 0f;
                                        lifetime = 50f;
                                        hittable = false;
                                        splashDamageRadius = 20f;
                                        splashDamage = 80;
                                    }

                                    @Override
                                    public void draw(Bullet b) {
                                        float in = b.time <= b.lifetime - 72 ?
                                                Math.min(b.time / 60f, 1) :
                                                (b.lifetime - b.time) / 72f;
                                        in = Interp.fastSlow.apply(in);
                                        BlackHoleRenderer.addBlackHole(b.x, b.y, inRad * in, outRad * in, Math.min(1, in + 0.1f));
                                        super.draw(b);
                                    }
                                };
                            }

//                            @Override
//                            public void createFrags(Bullet b, float x, float y) {
//                                fragBullet.create(b, b.x, b.y, 0, 1f, 1f);
//                            }


                            @Override
                            public void hit(Bullet b, float x, float y) {
                                super.hit(b, x, y);
                                fragBullet.create(b, b.x, b.y, 0, 1f, 1f);
                            }
                        };
                    }},

                    new Weapon("mount-weapon") {{
                        reload = 8f;
                        x = 8.5f;
                        y = 6f;
                        rotate = true;
                        ejectEffect = Fx.casing1;
                        bullet = smallBullet;
                    }},
                    new Weapon("mount-weapon") {{
                        reload = 11f;
                        x = 8.5f;
                        y = -7f;
                        rotate = true;
                        ejectEffect = Fx.casing1;
                        bullet = smallBullet;
                    }}
            );

            abilities.add(
                    new ForceFieldAbility(50f, 4f, 3000f, 60f * 2),
                    new RepairFieldAbility(1000f, 60f * 5, 50f)
            );
        }};
    }
}
