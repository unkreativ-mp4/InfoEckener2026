package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import static com.badlogic.gdx.utils.compression.CRC.Table;

public class Item{

    private String itemID;
    private String itemName;
    private Texture itemTexture;
    private int rarety;
    private int maxStackSize;

    public Item(String pItemID, String pItemName, Texture pItemTexture, int pRarety, int pMaxStackSize) {
        itemID = pItemID;
        itemName = pItemName;
        itemTexture = pItemTexture;
        rarety = pRarety;
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

    public void setItemTexture(Texture pItemTexure) {
        itemTexture = pItemTexure;
    }

    public int getRarety() {
        return rarety;
    }

    public void setRarety(int pRarety) {
        rarety = pRarety;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public void setMaxStackSize(int pMaxStackSize) {
        maxStackSize = pMaxStackSize;
    }

}
