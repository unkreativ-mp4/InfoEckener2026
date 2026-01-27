package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InventoryUI extends Table {

    static final float BASE_SLOT_SIZE = 48f;
    static final float BASE_SLOT_PAD  = 4f;
    static final float BASE_INV_PAD   = 12f;
    private boolean inventoryVisible;

    public InventoryUI(Inventory inventory, Texture inventoryTexture, Texture slotTexture, float uiScale){

        //this.setFillParent(true);
        inventory.setVisible(false);

        buildInventoryUI(inventory, inventoryTexture, slotTexture, uiScale);
    }

    private void buildInventoryUI(Inventory inventory, Texture inventoryTexture, Texture slotTexture, float uiScale) {

        float slotSize = BASE_SLOT_SIZE * uiScale;
        float slotPad  = BASE_SLOT_PAD  * uiScale;
        float invPad   = BASE_INV_PAD   * uiScale;

        pad(invPad);
        defaults().size(slotSize).pad(slotPad);

        NinePatch inventoryTexturePatch = new NinePatch(inventoryTexture, 11, 11, 11, 11);
        setBackground(new NinePatchDrawable(inventoryTexturePatch));

        createSlotsUI(slotTexture, inventory);
    }
    private void createSlotsUI(Texture slotTexture, Inventory inventory) {
        NinePatch slotUIPatch = new NinePatch(slotTexture, 2, 2, 3, 2);
        NinePatchDrawable slotBackground = new NinePatchDrawable(slotUIPatch);

        Table[][] slots = inventory.getSlots();
        for (int r = 0; r < slots.length; r++) {
            for (int c = 0; c < slots[r].length; c++) {
                slots[r][c].setBackground(slotBackground);
            }
        }
    }


    public void openInventory() {
        inventoryVisible = !inventoryVisible;
        setVisible(inventoryVisible);
        setTouchable(inventoryVisible ? Touchable.enabled : Touchable.disabled);

    }
    public boolean getInventoryVisible() {
        return inventoryVisible;
    }

}

