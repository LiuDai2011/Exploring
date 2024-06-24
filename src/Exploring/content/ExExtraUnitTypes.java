package Exploring.content;

import Exploring.graphics.ExPal;
import Exploring.world.entities.abilities.NonurgentRepairAbility;
import Exploring.world.entities.abilities.SelfbombAbility;
import Exploring.world.entities.abilities.UrgentRepairAbility;
import Exploring.world.entities.bullets.BlackHoleBulletType;
import Exploring.world.entities.units.ReignXEntity;
import Exploring.world.meta.ExStatValues;
import arc.Core;
import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.Strings;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.StatusEffects;
import mindustry.entities.abilities.Ability;
import mindustry.entities.abilities.ForceFieldAbility;
import mindustry.entities.abilities.RepairFieldAbility;
import mindustry.entities.abilities.ShieldRegenFieldAbility;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.ui.Bar;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class ExExtraUnitTypes {
    public static UnitType daggerX, maceX, fortressX, scepterX, reignX;

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
                bullet = ExBulletTypes.smallEnergyShell;
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
                        range = 20f;

                        shake = 4f;
                        shootY = 9f;
                        x = 4f;
                        y = 0f;
                        rotateSpeed = 2f;
                        reload = 13f;
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
                            pierce = true;
                            pierceBuilding = true;
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
            armor = 16f;
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
                    new Weapon("scepter-weapon") {
                        {
                            top = false;
                            y = 1f;
                            x = 16f;
                            shootY = 8f;
                            reload = 60f;
                            recoil = 5f;
                            shake = 2f;
                            ejectEffect = Fx.casing3;
                            shootSound = Sounds.bang;
                            inaccuracy = 3f;

                            bullet = new BasicBulletType(7f, 50, "shell") {
                                {
                                    lifetime = 35f;
                                    shootEffect = Fx.shootBig;
                                    splashDamage = 20;
                                    splashDamageRadius = 15f;

                                    trailColor = ExPal.lightBlue;
                                    trailLength = 15;

                                    despawnEffect = healEffect = Fx.none;

                                    fragBullet = new BlackHoleBulletType() {{
                                        inRad = 3 * 1.8f;
                                        outRad = 3 * 8f;

                                        lifetime = 180f;
                                        blackHoleDamage = 0.3f;
                                        blackHoleDamageRadius = 20f;
                                    }};
                                }

                                @Override
                                public void hit(Bullet b, float x, float y) {
                                    super.hit(b, x, y);
                                    fragBullet.create(b, b.x, b.y, 0, 1f, 1f);
                                }
                            };
                        }

                        @Override
                        public void addStats(UnitType u, Table t) {
                            if (inaccuracy > 0) {
                                t.row();
                                t.add("[lightgray]" + Stat.inaccuracy.localized() + ": [white]" + (int) inaccuracy + " " + StatUnit.degrees.localized());
                            }
                            if (!alwaysContinuous && reload > 0) {
                                t.row();
                                t.add("[lightgray]" + Stat.reload.localized() + ": " + (mirror ? "2x " : "") + "[white]" + Strings.autoFixed(60f / reload * shoot.shots, 2) + " " + StatUnit.perSecond.localized());
                            }

                            ExStatValues.ammo(ObjectMap.of(u, bullet)).display(t);
                        }
                    },

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
                    new ForceFieldAbility(50f, 4f, 4000f, 60f * 2),
                    new RepairFieldAbility(1000f, 60f * 0.5f, 50f)
            );
        }};

        reignX = new UnitType("reign-x") {
            {
                constructor = ReignXEntity::new;

                speed = 0.4f;
                hitSize = 26f;
                rotateSpeed = 1.65f;
                health = 53000;
                armor = 30f;
                mechStepParticles = true;
                stepShake = 0.75f;
                drownTimeMultiplier = 6f;
                mechFrontSway = 1.9f;
                mechSideSway = 0.6f;
                ammoType = new ItemAmmoType(Items.thorium);

                weapons.add(
                        new Weapon("reign-weapon") {{
                            top = false;
                            y = 1f;
                            x = 21.5f;
                            shootY = 11f;
                            reload = 2f;
                            recoil = 5f;
                            shake = 2f;
                            ejectEffect = Fx.casing4;
                            shootSound = Sounds.bang;

                            bullet = new BasicBulletType(13f, 100) {{
                                pierce = true;
                                pierceCap = 10;
                                width = 14f;
                                height = 33f;
                                lifetime = 30f;
                                shootEffect = Fx.shootBig;
                                fragVelocityMin = 0.4f;

                                hitEffect = Fx.blastExplosion;
                                splashDamage = 180f;
                                splashDamageRadius = 40f;

                                fragBullets = 3;
                                fragLifeMin = 0f;
                                fragRandomSpread = 30f;

                                fragBullet = new BasicBulletType(9f, 20) {{
                                    width = 10f;
                                    height = 10f;
                                    pierce = true;
                                    pierceBuilding = true;
                                    pierceCap = 3;

                                    lifetime = 50f;
                                    hitEffect = Fx.flakExplosion;
                                    splashDamage = 50f;
                                    splashDamageRadius = 30f;
                                }};
                            }};
                        }}
                );

                abilities.add(new Ability[]{
                        new ForceFieldAbility(64, 2000, 15000, 60f),
                        new RepairFieldAbility(1000, 30f, 32),
                        new ShieldRegenFieldAbility(1000, 20000, 60f * 2, 64),
                        new UrgentRepairAbility(),
                        new NonurgentRepairAbility(),
                        new SelfbombAbility(ReignXEntity.destroyMulti, ReignXEntity.destroyRad)
                });
            }

            @Override
            public void display(Unit unit, Table table) {
                super.display(unit, table);
                ReignXEntity unitR = (ReignXEntity) unit;
                table.table(bars -> {
                    bars.defaults().growX().height(20f).pad(4);
                    bars.add(new Bar(
                            () -> Core.bundle.get("bar.reign-x-urgent-timer"),
                            () -> unitR.cooldown ? Color.white : ExPal.lightBlue,
                            () -> unitR.urgentTimer / ReignXEntity.timerMax
                    ));
                    bars.row();
                }).growX();
            }
        };
    }
}
