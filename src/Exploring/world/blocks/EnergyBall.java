package Exploring.world.blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Building;
import mindustry.world.Block;

public class EnergyBall extends Block {
    public EnergyBall(String name) {
        super(name);
        buildType = EnergyBallBuild::new;
        update = true;
    }

    public class EnergyBallBuild extends Building {
        public float instability = 0f;
        public float size = 0f;
        public float energy = 0f;
        private float time = 0f;

        @Override
        public void draw() {
            Color c = Color.valueOf("cff2ff");

            Draw.color(c);

            float size = Mathf.sinDeg(time / 1.8f % 180f) * instability + this.size;

            Fill.circle(x, y, size);
            Fill.light(x, y, 50, size + 0.6f, 0, c, c);

            Draw.reset();
        }

        @Override
        public void updateTile() {
            size = 12f;
            instability = time % 9f;
            time += Time.delta;
            time %= 10000f;
            draw();
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(instability);
            write.f(size);
            write.f(energy);
        }

        @Override
        public void read(Reads read) {
            super.read(read);
            instability = read.f();
            size = read.f();
            energy = read.f();
        }
    }

}
