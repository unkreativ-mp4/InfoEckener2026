package net.eckener.dungeon_crawler.items;

import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.entities.LivingEntity;

public class VampireDagger extends Weapon {
    public VampireDagger(String itemID, String itemName, Texture itemTexture, int maxStackSize, int damage, float cooldownModifier, boolean isAOEWeapon, float range) {
        super(itemID, itemName, itemTexture, maxStackSize, damage, cooldownModifier, isAOEWeapon, range);
    }

    /**
     * Damages the attacked LivingEntity and heals the attacker
     * @param attacker the attacking LivingEntity
     * @param attacked the attacked LivingEntity
     */
    @Override
    public void attack(LivingEntity attacker, LivingEntity attacked) {
        attacked.takeDamage(getDamage());
        attacker.heal(getDamage());
    }
}
