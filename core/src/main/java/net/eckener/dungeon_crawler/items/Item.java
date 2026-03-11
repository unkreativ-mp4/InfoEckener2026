package net.eckener.dungeon_crawler.items;

import com.badlogic.gdx.graphics.Texture;

/**
 * A simple item
 */
public class Item{

    private final String itemID;
    private String itemName;
    private Texture itemTexture;
    private int maxStackSize;

    public Item(String itemID, String itemName, Texture itemTexture, int maxStackSize) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemTexture = itemTexture;
        this.maxStackSize = maxStackSize;
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
