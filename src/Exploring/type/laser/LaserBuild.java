package Exploring.type.laser;

import mindustry.gen.Building;

public class LaserBuild extends Building {
    public LaserModule laser = new LaserModule();

    @Override
    public void onDestroyed() {
        laser.clear();
        super.onDestroyed();
    }

    @Override
    public void update() {
        super.update();

        if (laser.overload() >= 1f)
            killed();

        laser.update();
    }
}
