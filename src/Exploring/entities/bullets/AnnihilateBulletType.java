package Exploring.entities.bullets;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.entities.Mover;
import mindustry.entities.bullet.BulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.graphics.Drawf;

import java.util.HashMap;

public class AnnihilateBulletType extends BulletType {
    public static HashMap<Bullet, Seq<Bullet>> subs = new HashMap<>();

    public AnnihilateBulletType(float damage) {
        super(0f, damage);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        if (subs.get(b).size == 0)
        if (b instanceof AnnihilateBullet annihilateBullet) {
            Vec2 p1 = position(b.time, annihilateBullet.i, b.rotation());
            Vec2 p2 = position(annihilateBullet.t, annihilateBullet.i, b.rotation());
            b.move(p1.x - p2.x, p1.y - p2.y);
            Log.info(p1.x + ", " + p1.y);
            annihilateBullet.t = b.time;
        }
    }

    public static Vec2 position(float t, float i, float r) {
        float y = Mathf.log(1.1f, t) * i;
        float dis = Mathf.sqrt(t * t + y * y);
        float a = Mathf.atan2(t, y) + Mathf.degRad * r;
        float ax = Mathf.cos(a) * dis;
        float ay = Mathf.sin(a) * dis;
        return new Vec2(ax, ay);
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        Drawf.circles(b.x, b.y, 15f, Color.valueOf("00ffff"));
        Drawf.light(b.x, b.y, 25f, Color.white, 1f);
        Draw.reset();
    }

    @Override
    public Bullet create(Entityc owner, Entityc shooter, Team team, float x, float y, float angle, float damage, float velocityScl, float lifetimeScl, Object data, Mover mover, float aimX, float aimY) {
        var b = super.create(owner, shooter, team, x, y, angle, damage, velocityScl, lifetimeScl, data, mover, aimX, aimY);
        subs.put(b, new Seq<>());
        return b;
    }
}
