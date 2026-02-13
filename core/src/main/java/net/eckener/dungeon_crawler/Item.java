package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

/**
 * A simple item
 */
public class Item{

    private final String itemID;
    private String itemName;
    private Texture itemTexture;
    private int rarity;
    private int maxStackSize;

    public Item(String pItemID, String pItemName, Texture pItemTexture, int pRarity, int pMaxStackSize) {
        itemID = pItemID;
        itemName = pItemName;
        itemTexture = pItemTexture;
        rarity = pRarity;
        maxStackSize = pMaxStackSize;

        NinePatch itemTexturePatch = new NinePatch(pItemTexture);
    }

    /**
     * @return the {@code ItemID}
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * @return the Item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param pItemName Sets the Item name
     */
    public void setItemName(String pItemName) {
        itemName = pItemName;
    }

    /**
     * @return the {@link Texture} of the Item
     */
    public Texture getItemTexture() {
        return itemTexture;
    }

    /**
     * @param pItemTexture Sets the {@link Texture} of the Item
     */
    public void setItemTexture(Texture pItemTexture) {
        itemTexture = pItemTexture;
    }

    /**
     * @return the {@code rarity} of the Item
     */
    public int getRarity() {
        return rarity;
    }

    /**
     * @param pRarity sets the {@code rarity} of the Item
     */
    public void setRarity(int pRarity) {
        rarity = pRarity;
    }

    /**
     * @return the maximum stack size of the Item
     */
    public int getMaxStackSize() {
        return maxStackSize;
    }

    /**
     * @param pMaxStackSize sets the maximum stack size of the Item
     */
    public void setMaxStackSize(int pMaxStackSize) {
        maxStackSize = pMaxStackSize;
    }

}
