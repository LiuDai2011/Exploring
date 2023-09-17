package Exploring.world.blocks;

public class RusttableWall extends ExWall {
    public RusttableWall(String name) {
        super(name);
        buildType = RusttableWallBuild::new;
    }

    public class RusttableWallBuild extends ExWallBuild {

    }
}
