package Exploring.entities.bullets;

import arc.math.Interp;
import arc.math.Mathf;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;

/**
 * @author <Yuria Main>
 * @author <LiuDai Ver2.0>
 */
public class AccelBulletType extends BasicBulletType {
    public float velocityBegin = -1;
    public float velocityIncrease = 0;
    public float accelerateBegin = 0.1f;
    public float accelerateEnd = 0.6f;

    public Interp accelInterp = Interp.linear;

    public AccelBulletType() {
        super();
    }

    public AccelBulletType(float velocityBegin, float velocityIncrease, Interp accelInterp, float damage, String bulletSprite) {
        super(1, damage, bulletSprite);
        this.velocityBegin = velocityBegin;
        this.velocityIncrease = velocityIncrease;
        this.accelInterp = accelInterp;
    }

    public AccelBulletType(float speed, float damage, String bulletSprite) {
        super(speed, damage, bulletSprite);
    }

    public AccelBulletType(float speed, float damage) {
        this(speed, damage, "bullet");
    }

    public AccelBulletType(float damage, String bulletSprite) {
        super(1, damage, bulletSprite);
    }

    public void disableAccel() {
        accelerateBegin = 10;
    }

    @Override
    protected float calculateRange() {
        if (velocityBegin < 0) velocityBegin = speed;

        boolean computeRange = rangeOverride < 0;
        float cal = 0;

        int c = 0;
        float t = 0.0f;
        for (float i = 0; i <= 1; i += 0.05f) {
            float s = velocityBegin + accelInterp.apply(Mathf.curve(i, accelerateBegin, accelerateEnd)) * velocityIncrease;
            t += s;
            if (computeRange) cal += s * lifetime * 0.05f;
            c++;
        }
        speed = t / c;

        if (computeRange) cal += 1;

        return cal;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void update(Bullet b) {
        if (accelerateBegin < 1)
            b.vel.setLength((velocityBegin + accelInterp.apply(Mathf.curve(b.fin(), accelerateBegin, accelerateEnd)) * velocityIncrease) * (drag != 0 ? (1 * Mathf.pow(b.drag, b.fin() * b.lifetime() / 6)) : 1));
        super.update(b);
    }
}
