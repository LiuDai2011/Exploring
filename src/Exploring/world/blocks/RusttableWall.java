package Exploring.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.util.Time;
import mindustry.ui.Bar;

import static java.lang.Math.max;

public class RusttableWall extends ExWall {
    public float[] maxHealths = {500f, 300f, 200f, 150f, 100f, 75f, 50f, 20f, 0f};

    public RusttableWall(String name) {
        super(name);
        buildType = RusttableWallBuild::new;
        update = true;
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("health", (RusttableWallBuild entity) -> new Bar(
                () -> Core.bundle.format("bar.health", Math.floor(entity.health), entity.maxHealthc),
                () -> Color.valueOf("ff0000"),
                () -> {
                    entity.health = max(entity.health, entity.maxHealthc);
                    return entity.health / entity.maxHealthc;
                }
        ));

        addBar("rust", (RusttableWallBuild entity) -> new Bar(
                () -> Core.bundle.formatString("rust:{0}", ((float) Math.floor(entity.progress * 100f)) / 100f),
                () -> Color.valueOf("000000"),
                () -> entity.progress / 8f
        ));
    }

    public class RusttableWallBuild extends ExWallBuild {
        public float progress = 0f;
        private float timer = 0f;
        public float maxHealthc = 0f;


        @Override
        public void update() {
            super.update();

            timer += Time.delta;
            if (timer >= 10f) {
                progress += 0.01f;
            }

            if (progress >= 40f)
                killed();

            maxHealthc = maxHealths[(int) (progress / 5f)];
        }
    }
}
