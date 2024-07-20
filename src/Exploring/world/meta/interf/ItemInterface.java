package Exploring.world.meta.interf;

import Exploring.world.util.Change;
import arc.Core;
import arc.graphics.Color;
import mindustry.graphics.MultiPacker;

public abstract class ItemInterface extends Interface {
    public ItemInterface(String name, Color color) {
        super(name, color);
    }

    public ItemInterface(String name) {
        super(name);
    }

    @Override
    public void createIcons(MultiPacker packer) {
        super.createIcons(packer);
        Change.color(Core.atlas.getPixmap(fullIcon), color);
//        Change.color(Core.atlas.getPixmap(uiIcon), color);
    }

    // TODO maybe
}
