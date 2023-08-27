package Exploring.world.blocks.storage;

import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.state;

public class SubspaceStorageLinker extends ExStorageBlock {
    public Effect runEffect = Fx.none;

    public SubspaceStorageLinker(String name) {
        super(name);
        coreMerge = false;
        update = true;
        hasPower = true;
    }

    public class SubspaceStorageLinkerBuild extends StorageBuild {
        public float timer = 0f;


        public boolean hadPower() {
            return !Mathf.zero(this.power.status);
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return hadPower() && (linkedCore != null ? linkedCore.acceptItem(source, item) : items.get(item) < getMaximumAccepted(item));
        }

        @Override
        public void updateTile() {
            super.updateTile();
            timer += Time.delta;
            if(timer * 60 < runEffect.lifetime) return ;
            if (wasVisible && hadPower()) {
                runEffect.at(x, y);
            }
            timer %= 1f;
        }

        @Override
        public void handleItem(Building source, Item item) {
            if (!hadPower()) return;
            if (linkedCore != null) {
                if (linkedCore.items.get(item) >= ((CoreBlock.CoreBuild) linkedCore).storageCapacity) {
                    incinerateEffect(this, source);
                }
                ((CoreBlock.CoreBuild) linkedCore).noEffect = true;
                linkedCore.handleItem(source, item);
            } else {
                super.handleItem(source, item);
            }
        }

        @Override
        public int removeStack(Item item, int amount) {
            if (hadPower()) {
                int result = super.removeStack(item, amount);

                if (linkedCore != null && team == state.rules.defaultTeam && state.isCampaign()) {
                    state.rules.sector.info.handleCoreItem(item, -result);
                }

                return result;
            } else {
                return 0;
            }
        }

        @Override
        public int getMaximumAccepted(Item item) {
            return linkedCore != null ? linkedCore.getMaximumAccepted(item) : hadPower()?itemCapacity:0;
        }

        @Override
        public int explosionItemCap() {
            return linkedCore != null ? Math.min(itemCapacity / 60, 6) : hadPower()?itemCapacity:0;
        }

        @Override
        public void overwrote(Seq<Building> previous) {
            if (linkedCore == null) {
                for (Building other : previous) {
                    if (other.items != null && other.items != items) {
                        items.add(other.items);
                    }
                }

                items.each((i, a) -> items.set(i, Math.min(a, hadPower() ? itemCapacity : 0)));
            }
        }

        @Override
        public boolean canPickup() {
            return super.canPickup() && !hadPower();
        }

        @Override
        public boolean canUnload() {
            return super.canUnload() && hadPower();
        }
    }
}
