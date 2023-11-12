package Exploring.type.laser;

import Exploring.graphics.ExPal;
import Exploring.util.Pair;
import arc.Core;
import arc.graphics.Color;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.util.Time;
import mindustry.ui.Bar;

import static java.lang.Math.max;

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

        addBar("laser", (LaserBuild entity) -> new Bar(
                        () -> Core.bundle.formatString(
                                "laser:{0}/{1}",
                                ((float) Math.floor(entity.laser.laserStorage * 100f)) / 100f,
                                ((float) Math.floor(entity.laser.laserStorageCapacity * 100f)) / 100f
                        ),
                        () -> ExPal.laserBar(Color.red, max(0f, entity.laser.overload())),
                        () -> entity.laser.laserStorage / entity.laser.laserStorageCapacity
                )
        );
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
                laser.draw(i, new Pair<>(x, y), size, rotation());
            }
        }
    }
}
