package Exploring.world.entities.abilities;

import Exploring.content.ExBulletTypes;
import Exploring.content.ExItems;
import Exploring.world.meta.ExStatValues;
import arc.Core;
import arc.scene.ui.layout.Collapser;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import arc.util.Strings;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Icon;
import mindustry.ui.Styles;

import static Exploring.ExploringMain.toText;
import static mindustry.Vars.tilesize;

public class SelfbombAbility extends Ability {
    public float mul = 1f, rad = 0f;


    public SelfbombAbility(float mul, float rad) {
        this.mul = mul;
        this.rad = rad;
    }

    @Override
    public void addStats(Table t) {
        t.add("[lightgray]" + toText("stat.self-bomb") + "[white]").row();
        t.table(table -> {
            table.row();
            table.table(Styles.grayPanel, bt -> {
                bt.left().top().defaults().padRight(3).left().row();

                Table fc = new Table();

                fc.table(ft -> {
                    ft.row();
                    ft.table(Styles.grayPanel, bft -> {
                        bft.left().top().defaults().padRight(3).left().row();

                        bft.add(Core.bundle.format("bullet.splashdamage",
                                Core.bundle.format("stat.self-bomb-bullet-damage", mul),
                                Strings.fixed(rad / tilesize, 1))).row();

                        Table fbc = new Table();
                        ExStatValues.ammo(ObjectMap.of(ExItems.singularityEncapsulation, ExBulletTypes.reignXDead)).display(fbc);
                        Collapser coll = new Collapser(fbc, true);
                        coll.setDuration(0.1f);

                        bft.table(fb -> {
                            fb.left().defaults().left();

                            fb.add(Core.bundle.format("stat.self-bomb-singularity"));
                            fb.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                        });
                        bft.row();
                        bft.add(coll);
                    }).padLeft(5).padTop(5).padBottom(0).growX().margin(0);
                });

                Collapser coll = new Collapser(fc, true);
                coll.setDuration(0.1f);

                bt.table(ft -> {
                    ft.left().defaults().left();

                    ft.add(toText("stat.self-bomb-bullet"));
                    ft.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                });
                bt.row();
                bt.add(coll);
            }).padLeft(0).padTop(5).padBottom(0).growX().margin(0);
        });
        t.row();
    }
}
