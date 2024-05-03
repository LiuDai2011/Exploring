package Exploring.content;

import arc.Core;
import arc.func.Cons;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.Drawable;
import arc.scene.style.TextureRegionDrawable;
import arc.struct.Seq;
import arc.util.Nullable;

public class ResearchTree {
    private static ResearchNode context = null;

    public static Seq<ResearchNode> all = new Seq<>();
    public static Seq<ResearchNode> roots = new Seq<>();

    public static ResearchNode nodeRoot(String name, Runnable children){
        return nodeRoot(name, false, children);
    }

    public static ResearchNode nodeRoot(String name, boolean requireUnlock, Runnable children) {
        ResearchNode root = node(name, children);
        root.requiresUnlock = requireUnlock;
        roots.add(root);
        return root;
    }

    public static ResearchNode node(String name, Runnable children) {
        ResearchNode node = new ResearchNode(context, name);

        ResearchNode prev = context;
        context = node;
        children.run();
        context = prev;

        return node;
    }

    public static ResearchNode node(String name) {
        return node(name, () -> {});
    }

    public static @Nullable ResearchNode context(){
        return context;
    }

    public static class ResearchNode {
        public int depth;
        public @Nullable TextureRegion icon;
        public String name;
        public boolean requiresUnlock = false;
        public boolean root = true;
        public float progress = 0f;
        public @Nullable ResearchNode parent;
        public final Seq<ResearchNode> children = new Seq<>();

        public ResearchNode(@Nullable ResearchNode parent, String name) {
            if (parent == null) {
                root = false;
            }

            this.parent = parent;
            this.depth = parent == null ? 0 : parent.depth + 1;
            this.name = name;

            all.add(this);
        }

        public void each(Cons<ResearchNode> consumer){
            consumer.get(this);
            for(var child : children){
                child.each(consumer);
            }
        }

        public Drawable icon(){
            return new TextureRegionDrawable(icon);
        }

        public String localizedName(){
            return Core.bundle.get("research-tree." + name, name);
        }

        public void reset(){
            progress = 0f;
            save();
        }

        public void remove(){
            all.remove(this);
            if(parent != null){
                parent.children.remove(this);
            }
        }

        public void save(){
            Core.settings.put("res-" + name + "-prog", progress);
        }

        public boolean locked() {
            return progress > 1f;
        }
    }
}
