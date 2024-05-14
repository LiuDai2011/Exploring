package Exploring.ui;

import Exploring.content.ResearchTree;
import Exploring.content.ResearchTree.ResearchNode;
import Exploring.input.ExBinding;
import Exploring.type.ExEventType.ExResearchEvent;
import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Rect;
import arc.scene.Element;
import arc.scene.Group;
import arc.scene.actions.Actions;
import arc.scene.actions.RelativeTemporalAction;
import arc.scene.event.Touchable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.ImageButton;
import arc.scene.ui.Label;
import arc.scene.ui.TextButton;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Align;
import arc.util.Scaling;
import arc.util.Structs;
import mindustry.content.TechTree;
import mindustry.core.UI;
import mindustry.game.Objectives;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.layout.TreeLayout.TreeNode;

import static mindustry.Vars.mobile;
import static mindustry.Vars.ui;
import static mindustry.gen.Tex.buttonDown;
import static mindustry.gen.Tex.buttonOver;

public class ExResearchDialog extends BaseDialog {
    public final float nodeSize = Scl.scl(60f);
    public ObjectSet<ResearchTreeNode> nodes = new ObjectSet<>();
    public Rect bounds = new Rect();
    public View view;
    public ResearchTreeNode root;
    public ExResearchDialog() {
        super("");

//        root = new ResearchTreeNode(ResearchTree.roots.first(), null);

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

    void checkNodes(ResearchTreeNode node) {}

    boolean selectable(ResearchNode node) {
        throw new IllegalCallerException();
    }

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
//
//        {
//            rebuildAll();
//        }
//
//        public void rebuildAll() {
//            clear();
//            hoverNode = null;
//            infoTable.clear();
//            infoTable.touchable = Touchable.enabled;
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
//        }
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
//            node.spend();
//            boolean complete = node.tryUnlock();
//
//            if(complete){
//                unlock(node);
//            }
//
//            node.save();
//
//            Core.scene.act();
//            rebuild();
//        }
//
//        void unlock(ResearchNode node) {
//            var parent = node.parent;
//            while (parent != null) {
//                parent.unlock();
//                parent = parent.parent;
//            }
//
//            checkNodes(root);
//            hoverNode = null;
//            treeLayout();
//            rebuild();
//            Core.scene.act();
//            Sounds.unlock.play();
//            Events.fire(new ExResearchEvent(node));
//        }
//
//        void rebuild() {
//            ImageButton button = hoverNode;
//
//            infoTable.remove();
//            infoTable.clear();
//            infoTable.update(null);
//
//            if(button == null) return;
//
//            ResearchNode node = (ResearchNode) button.userObject;
//
//            infoTable.exited(() -> {
//                if(hoverNode == button && !infoTable.hasMouse() && !hoverNode.hasMouse()){
//                    hoverNode = null;
//                    rebuild();
//                }
//            });
//
//            infoTable.update(() -> infoTable.setPosition(button.x + button.getWidth(), button.y + button.getHeight(), Align.topLeft));
//
//            infoTable.left();
//            infoTable.background(Tex.button).margin(8f);
//
//            boolean selectable = selectable(node);//done
//
//            infoTable.table(b -> {
//                b.margin(0).left().defaults().left();
//
//                if(selectable){
//                    b.button(Icon.info, Styles.flati, node::showUI).growY().width(50f);
//                }
//                b.add().grow();
//                b.table(desc -> {
//                    desc.left().defaults().left();
//                    desc.add(selectable ? node.content.localizedName : "[accent]???");
//                    desc.row();
//                    if(locked(node) || debugShowRequirements){
//
//                        desc.table(t -> {
//                            t.left();
//                            if(selectable){
//
//                                //check if there is any progress, add research progress text
//                                if(Structs.contains(node.finishedRequirements, s -> s.amount > 0)){
//                                    float sum = 0f, used = 0f;
//                                    boolean shiny = false;
//
//                                    for(int i = 0; i < node.requirements.length; i++){
//                                        sum += node.requirements[i].item.cost * node.requirements[i].amount;
//                                        used += node.finishedRequirements[i].item.cost * node.finishedRequirements[i].amount;
//                                        if(shine != null) shiny |= shine[i];
//                                    }
//
//                                    Label label = t.add(Core.bundle.format("research.progress", Math.min((int)(used / sum * 100), 99))).left().get();
//
//                                    if(shiny){
//                                        label.setColor(Pal.accent);
//                                        label.actions(Actions.color(Color.lightGray, 0.75f, Interp.fade));
//                                    }else{
//                                        label.setColor(Color.lightGray);
//                                    }
//
//                                    t.row();
//                                }
//
//                                for(int i = 0; i < node.requirements.length; i++){
//                                    ItemStack req = node.requirements[i];
//                                    ItemStack completed = node.finishedRequirements[i];
//
//                                    //skip finished stacks
//                                    if(req.amount <= completed.amount && !debugShowRequirements) continue;
//                                    boolean shiny = shine != null && shine[i];
//
//                                    t.table(list -> {
//                                        int reqAmount = debugShowRequirements ? req.amount : req.amount - completed.amount;
//
//                                        list.left();
//                                        list.image(req.item.uiIcon).size(8 * 3).padRight(3);
//                                        list.add(req.item.localizedName).color(Color.lightGray);
//                                        Label label = list.label(() -> " " +
//                                                UI.formatAmount(Math.min(items.get(req.item), reqAmount)) + " / "
//                                                + UI.formatAmount(reqAmount)).get();
//
//                                        Color targetColor = items.has(req.item) ? Color.lightGray : Color.scarlet;
//
//                                        if(shiny){
//                                            label.setColor(Pal.accent);
//                                            label.actions(Actions.color(targetColor, 0.75f, Interp.fade));
//                                        }else{
//                                            label.setColor(targetColor);
//                                        }
//
//                                    }).fillX().left();
//                                    t.row();
//                                }
//                            }else if(node.objectives.size > 0){
//                                t.table(r -> {
//                                    r.add("@complete").colspan(2).left();
//                                    r.row();
//                                    for(Objectives.Objective o : node.objectives){
//                                        if(o.complete()) continue;
//
//                                        r.add("> " + o.display()).color(Color.lightGray).left();
//                                        r.image(o.complete() ? Icon.ok : Icon.cancel, o.complete() ? Color.lightGray : Color.scarlet).padLeft(3);
//                                        r.row();
//                                    }
//                                });
//                                t.row();
//                            }
//                        });
//                    }else{
//                        desc.add("@completed");
//                    }
//                }).pad(9);
//
//                if(mobile && locked(node)){
//                    b.row();
//                    b.button("@research", Icon.ok, new TextButton.TextButtonStyle(){{
//                        disabled = Tex.button;
//                        font = Fonts.def;
//                        fontColor = Color.white;
//                        disabledFontColor = Color.gray;
//                        up = buttonOver;
//                        over = buttonDown;
//                    }}, () -> spend(node)).disabled(i -> !canSpend(node)).growX().height(44f).colspan(3);
//                }
//            });
//
//            infoTable.row();
//            if(node.content.description != null && node.content.inlineDescription && selectable){
//                infoTable.table(t -> t.margin(3f).left().labelWrap(node.content.displayDescription()).color(Color.lightGray).growX()).fillX();
//            }
//
//            addChild(infoTable);
//
//            checkMargin();
//            Core.app.post(() -> checkMargin());
//
//            infoTable.pack();
//            infoTable.act(Core.graphics.getDeltaTime());
//        }
//
//        @Override
//        protected void drawChildren() {}
    }
}
