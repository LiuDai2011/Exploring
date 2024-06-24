package Exploring.world.entities.bullets;

import Exploring.ExSettings;
import Exploring.content.ExFx;
import Exploring.graphics.BlackHoleRenderer;
import Exploring.graphics.ExPal;
import arc.Core;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;

public class BlackHoleBulletType extends BulletType {
    public float inRad, outRad;
    public int minLength = 13, midLength = 18, maxLength = 25;
    public float minWidth = 0.9f, maxWidth = 2.2f;
    public float minSpeed = 0.5f, midSpeed = 0.7f, maxSpeed = 1.8f;

    public float impulse = 6.67f * 1e-1f;

    public boolean acc = false;
    public Color accColor = ExPal.lightBlue;
    public int amount = 2;

    public float blackHoleDamage = 0f, blackHoleDamageRadius = 0f;


    public BlackHoleBulletType() {
        speed = 0;
        keepVelocity = collides = collidesGround = collidesAir = absorbable = hittable = false;
        despawnEffect = healEffect = Fx.none;
    }

    @Override
    public void draw(Bullet b) {
        float in = b.time <= b.lifetime - 72 ?
                Math.min(b.time / 60f, 1) :
                (b.lifetime - b.time) / 72f;
        in = Interp.fastSlow.apply(in);

//        BlackHoleRenderer.addBlackHole(b.x, b.y, inRad * in, outRad * in, Math.min(1, in + 0.1f), true);

        if (!Vars.headless && acc && (Core.settings != null && ExSettings.fullFx && b.time <= b.lifetime - 72))
            for (int i = 0; i < amount; i++) {
                var data = Pools.obtain(ExFx.ateData.class, ExFx.ateData::new);
                float outRDI = i % 2 == 0 ? outRad * 1.2f : outRad;
                data.width = Mathf.random(minWidth, maxWidth) * in;
                data.inRad = inRad * 0.9f * in;
                data.outRad = Math.max(data.inRad, Mathf.random(inRad * 1.1f, outRDI) * in);
                data.speed = data.outRad > inRad * 1.5f ? Mathf.random(minSpeed, midSpeed) : Mathf.random(midSpeed * 2f, maxSpeed);
                data.length = data.speed < midSpeed ? Mathf.random(midLength, maxLength) : Mathf.random(minLength, midLength);
                data.owner = b;
                if (i % 2 == 0) data.out = true;
                ExFx.AccretionDiskEffect.at(
                        b.x,
                        b.y,
                        0, accColor, data);
            }
        super.draw(b);
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
        Damage.damage(b.team, b.x, b.y, blackHoleDamageRadius, blackHoleDamage * Time.delta);
    }
}
