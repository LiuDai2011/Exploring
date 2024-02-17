package Exploring.type.laser;

import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.util.Time;

public class TestLaserBlock extends LaserBlock {
    public float laserP = 0f;

    public TestLaserBlock(String name) {
        super(name);
        buildType = TestLaserBuild::new;
        hasLaser = true;
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("laser", (LaserBuild entity) -> LaserBuild.laserBar(entity));
    }

    public class TestLaserBuild extends LaserBuild {
        private boolean setup = false;

        {
            hasLaser = true;
            laser.laserStorageCapacity = 200f;
            laser.storage = true;
        }

        @Override
        public void update() {
            super.update();
            laser.laserStorage += laserP * Time.delta;

            if (!setup && laserP != 0) {
                setup = true;
                laser.addOut(new Laser(new Point2(tile.x, tile.y), Geometry.d4(rotation()), 0.5f, 5f));
            }
        }

        @Override
        public void draw() {
            super.draw();

            for (int i = 0; i < laser.out.size; i++) {
                laser.draw(i, new Vec2(x, y), size, rotation());
            }
        }
    }
}
