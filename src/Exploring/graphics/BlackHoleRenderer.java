package Exploring.graphics;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.gl.FrameBuffer;
import arc.struct.Seq;
import arc.util.Tmp;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.game.EventType.Trigger;
import mindustry.graphics.Layer;
import mindustry.graphics.Shaders;

import static arc.Core.graphics;

public class BlackHoleRenderer {
    private static final int len = 1000;
    private final Seq<BlackHole> holes = new Seq<>();
    private static BlackHoleRenderer renderer;

    private FrameBuffer buffer;

    private static final float[][] initFloat = new float[len][];
    //private static final Pool<BlackHole> holePool = Pools.get(BlackHole.class, BlackHole::new);

    protected BlackHoleRenderer(){
        if(!Vars.headless) {
            BlackHoleShader.createShader();

            buffer = new FrameBuffer();
            Events.run(Trigger.draw, this::advancedDraw);
        }
    }

    public static void init(){
        if(renderer == null) renderer = new BlackHoleRenderer();
        for(int i = 0; i < len; i++){
            initFloat[i] = new float[i * 4];
        }
    }

    public static void addBlackHole(float x, float y, float inRadius, float outRadius, float alpha){
        if(!Vars.headless) renderer.addHole(x, y, inRadius, outRadius, alpha);
    }
    public static void addBlackHole(float x, float y, float inRadius, float outRadius){
        if(!Vars.headless) renderer.addHole(x, y, inRadius, outRadius, 1);
    }

    private void advancedDraw(){
        Draw.draw(Layer.background - 0.1f, () -> {
            buffer.resize(graphics.getWidth(), graphics.getHeight());
            buffer.begin();
        });

        Draw.draw(Layer.max - 4f, () -> {
//            buffer.end();
//
//            if(holes.size >= len) {
//                buffer.blit(Shaders.screenspace);
//                return;
//            }
//            if(holes.size >= BlackHoleShader.maxCount) BlackHoleShader.createShader();
//
//            float[] blackholes = initFloat[holes.size];
//
//            for(int i = 0; i < holes.size; i++){
//                var hole = holes.get(i);
//                blackholes[i * 4] = hole.x;
//                blackholes[i * 4 + 1] = hole.y;
//                blackholes[i * 4 + 2] = hole.inRadius;
//                blackholes[i * 4 + 3] = hole.outRadius;
//
//                Draw.color(Tmp.c2.set(Color.black).a(hole.alpha));
//                Fill.circle(hole.x, hole.y, hole.inRadius * 1.5f);
//                Draw.color();
//            }
//            BlackHoleShader.holeShader.blackHoles = blackholes;
//            buffer.blit(BlackHoleShader.holeShader);
//
//            buffer.begin();
//            Draw.rect();
//            buffer.end();
//
//            //holePool.freeAll(holes);
//            holes.clear();
            buffer.end();

            if(holes.size >= BlackHoleShader.maxCount) BlackHoleShader.createShader();

            float[] blackholes = new float[holes.size * 4];
            for(int i = 0; i < holes.size; i++){
                BlackHole hole = holes.get(i);
                blackholes[i * 4] = hole.x;
                blackholes[i * 4 + 1] = hole.y;
                blackholes[i * 4 + 2] = hole.inRadius;
                blackholes[i * 4 + 3] = hole.outRadius;

//                Draw.color(Tmp.c2.set(Color.black).a(hole.alpha));
//                Fill.circle(hole.x, hole.y, hole.inRadius * 1.5f);
//                Draw.color();
            }
            BlackHoleShader.holeShader.blackHoles = blackholes;
            buffer.blit(BlackHoleShader.holeShader);

            buffer.begin();
            Draw.rect();
            buffer.end();

            holes.clear();
        });
    }

    private void addHole(float x, float y, float inRadius, float outRadius, float alpha){
        if(inRadius > outRadius || outRadius <= 0) return;

        holes.add(Pools.obtain(BlackHole.class, BlackHole::new).set(x, y, inRadius, outRadius, alpha));
    }

    private static class BlackHole{
        float x, y, inRadius, outRadius, alpha;

        public BlackHole set(float x, float y, float inRadius, float outRadius, float alpha){
            this.x = x;
            this.y = y;
            this.inRadius = inRadius;
            this.outRadius = outRadius;
            this.alpha = alpha;
            return this;
        }

        public BlackHole(){

        }
    }
}
