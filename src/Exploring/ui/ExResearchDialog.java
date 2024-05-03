package Exploring.ui;

import Exploring.content.ResearchTree.ResearchNode;
import Exploring.input.ExBinding;
import arc.Core;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Rect;
import arc.scene.Element;
import arc.scene.Group;
import arc.scene.actions.RelativeTemporalAction;
import arc.scene.event.Touchable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Align;
import arc.util.Nullable;
import arc.util.Scaling;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.layout.TreeLayout.TreeNode;

import static mindustry.Vars.content;
import static mindustry.Vars.mobile;

public class ExResearchDialog extends BaseDialog {
    public final float nodeSize = Scl.scl(60f);
    public ObjectSet<ResearchTreeNode> nodes = new ObjectSet<>();
    public Rect bounds = new Rect();
    public View view;

    public ExResearchDialog() {
        super("");
        titleTable.remove();
        titleTable.clear();
        titleTable.top();

        margin(0f).marginBottom(8);
        cont.stack(titleTable, view = new View()).grow();

        addCloseButton();

        keyDown(key -> {
            if(key == Core.keybinds.get(ExBinding.research).key){
                Core.app.post(this::hide);
            }
        });
    }

    void checkMargin() {}

//    public @Nullable ResearchNode getPrefRoot() {}

    public void switchTree() {}

    public void rebuildTree() {}

    void treeLayout() {}

    void shift(LayoutNode[] children, float amount) {}

    void copyInfo() {}

    void checkNodes(ResearchNode node) {}

//    boolean selectable(ResearchNode node) {}

    boolean locked(ResearchNode node) {
        return node.locked();
    }

    class LayoutNode extends TreeNode<LayoutNode>{
        final ResearchTreeNode node;

        LayoutNode(ResearchTreeNode node, LayoutNode parent){
            this.node = node;
            this.parent = parent;
            this.width = this.height = nodeSize;
            if(node.children != null){
                children = Seq.with(node.children).map(t -> new LayoutNode(t, this)).toArray(LayoutNode.class);
            }
        }
    }

    public class ResearchTreeNode extends TreeNode<ResearchTreeNode> {
        public final ResearchNode node;
        public boolean visible = true, selectable = true;

        public ResearchTreeNode(ResearchNode node, ResearchTreeNode parent){
            this.node = node;
            this.parent = parent;
            this.width = this.height = nodeSize;
            nodes.add(this);
            children = new ResearchTreeNode[node.children.size];
            for(int i = 0; i < children.length; i++){
                children[i] = new ResearchTreeNode(node.children.get(i), this);
            }
        }
    }

    public class View extends Group {
        public float panX = 0, panY = -200, lastZoom = -1;
        public boolean moved = false;
        public ImageButton hoverNode;
        public Table infoTable = new Table();

        {
            rebuildAll();
        }

        public void rebuildAll() {
            clear();
            hoverNode = null;
            infoTable.clear();
            infoTable.touchable = Touchable.enabled;
//
//            for (ResearchTreeNode node : nodes) {
//                ImageButton button = new ImageButton(node.node.icon, Styles.nodei);
//                button.resizeImage(32f);
//                button.getImage().setScaling(Scaling.fit);
//                button.visible(() -> node.visible);
//                button.clicked(() -> {
//                    if (moved) return;
//
//                    if (mobile) {
//                        hoverNode = button;
//                        rebuild();
//                        float right = infoTable.getRight();
//                        if (right > Core.graphics.getWidth()) {
//                            float moveBy = right - Core.graphics.getWidth();
//                            addAction(new RelativeTemporalAction() {
//                                {
//                                    setDuration(0.1f);
//                                    setInterpolation(Interp.fade);
//                                }
//
//                                @Override
//                                protected void updateRelative(float v) {
//                                    panX -= moveBy * v;
//                                }
//                            });
//                        }
//                    } else if (canSpend(node.node) && locked(node.node)) {
//                        spend(node.node);
//                    }
//                });
//                button.hovered(() -> {
//                    if(!mobile && hoverNode != button && node.visible){
//                        hoverNode = button;
//                        rebuild();
//                    }
//                });
//                button.exited(() -> {
//                    if(!mobile && hoverNode == button && !infoTable.hasMouse() && !hoverNode.hasMouse()){
//                        hoverNode = null;
//                        rebuild();
//                    }
//                });
//                button.touchable(() -> !node.visible ? Touchable.disabled : Touchable.enabled);
//                button.userObject = node.node;
//                button.setSize(nodeSize);
//                button.update(() -> {
//                    float offset = (Core.graphics.getHeight() % 2) / 2f;
//                    button.setPosition(node.x + panX + width / 2f, node.y + panY + height / 2f + offset, Align.center);
//                    button.getStyle().up = !locked(node.node) ? Tex.buttonOver : !selectable(node.node) || !canSpend(node.node) ? Tex.buttonRed : Tex.button;
//
//                    ((TextureRegionDrawable)button.getStyle().imageUp).setRegion(node.selectable ? node.node.icon : Icon.lock.getRegion());
//                    button.getImage().setColor(!locked(node.node) ? Color.white : node.selectable ? Color.gray : Pal.gray);
//                    button.getImage().layout();
//                });
//                addChild(button);
//            }
//
//            if(mobile){
//                tapped(() -> {
//                    Element e = Core.scene.hit(Core.input.mouseX(), Core.input.mouseY(), true);
//                    if(e == this){
//                        hoverNode = null;
//                        rebuild();
//                    }
//                });
//            }
//
//            setOrigin(Align.center);
//            setTransform(true);
//            released(() -> moved = false);
        }
//
//        void clamp() {
//            float pad = nodeSize;
//
//            float ox = width/2f, oy = height/2f;
//            float rx = bounds.x + panX + ox, ry = panY + oy + bounds.y;
//            float rw = bounds.width, rh = bounds.height;
//            rx = Mathf.clamp(rx, -rw + pad, Core.graphics.getWidth() - pad);
//            ry = Mathf.clamp(ry, -rh + pad, Core.graphics.getHeight() - pad);
//            panX = rx - bounds.x - ox;
//            panY = ry - bounds.y - oy;
//        }
//
//        boolean canSpend(ResearchNode node) {
//            if(!selectable(node)) return false;
//
//            return node.locked();
//        }
//
//        void spend(ResearchNode node) {
//            boolean complete = true;
//
//            if(complete){
//                unlock(node);
//            }
//
//            node.save();
//
//            Core.scene.act();
//            rebuild(shine);
//        }
//
//        void unlock(ResearchNode node) {}
//
//        void rebuild() {
//            rebuild(null);
//        }
//
//        void rebuild(@Nullable boolean[] shine) {}
//
//        @Override
//        protected void drawChildren() {}
    }
}
