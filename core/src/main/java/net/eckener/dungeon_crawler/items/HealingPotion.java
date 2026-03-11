package net.eckener.dungeon_crawler.items;

import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.entities.Player;

public class HealingPotion extends Item{

    private final int healAmount;

    public HealingPotion(String pItemID, String pItemName, Texture pItemTexture, int pMaxStackSize, int pHealAmount) {
        super(pItemID, pItemName, pItemTexture, pMaxStackSize);
        this.healAmount = pHealAmount;
    }

    public void heal(Player player) {
        player.heal(healAmount);
    }

}
