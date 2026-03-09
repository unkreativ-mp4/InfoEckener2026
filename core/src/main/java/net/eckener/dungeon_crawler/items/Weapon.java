package net.eckener.dungeon_crawler.items;

import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.entities.LivingEntity;

/**
 * Expands {@link Item} by things like a {@code damage} attribute
 */
public abstract class Weapon extends Item{
    private final int damage;
    private final float range;
    private final float cooldownModifier;
    private final boolean isMeleeWeapon;

    public Weapon(String itemID, String itemName, Texture itemTexture, int maxStackSize, int damage, float cooldownModifier, boolean isMeleeWeapon, float range) {
        super(itemID, itemName, itemTexture, maxStackSize);
        this.damage = damage;
        this.cooldownModifier = cooldownModifier;
        this.isMeleeWeapon = isMeleeWeapon;
        this.range = range;
    }

    /**
     * @return the {@code damage} of the Weapon
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @return if the Weapon is a melee weapon
     */
    public boolean isMeleeWeapon() {
        return isMeleeWeapon;
    }

    /**
     * @return the range of the Weapon
     */
    public float getRange() {
        return range;
    }

    /**
     * @return the {@code cooldownModifier} of the Weapon
     */
    public float getCooldownModifier() {
        return cooldownModifier;
    }

    public abstract void attack(LivingEntity attacker, LivingEntity attacked);
}
