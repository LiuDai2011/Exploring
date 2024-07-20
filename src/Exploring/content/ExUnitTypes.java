package Exploring.content;

import Exploring.ExploringMain;
import Exploring.graphics.ExColor;
import Exploring.math.MathDef;
import Exploring.world.entities.units.DPSUnit;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import mindustry.ai.types.SuicideAI;
import mindustry.content.Fx;
import mindustry.entities.abilities.ShieldRegenFieldAbility;
import mindustry.gen.EntityMapping;
import mindustry.gen.MechUnit;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.ui.Bar;

public class ExUnitTypes {
    public static UnitType singularityPutter, dpsUnitGround, dpsUnitFly;

    static {
        EntityMapping.nameMap.put(ExploringMain.name("singularity-putter"), MechUnit::create);
        EntityMapping.nameMap.put(ExploringMain.name("dps-unit-ground"), DPSUnit::new);
        EntityMapping.nameMap.put(ExploringMain.name("dps-unit-fly"), DPSUnit::new);
    }

    public static void load() {
        singularityPutter = new UnitType("singularity-putter") {{
            constructor = EntityMapping.idMap[4];
            aiController = SuicideAI::new;

            speed = 1f;
            hitSize = 8f;
            health = 2000;
            armor = 50;
            mechSideSway = 0.25f;
            range = 64f;
            ammoType = new ItemAmmoType(ExItems.singularityEncapsulation);

            weapons.add(new Weapon() {{
                shootOnDeath = true;
                reload = 1f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                x = shootY = 0f;
                mirror = false;
                bullet = ExBulletTypes.smallSingularity;
            }});

            abilities.add(new ShieldRegenFieldAbility(10000, 100000, 30f, 1));
        }};

        dpsUnitGround = new DPSUnitType("dps-unit-ground", false);
        dpsUnitFly = new DPSUnitType("dps-unit-fly", true);
    }

    public static class DPSUnitType extends UnitType {
        public DPSUnitType(String name, boolean fly) {
            super(name);
            health = Integer.MAX_VALUE;
            speed = 18;
            constructor = DPSUnit::new;
            flying = fly;
            canDrown = false;
        }

        @Override
        public void display(Unit unit, Table table) {
            super.display(unit, table);
            DPSUnit dpsUnit = (DPSUnit) unit;
            table.table(bars -> {
                bars.defaults().growX().height(20f).pad(4);

                bars.add(new Bar(
                        () -> "TD " + MathDef.exactlyRound(dpsUnit.damageTotal, 10),
                        () -> Color.red,
                        () -> 1f
                ).blink(Color.white));
                bars.row();

                bars.add(new Bar(
                        () -> "DPS " + MathDef.exactlyRound(dpsUnit.dps(), 10),
                        () -> ExColor.lightBlue,
                        () -> 1f
                ).blink(Color.white));
                bars.row();

                bars.add(new Bar(
                        () -> "length " + dpsUnit.seq.size,
                        () -> ExColor.lightBlue,
                        () -> dpsUnit.seq.size / 10000f
                ).blink(Color.white));
                bars.row();
            }).growX();
        }
    }
}
