package Exploring.world.blocks;

import arc.math.Angles;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.logic.Ranged;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.tilesize;

public class AC extends Block {
    public static float range = 24f;
    public static float addTimeMul = 1f;
    public static float velMul = 1.001f;

    public AC(String name) {
        super(name);
        solid = true;
        update = true;
        group = BlockGroup.turrets;
        buildType = ACBuild::new;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Drawf.dashCircle(x * tilesize + offset, y * tilesize + offset, range, Pal.placing);
    }

    public class ACBuild extends Building implements Ranged {
        @Override
        public void updateTile() {
            float addTime = Time.delta * addTimeMul;
            Seq<Bullet> intersected = Groups.bullet.intersect(x - range, y - range, 2 * range, 2 * range);

            for (Bullet bullet : intersected) {
                if (bullet != null && bullet.within(this, range) && bullet.team() == team) {
//                    float f = bullet.time / bullet.lifetime;
//                    bullet.time += bullet.time * addTime / f + 1;
//                    bullet.lifetime += bullet.lifetime * addTime / f + 1;
                    bullet.time += addTime;
                    bullet.lifetime += 2 * addTime;
                    bullet.vel.x *= velMul;
                    bullet.vel.y *= velMul;
                    bullet.vel.add(Angles.trnsx(bullet.rotation(), 0.00001f), Angles.trnsy(bullet.rotation(), 0.00001f));
//                            Fx.shieldBreak.at(bullet);
                }
            }
        }

        @Override
        public float range() {
            return range;
        }

        @Override
        public void drawSelect() {
            Drawf.dashCircle(x + offset, y + offset, range, team.color);
        }
    }
}
