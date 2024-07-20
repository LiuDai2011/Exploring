package Exploring.ui;

import arc.Core;
import mindustry.ui.dialogs.BaseDialog;

import static Exploring.ExploringMain.aboutMod;
import static Exploring.ExploringMain.toText;

public class AboutModDialog extends BaseDialog {
    public AboutModDialog() {
        super("@ex-about-mod-text");
        cont.add(aboutMod()).row();
        buttons.check(toText("ex-not-show-about-next"), b -> {
            Core.settings.put("ex-show-about", !b);
        }).center();
        addCloseButton();
    }
}
