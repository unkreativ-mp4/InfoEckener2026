package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;

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


    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String pItemName) {
        itemName = pItemName;
    }

    public Texture getItemTexture() {
        return itemTexture;
    }

    public void setItemTexture(Texture pItemTexture) {
        itemTexture = pItemTexture;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int pRarity) {
        rarity = pRarity;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int pMaxStackSize) {
        maxStackSize = pMaxStackSize;
    }

}
