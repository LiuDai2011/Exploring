package Exploring.type.laser;

import Exploring.graphics.ExPal;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.graphics.Drawf;
import mindustry.world.Tile;
import mindustry.world.modules.BlockModule;

import static java.lang.Math.min;

public class LaserModule extends BlockModule {
    public static LaserGraph graph;

    public float laser = 0.0f;
    public float laserCapacity = 0.0f;

    public boolean storage = false;
    public float laserStorage = 0.0f;
    public float laserStorageCapacity = 0.0f;

    public Seq<Laser> out = new Seq<>();


    public float overload() {
        return ((laserStorage / laserStorageCapacity) - 1f) * 10f;
    }

    @Override
    public void write(Writes write) {
        write.bool(storage);
    }

    @Override
    public void read(Reads read) {
        storage = read.bool();
    }

    public boolean addOut(Laser laser1) {
        out.add(laser1);
        return true;
    }

    public boolean setOut(int id, Laserp p) {
        try {
            out.set(id, p.get(out.get(id)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void add(float amount) {
        laser += amount;
    }

    public void update() {
        if (!storage) {
            laserStorageCapacity = laserCapacity;
            laserStorage = laser;
        } else {
            laserStorage += laser;
            laserStorage = min(laserStorage, laserStorageCapacity * 1.15f);
        }

        for (int i = 0; i < out.size; i++) {
            Point2 pos = out.get(i).start;
            Point2 added = new Point2(0, 0);
            float dis;

            do {
                dis = Mathf.sqrt(Mathf.sqr(added.x) + Mathf.sqr(added.y));
                added.add(out.get(i).getVelocity());

                Point2 tpos = pos.cpy().add(added);
                Tile tile = Vars.world.tile(tpos.x, tpos.y);

                if (tile == null)
                    break;

                if (tile.block() instanceof LaserBlock && ((LaserBlock) tile.block()).hasLaser) {
                    if (laserStorage >= out.get(i).amount) {
                        ((LaserBuild) tile.build).laser.add(out.get(i).amount);
                        laserStorage -= out.get(i).amount;
                    }
                    break;
                }
            } while (dis <= out.get(i).length);
        }

        laser = 0f;
    }

    public void clear() {
        out.clear();
    }
}
