package Exploring.content;

import Exploring.ExploringMain;
import Exploring.world.entities.units.ReignXEntity;
import mindustry.gen.EntityMapping;

public class ExUnitTypes {
    static {
        EntityMapping.nameMap.put(ExploringMain.name("reign-x"), ReignXEntity::new);
    }

    public static void load() {

    }
}
