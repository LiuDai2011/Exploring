package Exploring;

import Exploring.content.ExContent;
import Exploring.content.ExTeam;
import Exploring.graphics.ExColor;
import Exploring.graphics.ExRenderer;
import Exploring.math.MathDef;
import Exploring.ui.AboutModDialog;
import Exploring.ui.ExContentInfoDialog;
import Exploring.ui.TestDialog;
import Exploring.ui.TodoListDialog;
import arc.Core;
import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.mod.Mods;

import static Exploring.content.ExOverride.removeImmunities;
import static arc.Core.bundle;
import static arc.Core.settings;
import static mindustry.Vars.ui;

public class ExploringMain extends Mod {
    public static final String MOD_NAME = "exploring";
    public static final String MOD_NAME_BAR = MOD_NAME + "-";
    public static final String MOD_REPO = "LiuDai2011/Exploring";
    public static final String MOD_RELEASES = "https://github.com/LiuDai2011/Exploring/releases";
    public static final String MOD_GITHUB_URL = "https://github.com/LiuDai2011/Exploring.git";

    public static Mods.LoadedMod MOD;

    public ExploringMain() {
        info("run 'public ExploringMain()'");

//        Log.level = Log.LogLevel.debug;
//        info("Log level set to debug.");

        Events.on(EventType.ClientLoadEvent.class, e -> {
            removeImmunities();
            if (settings.getBool("ex-show-about", true))
                new AboutModDialog().show();
            if (settings.getBool("developer-mode", false))
                new TodoListDialog().show();
            new TestDialog().show();
        });
        Events.on(EventType.WorldLoadEvent.class, e -> {
            Vars.state.teams.get(ExTeam.abyss).unitCap = Integer.MAX_VALUE;
        });
    }

    public static void info(String text, Object... args) {
        Log.info("[" + MOD_NAME + "] " + text, args);
    }

    public static void info(Object object) {
        info(String.valueOf(object), MathDef.empty);
    }

    public static void err(String text, Object... args) {
        Log.err("[" + MOD_NAME + "] " + text, args);
    }

    public static void err(Object object) {
        err(String.valueOf(object), MathDef.empty);
    }

    public static String name(String name) {
        return MOD_NAME + "-" + name;
    }

    public static String toText(String key) {
        return Core.bundle.format(key);
    }

    public static String aboutMod() {
        return toText("ex-about-mod");
    }

    @Override
    public void init() {
        settings.defaults("developer-mode", false);
        settings.defaults("ex-show-about", true);
        settings.defaults("ex-full-fx", true);
        settings.defaults("ex-override", false);
        settings.defaults("ex-override-server", false);

        ExRenderer.init();

        if (ui != null) {
            ui.content = new ExContentInfoDialog();
            ExSettings.loadUI();
        }
    }

    @Override
    public void loadContent() {
        info("load content");

        MOD = Vars.mods.getMod(getClass());

        MOD.meta.displayName = bundle.get("mod.exploring.display-name");
        MOD.meta.author = "[#" + ExColor.author + "]LiuDai[]";
        MOD.meta.description = bundle.get("mod.exploring.description");

        MOD.setRepo(MOD_REPO);

        ExSettings.devEnv = settings.getBool("developer-mode", false);
        ExSettings.fullFx = settings.getBool("ex-full-fx", false);
        ExSettings.overrideDeveloper = settings.getBool("ex-override-developer", false);
        ExSettings.overrideServer = settings.getBool("ex-override-server", false);

        ExSettings.overrideTag = "\n[#" + ExColor.author.toString() + "]Override by Exploring mod.[]";

        ExContent.load();
    }
}
