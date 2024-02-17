package Exploring.world.blocks.turrets;

import Exploring.type.laser.LaserBuild;
import Exploring.type.laser.LaserModule;
import Exploring.type.laser.Laserc;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.entities.bullet.BulletType;

public class LaserItemTurret extends ExItemTurret {
    public float laserStorageCapacity = 0f;
    public float laserShootNeeds = 0f;
    public LaserItemTurret(String name) {
        super(name);
        buildType = LaserItemTurretBuild::new;
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("laser", (LaserItemTurretBuild entity) -> LaserBuild.laserBar(entity));
    }

    public class LaserItemTurretBuild extends ExItemTurret.ExItemTurretBuild implements Laserc {
        public LaserModule laser = new LaserModule();

        {
            laser.laserStorageCapacity = laserStorageCapacity;
            laser.storage = true;
        }

        @Override
        public void update() {
            super.update();

            if (laser.overload() >= 1f)
                kill();

            laser.update();
        }

        @Override
        public void read(Reads read) {
            super.read(read);
            laser.read(read);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            laser.write(write);
        }

        @Override
        public boolean hasLaser() {
            return true;
        }

        @Override
        public LaserModule module() {
            return laser;
        }

        @Override
        public void onDestroyed() {
            LaserBuild.onDestroyedCallback(this, laser);
            super.onDestroyed();
        }

        @Override
        protected void shoot(BulletType type) {
            if (laser.laserStorage >= laserShootNeeds) {
                super.shoot(type);
                laser.add(-laserShootNeeds);
            }
        }
    }
}
