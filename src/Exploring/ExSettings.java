package Exploring;

import Exploring.content.ExOverride;
import arc.Core;
import arc.scene.Element;
import arc.scene.ui.Dialog;
import arc.scene.ui.TextButton;
import arc.struct.Seq;
import arc.struct.SnapshotSeq;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.SettingsMenuDialog;

import static Exploring.ExploringMain.info;
import static Exploring.ExploringMain.toText;
import static arc.Core.bundle;
import static mindustry.Vars.ui;

public class ExSettings {
    public static boolean devEnv = false,
            fullFx = true,
            overrideDeveloper = false,
            overrideServer = false,
            overrideUI = false,
            autoAccept = false;
    public static String overrideTag = "";
    public static boolean needShowTip = false;

    public static BaseDialog tip;

    public static BaseDialog tip() {
        if (tip != null) return tip;
        tip = new BaseDialog("@ex-tip") {
            @Override
            public Dialog show() {
                if (ExSettings.autoAccept) {
                    info("exit.");
                    Core.app.exit();
                }
                return super.show();
            }
        };
        Runnable exit = () -> {
            tip.hide();
            info("exit.");
            Core.app.exit();
        };
        tip.cont.add(toText("ex-auto-exit"));
        tip.buttons.button(toText("ex-ok"), exit).center().size(150, 50);
        tip.addCloseListener();
        return tip;
    }

    public static void loadUI() {
        if (ui.settings != null) {

            ui.settings.addCategory(toText("ex-set-name"), settingsTable -> {
                settingsTable.checkPref("developer-mode", false, b -> {
                    tip().show();
                });
                settingsTable.checkPref("ex-show-about", true);
                settingsTable.checkPref("ex-tip-auto-accept", false, b -> autoAccept = b);
                settingsTable.checkPref("ex-override-server", false, b -> {
                    if (b) ExOverride.loadServers();
                    else tip().show();
                    overrideServer = b;
                });
                settingsTable.checkPref("ex-override-ui", false, b -> {
                    if (b) Core.settings.put("mod-" + ExploringMain.MOD.meta.name + "-orui-enabled", true);
                    else Core.settings.put("mod-" + ExploringMain.MOD.meta.name + "-orui-enabled", false);
                    tip().show();
                });
                if (!devEnv) return;
                settingsTable.checkPref("ex-override-developer", false, b -> {
                    if (b) ExOverride.overrideDeveloper();
                    else tip().show();
                    overrideDeveloper = b;
                });
//                ExStatValues.ammo(ObjectMap.of(ExExtraUnitTypes.daggerX, ((ItemTurret) Blocks.cyclone).ammoTypes.get(Items.plastanium))).display(settingsTable);
                // TODO fx
            });

            Seq<SettingsMenuDialog.SettingsCategory> categories = ui.settings.getCategories();
            for (int i = 0; i < categories.size; i++) {
                SettingsMenuDialog.SettingsCategory category = categories.get(i);
                if (category.name.equals(toText("ex-set-name"))) {
                    SettingsMenuDialog.SettingsTable settingsTable = category.table;
                    SnapshotSeq<Element> children = settingsTable.getChildren();
                    for (int j = 0; j < children.size; j++) {
                        Element element = children.get(j);
                        if (element instanceof TextButton textButton &&
                                textButton.getLabel().getText().toString().equals(bundle.get("settings.reset", "Reset to Defaults"))
                        ) {
                            children.remove(j);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
}
