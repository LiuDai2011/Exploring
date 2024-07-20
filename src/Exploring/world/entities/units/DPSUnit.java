package Exploring.world.entities.units;

import Exploring.math.Pair;
import Exploring.world.entities.EntityRegister;
import Exploring.world.meta.TransTeamc;
import Exploring.world.util.Change;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.game.Team;
import mindustry.gen.UnitEntity;

public class DPSUnit extends UnitEntity implements TransTeamc {
    public float damageTotal = 0f;
    public Seq<Pair<Float, Float>> seq = new Seq<>();
    public Team oTeam = null;

    @Override
    public int classId() {
        return EntityRegister.getID(DPSUnit.class);
    }

    @Override
    public void damage(float damage) {
        damageTotal += damage;
        seq.add(new Pair<>(Time.time, damage));
    }

    @Override
    public void update() {
        super.update();
        while (seq.size > 1000 && !(Time.time - seq.get(0).key() > 60f)) seq.remove(0);
        while (seq.size > 10000) seq.remove(0);
        if (oTeam == null) oTeam = team;
        if (team != oTeam) Change.team(this, team);
        health = maxHealth;
    }

    public float dps() {
        float tmp = 0f;
        for (var e : seq) {
            tmp += e.value();
        }
        return tmp / (seq.size == 0 || Mathf.zero(Time.time - seq.get(0).key()) ? 1f : (Time.time - seq.get(0).key()) / 60f);
    }

    @Override
    public boolean can() {
        return false;
    }
}
