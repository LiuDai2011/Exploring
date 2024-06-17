package Exploring.graphics;

import Exploring.ExploringMain;
import arc.func.Cons;
import arc.graphics.Texture;

public class ExTex {
    public static Texture dynBarNoise;

    public static void load() {
         dynBarNoise = loadTex("dyn-bar-noise", t -> {
            t.setFilter(Texture.TextureFilter.linear);
            t.setWrap(Texture.TextureWrap.repeat);
        });
    }

    static Texture loadTex(String name, Cons<Texture> modifier){
        Texture tex = new Texture(ExploringMain.MOD.root.child("textures").child(name + (name.endsWith(".png") ? "" : ".png")));
        modifier.get(tex);

        return tex;
    }
}
