package Exploring.io;

import arc.Core;
import arc.Events;
import arc.scene.style.Drawable;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.ui.dialogs.BaseDialog;

public class AboutModDialog {
    public static void show() {
        Events.on(EventType.ClientLoadEvent.class, e -> Time.runTask(10f, () -> {
            BaseDialog dialog = new BaseDialog("");
            dialog.cont.add("behold").row();
            dialog.cont.button("I see", dialog::hide).size(100f, 50f);
            dialog.cont.button((Drawable) Core.atlas.getDrawable("exploring-imgtest"), dialog::hide);
            dialog.show();
        }));
    }
}
