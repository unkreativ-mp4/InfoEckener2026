package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Expands {@link Item} by things like a {@code damage} attribute
 */
public abstract class Weapon extends Item{
    private final int damage;
    private final float cooldownModifier;

    public Weapon(String pItemID, String pItemName, Texture pItemTexture, int pRarity, int pMaxStackSize, int damage, float cooldownModifier) {
        super(pItemID, pItemName, pItemTexture, pRarity, pMaxStackSize);
        this.damage = damage;
        this.cooldownModifier = cooldownModifier;
    }

    /**
     * @return the {@code damage} of the Weapon
     */
    public int getDamage() {
        return damage;
    }

    /**
     * @return the {@code cooldownModifier} of the Weapon
     */
    public float getCooldownModifier() {
        return cooldownModifier;
    }

    public abstract void attack(Player player,Enemy enemy, Viewport viewport);
}
