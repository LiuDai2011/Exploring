package Exploring.content;

import arc.graphics.Color;
import arc.util.Log;
import mindustry.ai.types.BuilderAI;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.content.UnitTypes;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class ExUnitTypes {
    public static UnitType exAlpha, author;

    public static void load() {
        Log.info("Loading units...");

        exAlpha = new UnitType("ex-alpha") {{
            constructor = UnitEntity::create;

            aiController = BuilderAI::new;
            isEnemy = false;

            lowAltitude = true;
            flying = true;
            mineSpeed = 6.5f;
            mineTier = 1;
            buildSpeed = 0.5f;
            drag = 0.05f;
            speed = 3;
            rotateSpeed = 15;
            accel = 0.1f;
            fogRadius = 0;
            itemCapacity = 30;
            health = 150;
            engineOffset = 6;
            hitSize = 8;
            alwaysUnlocked = true;

            weapons.add(new Weapon("ex-small-basic-weapon") {{
                reload = 17.0f;
                x = 2.75f;
                y = 1.0f;
                top = false;
                ejectEffect = Fx.casing1;

                bullet = new BasicBulletType(2.5f, 11) {{
                    width = 7;
                    height = 9;
                    lifetime = 60;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    buildingDamageMultiplier = 0.01f;
                }};
            }});
        }};

        author = new UnitType("LiuDai") {{
            constructor = UnitEntity::create;

            aiController = BuilderAI::new;
            isEnemy = false;

            lowAltitude = true;
            flying = true;
            mineSpeed = 9999999.9f;
            mineTier = 9999;
            buildSpeed = 9999999.9f;
            drag = 0.05f;
            speed = 15f;
            rotateSpeed = 9999999.9f;
            accel = 0.1f;
            fogRadius = 0;
            itemCapacity = 9999999;
            health = 9999999;
            engineOffset = 6;
            hitSize = 10;

            payloadCapacity = 9999999f;

            weapons.add(new Weapon("EX_BOOM!") {{
                reload = 1f;
                x = 2.75f;
                y = 1.0f;
                top = false;
                ejectEffect = Fx.casing1;

                bullet = new BasicBulletType(2.5f, 1100000) {{
                    width = 7;
                    height = 9;
                    lifetime = 200f;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    buildingDamageMultiplier = 0.01f;
                }};
            }});

            weapons.add(new Weapon("miss!-weapon"){{
                top = false;
                y = -2.5f;
                x = 3.75f;
                reload = 1f;
                ejectEffect = Fx.none;
                recoil = 2f;
                shootSound = Sounds.missile;
                velocityRnd = 0.5f;
                inaccuracy = 15f;
                alternate = true;

                bullet = new MissileBulletType(4f, 12){{
                    homingPower = 0.08f;
                    weaveMag = 4;
                    weaveScale = 4;
                    lifetime = 200f;
                    keepVelocity = false;
                    shootEffect = Fx.shootHeal;
                    smokeEffect = Fx.hitLaser;
                    hitEffect = despawnEffect = Fx.hitLaser;
                    frontColor = Color.white;
                    hitSound = Sounds.none;

                    healPercent = 5.5f;
                    collidesTeam = true;
                    backColor = Pal.heal;
                    trailColor = Pal.heal;
                }};
            }});

            weapons.add(new Weapon("a-ba-a-ba-weapon"){{
                top = false;
                y = -2.5f;
                x = 3.75f;
                reload = 1f;
                ejectEffect = Fx.none;
                recoil = 2f;
                velocityRnd = 0.5f;
                inaccuracy = 15f;
                alternate = true;

                bullet = new BasicBulletType() {
                    {
                        damage = 1;
                        buildingDamageMultiplier = 0.2f;
                        speed = 25f;
                        hitShake = 6f;
                        ammoMultiplier = 1f;

                        lifetime = 200f;

                        fragBullets = 5;
                        fragBullet = new BasicBulletType(25f, 1) {
                            {
                                width = 7f;
                                height = 9f;
                                ammoMultiplier = 2;

                                fragBullets = 2;
                                fragBullet = new PointBulletType() {{
                                    shootEffect = Fx.instShoot;
                                    hitEffect = Fx.instBomb;
                                    smokeEffect = Fx.smokeCloud;
                                    trailEffect = Fx.instBomb;
                                    despawnEffect = Fx.instBomb;
                                    trailSpacing = 20f;
                                    damage = 1350;
                                    buildingDamageMultiplier = 0.2f;
                                    speed = 0.5f;
                                    hitShake = 6f;
                                    ammoMultiplier = 1f;
                                    homingPower = 1800f;
                                    homingRange = 1800f;
                                    splashDamageRadius = 80f;
                                    splashDamage = 350f;
                                }};
                            }

                            @Override
                            public void draw(Bullet b) {
                            }
                        };
                    }

                    @Override
                    public void draw(Bullet b) {
                    }
                };
            }});

            abilities.add(new EnergyFieldAbility(35f, 1f, 8000f){{
                statusDuration = 60f * 6f;
                maxTargets = 99;
                healPercent = 30f;
            }});

            abilities.add(new ForceFieldAbility(600f, 100f, 40000f, 1f));

            abilities.add(new RepairFieldAbility(1000f, 1f, 6000f));

            abilities.add(new ShieldRegenFieldAbility(2000f, 40000f, 1f, 6000f));

            abilities.add(new StatusFieldAbility(StatusEffects.invincible, 60f * 2, 20f, 60f));

            //abilities.add(new UnitSpawnAbility(UnitTypes.reign, 300f, 0, 0));
            //abilities.add(new UnitSpawnAbility(UnitTypes.corvus, 300f, 0, 0));
        }};
    }
}
