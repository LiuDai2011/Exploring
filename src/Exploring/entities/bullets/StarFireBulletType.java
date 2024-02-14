package Exploring.entities.bullets;

import arc.math.geom.Geometry;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.entities.Units;
import mindustry.entities.bullet.PointBulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Unit;

public class StarFireBulletType extends PointBulletType {
    private float cdist_;
    private Unit result_;

    @Override
    public void init(Bullet b){
        super.init(b);

        float px = b.x + b.lifetime * b.vel.x,
                py = b.y + b.lifetime * b.vel.y,
                rot = b.rotation();

        Geometry.iterateLine(0f, b.x, b.y, px, py, trailSpacing, (x, y) -> {
            trailEffect.at(x, y, rot);
        });

        b.time = b.lifetime;
        b.set(px, py);

        //calculate hit entity

        cdist_ = 0f;
        result_ = null;
        float range = 1f;

        Units.nearbyEnemies(b.team, px - range, py - range, range*2f, range*2f, e -> {
            if(e.dead() || !e.checkTarget(collidesAir, collidesGround) || !e.hittable()) return;

            e.hitbox(Tmp.r1);
            if(!Tmp.r1.contains(px, py)) return;

            float dst = e.dst(px, py) - e.hitSize;
            if((result_ == null || dst < cdist_)){
                result_ = e;
                cdist_ = dst;
            }
        });

        if(result_ != null){
            b.collision(result_, px, py);
        }else if(collidesTiles){
            Building build = Vars.world.buildWorld(px, py);
            if(build != null && build.team != b.team){
                build.collision(b);
            }
        }

        createFrags(b, b.x, b.y);

        b.remove();

        b.vel.setZero();
    }
}
