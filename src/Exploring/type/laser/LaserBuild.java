package Exploring.type.laser;

import Exploring.graphics.ExPal;
import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Puddles;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.world.Tile;

import java.util.Iterator;

import static java.lang.Math.max;

public class LaserBuild extends Building implements Laserc {
    public boolean hasLaser;
    public LaserModule laser = new LaserModule();

    public static void onDestroyedCallback(Building build, LaserModule laser) {
        float power = laser.laserStorage * 47.5f;

        Damage.dynamicExplosion(build.x, build.y, 0, 0, power, (float) (8 * build.block.size) / 2.0f, Vars.state.rules.damageExplosions, build.block.destroyEffect);

        laser.clear();
    }

    @Override
    public void onDestroyed() {
        super.onDestroyed();
        onDestroyedCallback(this, laser);
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
        return hasLaser;
    }

    @Override
    public LaserModule module() {
        return laser;
    }

    public static Bar laserBar(Laserc entity) {
        return new Bar(
                () -> Core.bundle.formatString(
                        "laser:{0}/{1}",
                        ((float) Math.floor(entity.module().laserStorage * 100f)) / 100f,
                        ((float) Math.floor(entity.module().laserStorageCapacity * 100f)) / 100f
                ),
                () -> ExPal.laserBar(Color.red, max(0f, entity.module().overload())),
                () -> entity.module().laserStorage / entity.module().laserStorageCapacity
        );
    }
}
