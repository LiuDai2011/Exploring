package Exploring;

import Exploring.content.ExOverride;
import arc.Core;
import mindustry.ui.dialogs.BaseDialog;

import static Exploring.ExploringMain.toText;
import static mindustry.Vars.ui;

public class ExSettings {
    public static boolean devEnv = false, fullFx = true, overrideDeveloper = false, overrideServer = false;
    public static String overrideTag = "";

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
                settingsTable.checkPref("ex-override-server", false, b -> {
                    if (b) ExOverride.loadServers();
                    else tip.show();
                });
                if (!devEnv) return;
                settingsTable.checkPref("ex-override-developer", false, b -> {
                    if (b) ExOverride.overrideDeveloper();
                    else tip.show();
                });
//                ExStatValues.ammo(ObjectMap.of(ExExtraUnitTypes.daggerX, ((ItemTurret) Blocks.cyclone).ammoTypes.get(Items.plastanium))).display(settingsTable);
                // TODO fx
            });

//            Seq<SettingsMenuDialog.SettingsCategory> categories = ui.settings.getCategories();
//            for (int i = 0; i < categories.size; i++) {
//                SettingsMenuDialog.SettingsCategory category = categories.get(i);
//                if (category.name.equals(toText("ex-set-name"))) {
//                    SettingsMenuDialog.SettingsTable settingsTable = category.table;
//                    SnapshotSeq<Element> children = settingsTable.getChildren();
//                    for (int j = 0; j < children.size; j++) {
//                        Element element = children.get(j);
//                        if (element instanceof TextButton textButton &&
//                                textButton.getLabel().getText().toString().equals(bundle.get("settings.reset", "Reset to Defaults"))
//                        ) {
//                            children.remove(j);
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }
        }
    }
}
