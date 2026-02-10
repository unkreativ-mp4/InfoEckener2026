package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class InventoryUI extends Table {

    private boolean inventoryVisible;

    public InventoryUI(Inventory inventory, Texture inventoryTexture, Texture slotBackgroundTexture, float uiScale){

        this.setFillParent(true);

        this.setVisible(false);
        inventory.setVisible(false);
        setTouchable(Touchable.disabled);

        center();

        float slotSize = 48f * uiScale;
        float slotPad = 4f * uiScale;

        NinePatch invPatch = new NinePatch(inventoryTexture, 11, 11, 11, 11);
        Table invPanel = new Table();
        invPanel.setBackground(new NinePatchDrawable(invPatch));
        invPanel.pad(12f);

        buildInventoryUI(inventory, slotBackgroundTexture, slotSize, slotPad);

        invPanel.add(inventory).center();
        add(invPanel).center();

        pack();
    }

    private void buildInventoryUI(Inventory inventory, Texture slotBackgroundTexture, float slotSize, float slotPad) {

        inventory.clearChildren();
        inventory.defaults().size(slotSize).pad(slotPad);

        NinePatch slotBackgroundPatch = new NinePatch(slotBackgroundTexture, 3, 3, 3, 3);
        Drawable slotBackground = new NinePatchDrawable(slotBackgroundPatch);

        for (int row = 0; row < inventory.getRows(); row++) {
            for (int col = 0; col < inventory.getCols(); col++) {
                Table cell = new Table();
                cell.setBackground(new NinePatchDrawable(slotBackgroundPatch));

                SlotWidget slot = new SlotWidget(inventory.getItemStack(row,col), new BitmapFont());
                cell.add(slot).grow();

                inventory.add(cell).size(slotSize).pad(slotPad);

                if(col == inventory.getCols() - 1) {
                    inventory.row();
                }
            }
        }
    }

    public void openInventory(Inventory inventory) {
        inventoryVisible = !inventoryVisible;
        setVisible(inventoryVisible);
        inventory.setVisible(inventoryVisible);
        setTouchable(inventoryVisible ? Touchable.enabled : Touchable.disabled);
    }

    public boolean getInventoryVisible() {
        return inventoryVisible;
    }
}

