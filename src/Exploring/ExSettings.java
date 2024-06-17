package Exploring;

import arc.Core;
import mindustry.ui.dialogs.BaseDialog;

import static Exploring.ExploringMain.toText;
import static mindustry.Vars.ui;

public class ExSettings {
    public static boolean devEnv = false;

    public static BaseDialog tip;

    public static void loadUI() {
        if (ui.settings != null) {
            tip = new BaseDialog("@ex-tip");
            Runnable exit = () -> {
                tip.hide();
                Core.app.exit();
            };
            tip.cont.add(toText("ex-auto-exit"));
            tip.buttons.button(toText("ex-ok"), exit).center().size(150, 50);

            ui.settings.addCategory(toText("ex-set-name"), settingsTable -> {
                settingsTable.checkPref("developer-mode", false, b -> {
                    tip.show();
                });
                settingsTable.checkPref("ex-show-about", true);
            });
        }
    }
}
