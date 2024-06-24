package Exploring;

import Exploring.content.ExContent;
import Exploring.graphics.BlackHoleRenderer;
import Exploring.ui.AboutModDialog;
import Exploring.ui.TodoListDialog;
import arc.Core;
import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.EntityMapping;
import mindustry.mod.Mod;
import mindustry.mod.Mods;

import static arc.Core.settings;
import static mindustry.Vars.ui;

public class ExploringMain extends Mod {
    public static final String MOD_NAME = "exploring";
    public static final String MOD_NAME_BAR = "exploring-";
    public static final String MOD_REPO = "LiuDai2011/Exploring";
    public static final String MOD_RELEASES = "https://github.com/LiuDai2011/Exploring/releases";
    public static final String MOD_GITHUB_URL = "https://github.com/LiuDai2011/Exploring.git";

    public static Mods.LoadedMod MOD;

    public ExploringMain() {
        Log.info("run 'public ExploringMain()'");

        Events.on(EventType.ClientLoadEvent.class, e -> {
            if (settings.getBool("ex-show-about", true))
                new AboutModDialog().show();
            if (settings.getBool("developer-mode", false))
                new TodoListDialog().show();
        });
    }

    public static String name(String name) {
        return MOD_NAME + "-" + name;
    }

    public static String toText(String key) {
        return Core.bundle.format(key);
    }

    public static String AboutMod() {
        return toText("ex-about-mod");
    }

    @Override
    public void init() {
        settings.defaults("developer-mode", false);
        settings.defaults("ex-show-about", true);
        settings.defaults("ex-full-fx", true);

        BlackHoleRenderer.init();

        if (ui != null) {
            ExSettings.loadUI();
        }
    }

    @Override
    public void loadContent() {
        Log.info("load content");

        MOD = Vars.mods.getMod(getClass());

        ExSettings.devEnv = settings.getBool("developer-mode", false);
        ExSettings.fullFx = settings.getBool("ex-full-fx", false);

        ExContent.load();

        settings.put("ex-full-fx", false);
    }
}
