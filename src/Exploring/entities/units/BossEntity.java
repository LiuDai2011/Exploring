package Exploring.entities.units;

import arc.math.Mathf;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.gen.UnitEntity;

public class BossEntity extends UnitEntity {
    public float damageMulti = 1f;
    public float damaged = 0f;

    public static UnitEntity create() {
        return new BossEntity();
    }

    @Override
    public void rawDamage(float amount) {
        boolean hadShields = this.shield > 1e-4f;
        if (hadShields) {
            this.shieldAlpha = 1;
        }

//        float realAmount = amount;
        amount *= damageMulti;
        float shieldDamage = Math.min(Math.max(this.shield, 0), amount);
        this.shield -= shieldDamage;
        this.hitTime = 1;
        amount -= shieldDamage;
        if (amount > 0 && this.type.killable) {
            this.health -= amount;
            if (this.health <= 0 && !this.dead) {
                this.kill();
            }

            if (hadShields && this.shield <= 1e-4f) {
                Fx.unitShieldBreak.at(this.x, this.y, 0, this.team.color, this);
            }
        }

        damaged += amount;//realAmount;
        damageMulti = Mathf.clamp(1.6f - Mathf.log(3000f, damaged / 8f + 10f), 0.12f, 1f);
//        Util.getFiChild(Vars.dataDirectory, "structureordered/");
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void read(Reads read) {
        super.read(read);
        damageMulti = 0f;//read.f();
        damaged = 0f;//read.f();
    }

    @Override
    public void write(Writes write) {
        super.write(write);
        //TODO: why ?
//        write.f(damageMulti);
//        write.f(damaged);
    }
}
