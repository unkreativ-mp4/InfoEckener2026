package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;

public class Weapon extends Item{
    private final int damage;
    private final float cooldownModifier;

    public Weapon(String pItemID, String pItemName, Texture pItemTexture, int pRarity, int pMaxStackSize, int damage, float cooldownModifier) {
        super(pItemID, pItemName, pItemTexture, pRarity, pMaxStackSize);
        this.damage = damage;
        this.cooldownModifier = cooldownModifier;
    }

    public int getDamage() {
        return damage;
    }

    public float getCooldownModifier() {
        return cooldownModifier;
    }
}
