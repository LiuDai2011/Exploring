package Exploring;

import Exploring.content.*;
import Exploring.io.AboutModDialog;
import arc.util.Log;
import mindustry.mod.Mod;

public class ExploringMain extends Mod {
    // TODO: 2023/7/26 (#1#) Update version 
    
    public ExploringMain() {
        Log.info("Loaded Exploring constructor.");

        AboutModDialog.show();
    }

    @Override
    public void loadContent() {
        Log.info("Loading Exploring content.");

        ExStatusEffects.load();
        ExLiquids.load();
        ExItems.load();
//        ExUnitTypes.load();
        ExBlocks.load();
        ExPlanets.load();
        ExSectorPresets.load();
        ExSerpuloTechTree.load();
    }
}
