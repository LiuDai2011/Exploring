package Exploring.world.blocks.test;

import Exploring.graphics.ExColor;
import Exploring.math.MathDef;
import Exploring.math.Pair;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.BuildVisibility;

public class DPSWall extends Wall {
    public DPSWall(String name, int size) {
        super(name);
        health = Integer.MAX_VALUE;
        buildType = DPSBuild::new;
        update = true;

        requirements(Category.effect, BuildVisibility.sandboxOnly, ItemStack.empty);
        this.size = size;
    }

    @Override
    public void setBars() {
        super.setBars();

        removeBar("health");
        addBar("total-damage", (DPSBuild build) -> new Bar(
                () -> "TD " + MathDef.exactlyRound(build.damageTotal, 10),
                () -> Color.red,
                () -> 1f
        ).blink(Color.white));
        addBar("dps", (DPSBuild build) -> new Bar(
                () -> "DPS " + MathDef.exactlyRound(build.dps(), 10),
                () -> ExColor.lightBlue,
                () -> 1f
        ).blink(Color.white));
        addBar("length", (DPSBuild build) -> new Bar(
                () -> "length " + build.seq.size,
                () -> ExColor.lightBlue,
                () -> build.seq.size / 10000f
        ).blink(Color.white));
    }

    public class DPSBuild extends WallBuild {
        public float damageTotal = 0f;
        public Seq<Pair<Float, Float>> seq = new Seq<>();

        @Override
        public void damage(float damage) {
            damageTotal += damage;
            seq.add(new Pair<>(Time.time, damage));

            lastDamageTime = Time.time;
        }

        @Override
        public void update() {
            super.update();
            while (seq.size > 1000 && !(Time.time - seq.get(0).key() > 60f)) seq.remove(0);
            while (seq.size > 10000) seq.remove(0);
        }

        public float dps() {
            float tmp = 0f;
            for (var e : seq) {
                tmp += e.value();
            }
            return tmp / (seq.size == 0 || Mathf.zero(Time.time - seq.get(0).key()) ? 1f : (Time.time - seq.get(0).key()) / 60f);
        }
    }
}
