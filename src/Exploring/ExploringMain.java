package Exploring;

import Exploring.content.ExContent;
import Exploring.ui.AboutModDialog;
import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.mod.Mod;
import mindustry.mod.Mods;

public class ExploringMain extends Mod {
    public static final String MOD_NAME = "exploring";
    public static final String MOD_NAME_BAR = "exploring-";
    public static final String MOD_REPO = "LiuDai2011/Exploring";
    public static final String MOD_RELEASES = "https://github.com/LiuDai2011/Exploring/releases";
    public static final String MOD_GITHUB_URL = "https://github.com/LiuDai2011/Exploring.git";
    public static final String ABOUT_MOD = """
            QQ Group: 901923295
            QQ Group2: 915241460
                        
            QQ\u7fa4\uff1a901923295
            QQ\u5f00\u53d1\u7fa4\uff1a 915241460
            """;

    public static Mods.LoadedMod MOD;

//    static {
//        EntityMapping.nameMap.put(name());
//    }

    public ExploringMain() {
        Log.info("run 'public ExploringMain()'");

        Events.on(EventType.ClientLoadEvent.class, e -> {
            new AboutModDialog().show();
        });
    }

    public static String name(String name) {
        return MOD_NAME + "-" + name;
    }

    @Override
    public void loadContent() {
        Log.info("load content");

        MOD = Vars.mods.getMod(getClass());

        ExContent.load();
    }
}
