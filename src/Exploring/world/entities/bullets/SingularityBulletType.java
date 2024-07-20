package Exploring.world.entities.bullets;

import Exploring.content.ExFx;
import Exploring.content.ExStatusEffects;
import Exploring.graphics.ExColor;
import Exploring.graphics.ExRenderer;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

import static mindustry.Vars.tilesize;

public class SingularityBulletType extends BulletType {
    public float impulseRange;
    public float attract;
    public Effect spawnEffect;

    public float size = 3, triWidth = 6, triLength = 48;

    public SingularityBulletType() {
        keepVelocity = collides = collidesGround = collidesAir = absorbable = hittable = false;
        speed = 0f;

        spawnEffect = ExFx.singularitySpawn(15);
    }

    @Override
    public void init(Bullet b) {
        super.init(b);
        if (!Vars.headless) spawnEffect.at(b);
    }

    @Override
    public void draw(Bullet b) {
        super.draw(b);
        float f = Mathf.curve(b.fout(), 0, 0.15f);
        float f2 = Mathf.curve(b.fin(), 0, 0.1f);
        Draw.color(ExColor.lightBlue, b.team.color, 0.6f);
        Fill.circle(b.x, b.y, size * tilesize / 3f * f);

        for (int i : Mathf.signs) {
            Drawf.tri(b.x, b.y, triWidth * f2 * f, triLength * 1.4f * f2 * f + Mathf.sin(Time.time / 15) * 13, (i + 1) * 90 + Time.time * 2);
            Drawf.tri(b.x, b.y, triWidth * f2 * f, triLength * 1.4f * f2 * f + Mathf.sin(Time.time / 15) * 13, (i + 1) * 90 + Time.time * 2 + 90);
        }

        Draw.color(Color.black);
        Draw.z(Layer.effect + 0.01f);
        Fill.circle(b.x, b.y, size * tilesize / 5f * f);
        Draw.z(Layer.bullet);

        Drawf.light(b, impulseRange * 4f * b.fout(Interp.pow2Out), ExColor.lightBlue, 0.75f);

        ExRenderer.addBlackHole(b.x, b.y, 0, 24 * f2 * f, false);
        ExRenderer.addBlackHole(b.x, b.y, 0, 16 * f2 * f, false);
        ExRenderer.addBlackHole(b.x, b.y, 0, 8 * f2 * f, true);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        float out = b.fout() * b.fout() - b.fout() + 1, damage = this.damage * Time.delta;
        Units.nearby(Tmp.r1.setCenter(b.x, b.y).setSize(impulseRange * 5f * out), unit -> {
            float dis = Tmp.v4.set(b).dst(unit) + 0.000001f, db = 8 / dis * damage;
            if (unit.team == b.team) {
                unit.apply(ExStatusEffects.fast, 60f);
                unit.apply(ExStatusEffects.phased, 60f);
                if (Mathf.chance(0.095)) unit.unapply(ExStatusEffects.gravitationalTearing);
                if (Mathf.chance(0.135)) unit.unapply(ExStatusEffects.slow);
                if (unit.shield < 200000) unit.shield += db * 32;
                if (unit.shield < unit.maxHealth) unit.health += db * 16;
                return;
            }
            boolean can = !unit.dead && unit.type != null && unit.type.targetable;
            if (can) unit.impulse(Tmp.v3.set(unit).sub(b.x, b.y).nor().scl(-Math.min(dis * 1.3f,
                    attract * 100.0f * out)).rotate(-Mathf.clamp(Mathf.pow(attract / dis / 10f, 1 / 3f) * 40f, 25f, 60f)));
            unit.apply(ExStatusEffects.slow, 60f);
            if (Mathf.chance(0.255)) unit.apply(ExStatusEffects.gravitationalTearing, 60f);
            if (dis < tilesize * 4) db *= 4;
            unit.shield = Math.max(unit.shield - db, 0f);
            if (Mathf.zero(unit.shield)) db *= 1.5f;
            if (can) unit.damage(4 * db);
            if (can) unit.health -= db;
            if (unit.health < unit.maxHealth * 0.1f && can) unit.kill();
        });
        Damage.damage(b.team, b.x, b.y, impulseRange * 3f * out, damage);
    }
}
