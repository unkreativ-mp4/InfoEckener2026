package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class InventoryUI extends Table {

    private boolean inventoryVisible;

    public InventoryUI(Inventory inventory, Texture inventoryTexture, Texture slotTexture){

        this.setFillParent(true);

        this.setVisible(false);
        inventory.setVisible(false);
        setTouchable(Touchable.disabled);

        center();

        NinePatch invPatch = new NinePatch(inventoryTexture, 11, 11, 11, 11);
        Table invPanel = new Table();
        invPanel.setBackground(new NinePatchDrawable(invPatch));

        invPanel.pad(12f);
        invPanel.add(inventory).center();
        add(invPanel).center();

        applySlotBackgrounds(inventory, slotTexture);
    }

    private void applySlotBackgrounds(Inventory inventory, Texture slotTexture) {
        NinePatch slotPatch = new NinePatch(slotTexture, 2, 2, 3, 2);
        NinePatchDrawable slotBackground = new NinePatchDrawable(slotPatch);

        Table[][] slots = inventory.getSlots();
        for (Table[] slot : slots) {
            for (Table table : slot) {
                table.setBackground(slotBackground);
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

