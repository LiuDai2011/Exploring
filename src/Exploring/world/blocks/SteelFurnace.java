package Exploring.world.blocks;

public class SteelFurnace extends ExGenericCrafter{
    public SteelFurnace(String name) {
        super(name);
        buildType = SteelFurnaceBuild::new;
    }

    public class SteelFurnaceBuild extends ExGenericCrafterBuild {

    }
}
