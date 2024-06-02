package Exploring.ui;

import Exploring.ExploringMain;
import mindustry.ui.dialogs.BaseDialog;

public class AboutModDialog extends BaseDialog {
    public AboutModDialog() {
        super("about mod");
        cont.add("[blue]ABOUT[]").row();
        cont.add(ExploringMain.ABOUT_MOD).row();
        cont.button("I see", this::hide).size(100f, 50f);
    }
}
