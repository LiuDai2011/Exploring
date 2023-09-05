package Exploring.content;

//import mindustry.annotations.Annotations.*;
import mindustry.ai.types.BuilderAI;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class ExUnitTypes {
    public static /*@EntityDef(value = {Unitc.class}, legacy = true)*/ UnitType exAlpha;

    public static void load() {
        exAlpha = new UnitType("ex-alpha"){{
            aiController = BuilderAI::new;
            isEnemy = false;

            lowAltitude = true;
            flying = true;
            mineSpeed = 6.5f;
            mineTier = 1;
            buildSpeed = 0.5f;
            drag = 0.05f;
            speed = 3f;
            rotateSpeed = 15f;
            accel = 0.1f;
            itemCapacity = 30;
            health = 150f;
            engineOffset = 6f;
            hitSize = 8f;
            alwaysUnlocked = true;

            weapons.add(new Weapon("ex-small-basic-weapon"){{
                reload = 17f;
                x = 2.75f;
                y = 1f;
                top = false;
                ejectEffect = Fx.casing1;

                bullet = new BasicBulletType(2.5f, 11){{
                    width = 7f;
                    height = 9f;
                    lifetime = 60f;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    buildingDamageMultiplier = 0.01f;
                }};
            }});
        }};
    }
}
