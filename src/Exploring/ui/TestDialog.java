package Exploring.ui;

import arc.flabel.FLabel;
import arc.math.Interp;
import arc.math.Mathf;
import arc.scene.actions.Actions;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Time;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

public class TestDialog extends BaseDialog {
    public TestDialog() {
        super("test-dialog");
        buttons.margin(10);
        cont.table(t -> t.pane(p -> {
            p.margin(13f);
            p.left().defaults().left();
            p.setBackground(Styles.black3);

            p.table(stats -> {
                for (int i = 0; i < 6; i++) {
                    addStat(stats, String.valueOf(i + 1), i, 0.05f * i);
                }
            }).top().grow().row();
        }).grow().pad(12).top()).center().minWidth(370).maxSize(600, 550).grow();
        addCloseButton();
    }

    private void addStat(Table parent, String stat, int value, float delay){
        parent.add(new StatLabel(stat, value, delay)).top().pad(5).growX().height(50).row();
    }

    private static class StatLabel extends Table {
        private float progress = 0;

        public StatLabel(String stat, int value, float delay){
            setTransform(true);
            setClip(true);
            setBackground(Tex.whiteui);
            setColor(Pal.accent);
            margin(2f);

            FLabel statLabel = new FLabel("{RAINBOW=1;1;0.7}???" + stat + "???{ENDRAINBOW}{NORMAL}");
            statLabel.setStyle(Styles.outlineLabel);
            statLabel.setWrap(true);
            statLabel.pause();

            Label valueLabel = new Label("", Styles.outlineLabel);
            valueLabel.setAlignment(Align.right);

            add(statLabel).left().growX().padLeft(5);
            add(valueLabel).right().growX().padRight(5);

            actions(
                    Actions.scaleTo(0, 1),
                    Actions.delay(delay),
                    Actions.parallel(
                            Actions.scaleTo(1, 1, 0.3f, Interp.pow3Out),
                            Actions.color(Pal.darkestGray, 0.3f, Interp.pow3Out),
                            Actions.sequence(
                                    Actions.delay(0.3f),
                                    Actions.run(() -> {
                                        valueLabel.update(() -> {
                                            progress = Math.min(1, progress + (Time.delta / 60));
                                            valueLabel.setText(String.valueOf((int) Mathf.lerp(0, value, value < 10 ? progress : Interp.slowFast.apply(progress))));
                                        });
                                        statLabel.resume();
                                    })
                            )
                    )
            );
        }
    }
}
