package Exploring.type.laser;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.Vars;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Puddles;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Tile;

import java.util.Iterator;

public class LaserBuild extends Building {
    public LaserModule laser = new LaserModule();

    @Override
    public void onDestroyed() {
        float explosiveness = this.block.baseExplosiveness;
        float flammability = 0.0f;
        float power = 0.0f;
        Item item;
        int amount;
        if (this.block.hasItems) {
            for(Iterator var4 = Vars.content.items().iterator(); var4.hasNext(); power += item.charge * Mathf.pow((float)amount, 1.1f) * 150.0f) {
                item = (Item)var4.next();
                amount = Math.min(this.items.get(item), this.explosionItemCap());
                explosiveness += item.explosiveness * (float)amount;
                flammability += item.flammability * (float)amount;
            }
        }

        if (this.block.hasLiquids) {
            flammability += this.liquids.sum((liquid, amountx) -> {
                return liquid.flammability * amountx / 2.0f;
            });
            explosiveness += this.liquids.sum((liquid, amountx) -> {
                return liquid.explosiveness * amountx / 2.0f;
            });
        }

        if (this.block.consPower != null && this.block.consPower.buffered) {
            power += this.power.status * this.block.consPower.capacity;
        }

        if (this.block.hasLiquids && Vars.state.rules.damageExplosions) {
            this.liquids.each((liquid, amountx) -> {
                float splash = Mathf.clamp(amountx / 4.0f, 0.0f, 10.0f);

                for(int i = 0; (float)i < Mathf.clamp(amountx / 5.0f, 0.0f, 30.0f); ++i) {
                    Time.run((float)i / 2.0f, () -> {
                        Tile other = Vars.world.tileWorld(this.x + (float)Mathf.range(this.block.size * 8 / 2), this.y + (float)Mathf.range(this.block.size * 8 / 2));
                        if (other != null) {
                            Puddles.deposit(other, liquid, splash);
                        }

                    });
                }

            });
        }

        power += laser.laserStorage * 47.5f;

        Damage.dynamicExplosion(this.x, this.y, flammability, explosiveness * 3.5f, power, (float)(8 * this.block.size) / 2.0f, Vars.state.rules.damageExplosions, this.block.destroyEffect);
        if (this.block.createRubble && !this.floor().solid && !this.floor().isLiquid) {
            Effect.rubble(this.x, this.y, this.block.size);
        }

        laser.clear();
    }

    @Override
    public void update() {
        super.update();

        if (laser.overload() >= 1f)
            killed();

        laser.update();
    }
}
