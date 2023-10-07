package Exploring.type.laser;

import Exploring.ExSettings;
import Exploring.graphics.ExPal;
import Exploring.util.Pair;
import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.core.Renderer;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.Tile;
import mindustry.world.modules.BlockModule;

import static java.lang.Math.min;
import static mindustry.Vars.tilesize;

public class LaserModule extends BlockModule {

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
            boolean frag = true;

            while (true) {
                added.add(out.get(i).getVelocity());
                dis = Mathf.sqrt(Mathf.sqr(added.x) + Mathf.sqr(added.y));

                if (dis > out.get(i).length + 1)
                    break;

                Point2 tpos = pos.cpy().add(added);
                Tile tile = Vars.world.tile(tpos.x, tpos.y);

                if (tile == null)
                    break;

                if (tile.block() instanceof LaserBlock && ((LaserBlock) tile.block()).hasLaser) {
                    if (laserStorage >= out.get(i).amount * Time.delta) {
                        ((LaserBuild) tile.build).laser.add(out.get(i).amount * Time.delta);
                        laserStorage -= out.get(i).amount * Time.delta;
                        frag = false;
                    }
                    break;
                }
            }

            if (frag)
                if (laserStorage >= out.get(i).amount * Time.delta)
                    laserStorage -= out.get(i).amount * Time.delta;
        }

        laser = 0f;
    }

    public void draw(int id, Pair<Float, Float> pos, int size, int rotation) {
        TextureRegion laserR = Core.atlas.find("laser");
        TextureRegion laserEnd = Core.atlas.find("laser-end");

        Draw.z(Layer.power);
        Draw.color(ExPal.laser(min(out.get(id).amount * Time.delta, laserStorage)));
        Draw.alpha(ExSettings.laserOpacity * 0.9f);
        float w = 0.4f + Mathf.absin(7f, 0.05f);

        boolean frag = false;

        Point2 posS = out.get(id).start;
        Point2 added = new Point2(0, 0);
        float dis;

        while (true) {
            added.add(out.get(id).getVelocity());
            dis = Mathf.sqrt(Mathf.sqr(added.x) + Mathf.sqr(added.y));

            if (dis > out.get(id).length)
                break;

            Point2 tpos = posS.cpy().add(added);
            Tile tile = Vars.world.tile(tpos.x, tpos.y);

            if (tile == null)
                break;

            if (tile.block() instanceof LaserBlock && ((LaserBlock) tile.block()).hasLaser) {
                frag = true;

                Point2 point = Geometry.d4(rotation);
                float poff = tilesize / 2f;
                Drawf.laser(laserR, laserEnd, pos.getKey() + poff * size * point.x, pos.getValue() + poff * size * point.y, tile.worldx() - poff * point.x, tile.worldy() - poff * point.y, w);

                break;
            }
        }

        if (!frag) {
            while (Vars.world.tile(added.cpy().add(posS).x, added.cpy().add(posS).y) == null && !(added.x == posS.x && added.y == posS.y)) {
                added.sub(out.get(id).getVelocity());
            }
            if (Vars.world.tile(added.cpy().add(posS).x, added.cpy().add(posS).y) == null) {
                return;
            }
            Tile tile = Vars.world.tile(added.cpy().add(posS).x, added.cpy().add(posS).y);
            Point2 point = Geometry.d4(rotation);
            float poff = tilesize / 2f;
            Drawf.laser(laserR, laserEnd, pos.getKey() + poff * size * point.x, pos.getValue() + poff * size * point.y, tile.worldx() - poff * point.x, tile.worldy() - poff * point.y, w);
        }
    }

    public void clear() {
        out.clear();
    }
}
