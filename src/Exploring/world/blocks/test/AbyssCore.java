package Exploring.world.blocks.test;

import Exploring.content.ExBlocks;
import Exploring.content.ExTeam;
import Exploring.world.meta.ExFunc;
import Exploring.world.meta.info.InfoStreamModule;
import Exploring.world.meta.info.Infoc;
import Exploring.world.util.Change;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.util.Time;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import org.jetbrains.annotations.NotNull;

public class AbyssCore extends Block {
    public AbyssCore(String name) {
        super(name);
        solid = true;
        destructible = true;
        update = true;

        buildType = AbyssCoreBuild::new;
    }

    public class AbyssCoreBuild extends Building implements Infoc {
        public InfoStreamModule infoStreamModule = new InfoStreamModule(10000);
        public float[] process = new float[]{0f, 0f, 0f, 0f};

        @Override
        public void update() {
            super.update();
            Change.team(this, ExTeam.abyss);
            infoStreamModule.add(InfoStreamModule.InfoType.abyss, Time.delta);
            if (infoStreamModule.stream.get(InfoStreamModule.InfoType.abyss) > 5400)
                tile.setFloor((Floor) ExBlocks.abyssFloor);
            for (int i = 0; i < 4; i++) {
                var p = Point2.unpack(tile.pos()).add(Geometry.d4(i));
                Tile tile1 = Vars.world.tile(p.pack());
                if (tile1 != null) {
                    ExFunc.rand.setSeed(tile1.pos());
                    process[i] += Time.delta * (tile1.overlay() instanceof OreBlock ? 0.3f : 0f + 0.1f + ExFunc.rand.nextFloat() * 0.2f);
                    if (process[i] > 1000f) {
                        tile1.setFloor((Floor) ExBlocks.abyssFloor);
                    }
                }
            }
        }

        @Override
        public @NotNull InfoStreamModule infoModule() {
            return infoStreamModule;
        }
    }
}
