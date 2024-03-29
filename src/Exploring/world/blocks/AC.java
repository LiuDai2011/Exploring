package Exploring.world.blocks;

import arc.util.Time;
import mindustry.content.Fx;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.tilesize;

public class AC extends Block {
    public static float range = 24f;
    public static float addTimeMul = 1f;

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

    public class ACBuild extends Building {
        @Override
        public void updateTile() {
            float addTime = Time.delta;
            Groups.bullet.intersect(x - range, y - range, 2 * range, 2 * range)
                    .forEach(bullet -> {
                        if (bullet != null && bullet.within(this, range) && bullet.team() == team) {
                            bullet.lifetime += addTime * addTimeMul;
                            Fx.shieldBreak.at(bullet);
                        }
                    });
        }
    }
}
