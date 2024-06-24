package Exploring.world.entities.units;

import Exploring.world.entities.EntityRegister;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.entities.Damage;
import mindustry.gen.UnitEntity;

public class ReignXEntity extends UnitEntity {
    public static final Float regen = 0.2f;
    public static final Float destroyRad = 128f;
    public static final Float destroyMulti = 4f;
    public static final Float[] regenSpeed = {0.04f, 0.001f};
    public static final Float timerMax = 5f * 60f;

    public boolean urgent = false;
    public float urgentTimer;
    public boolean cooldown = false;

    public ReignXEntity() {
        urgentTimer = timerMax;
    }

    @Override
    public void update() {
        super.update();
        if (!Vars.net.client()) {
            repair();
            if (!urgent) urgentTimer += Time.delta * regen;
            else urgentTimer -= Time.delta;
            if (urgentTimer < 0f) {
                urgent = false;
                cooldown = true;
            }
            urgentTimer = Mathf.clamp(urgentTimer, 0f, timerMax);
        }
        // 85% Health. Safe
        if (health > maxHealth * 0.85f) urgent = false;
        // 15% Health
        if (health < maxHealth * 0.15f) tryUrgent();
    }

    @Override
    public void rawDamage(float amount) {
        super.rawDamage(amount);
        // 1/30 Health
        if (amount > maxHealth * 0.0333333333333333333333f) tryUrgent();
        // 10% Health
        if (amount > maxHealth * 0.1f) {
            kill();
        }
    }

    @Override
    public void destroy() {
        if (!Vars.net.client()) Damage.damage(team, x, y, destroyRad, maxHealth * destroyMulti);
        super.destroy();
    }

    @Override
    public int classId() {
        return EntityRegister.getID(ReignXEntity.class);
    }

    private void repair() {
        float speed = regenSpeed[urgent ? 0 : 1];
        health = Mathf.clamp(health + (maxHealth - health) * speed * Time.delta, -1.0f, maxHealth);
        shield += (maxHealth - health) * speed * Time.delta;
    }

    private void tryUrgent() {
        if (cooldown) {
            if (urgentTimer >= timerMax) cooldown = false;
            else return;
        }
        urgent = true;
    }

    @Override
    public boolean canShoot() {
        return super.canShoot() && !urgent;
    }

    @Override
    public void read(Reads read) {
        urgent = read.bool();
        super.read(read);
    }

    @Override
    public void write(Writes write) {
        write.bool(urgent);
        super.write(write);
    }

    @Override
    public void readSync(Reads read) {
        urgent = read.bool();
        super.readSync(read);
    }

    @Override
    public void writeSync(Writes write) {
        write.bool(urgent);
        super.writeSync(write);
    }
}
