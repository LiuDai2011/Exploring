package Exploring;

import Exploring.content.ExContent;
import Exploring.io.AboutModDialog;
import arc.util.Log;
import mindustry.mod.Mod;
import mindustry.mod.Mods;

public class ExploringMain extends Mod {
    // TODO: 2023/7/26 (#1#) Update version

    public static final String MOD_NAME = "exploring";
    public static final String MOD_NAME_BAR = "exploring-";
    public static final String MOD_REPO = "LiuDai2011/Exploring";
    public static final String MOD_RELEASES = "https://github.com/LiuDai2011/Exploring/releases";
    public static final String MOD_GITHUB_URL = "https://github.com/LiuDai2011/Exploring.git";

    public static Mods.LoadedMod MOD;

//    static {
//        EntityMapping.nameMap.put(name());
//    }

    public static String name(String name){
        return MOD_NAME + "-" + name;
    }
    
    public ExploringMain() {
        Log.info("Loaded Exploring constructor.");

        AboutModDialog.show();
    }

    @Override
    public void loadContent() {
        Log.info("Loading Exploring content.");

//        ExContent.loadBLC();

        ExContent.load();
    }
}
