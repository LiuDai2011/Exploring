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
    /*
     TODO: Update version
     TODO: EnergyBall
     TODO: RusttableWall
     TODO: molecularWeight
     TODO: Unit
     TODO: 研究中心
     TODO: Logic
     TODO: Liquids
     TODO: Formula
     TODO: Laser
     TODO: Dyson Sphere
     TODO: BOSS S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S S!
     TODO: ...
    */

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
        Log.info("Loaded Exploring constructor.");

        Events.on(EventType.ClientLoadEvent.class, e -> {
            ExVars.init();
            ExVars.techUI.show();
        });

        AboutModDialog.show();
    }

    public static String name(String name) {
        return MOD_NAME + "-" + name;
    }

    @Override
    public void loadContent() {
        Log.info("Loading Exploring content.");

        Log.info("Loading Exploring Mod Object.");

        MOD = Vars.mods.getMod(getClass());

//        ExShaders.init();

        ExSettings.load();

        ExContent.loadPriority();

        ExContent.loadVars();
    }
}
