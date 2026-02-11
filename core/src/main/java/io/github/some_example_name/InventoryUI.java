package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import org.w3c.dom.Text;

import java.util.Objects;

public class InventoryUI extends Table {

    private boolean inventoryVisible;
    private float slotSize = 48f;
    private float slotPad = 4f;
    private Texture slotBackgroundTexture;


    public InventoryUI(Inventory inventory, Texture inventoryTexture, Texture pSlotBackgroundTexture, float uiScale){

        this.setFillParent(true);

        this.setVisible(false);
        inventory.setVisible(false);
        setTouchable(Touchable.disabled);

        center();

        slotSize = slotSize * uiScale;
        slotPad = slotPad * uiScale;
        slotBackgroundTexture = pSlotBackgroundTexture;

        NinePatch invPatch = new NinePatch(inventoryTexture, 11, 11, 11, 11);
        invPatch.scale(uiScale, uiScale);
        Table invPanel = new Table();
        invPanel.setBackground(new NinePatchDrawable(invPatch));
        invPanel.pad(12f);

        buildInventoryUI(inventory);

        invPanel.add(inventory).center();
        add(invPanel).expand().center();

        pack();
    }

    private void buildInventoryUI(Inventory inventory) {

        inventory.clearChildren();
        inventory.defaults().size(slotSize).pad(slotPad);

        NinePatch slotBackgroundPatch = new NinePatch(slotBackgroundTexture, 3, 3, 3, 3);

        for (int row = 0; row < inventory.getRows(); row++) {
            for (int col = 0; col < inventory.getCols(); col++) {
                Table cell = new Table();
                cell.setBackground(new NinePatchDrawable(slotBackgroundPatch));

                SlotWidget slot = new SlotWidget(inventory.getItemStack(row,col), new BitmapFont());
                slot.setUserObject(new GridPoint2(col, row));
                final int r = row;
                final int c = col;

                slot.addListener(new ClickListener() {
                    ItemStack itemStack = inventory.getItemStack(r, c);
                    public void clicked(InputEvent event, float x, float y) {
                        if (itemStack != null) {
                            System.out.println("Clicked item: " + itemStack.getItem().getItemName() + " at " + r + "," + c);
                            inventory.moveItemtoSlot(inventory.getItemStacks(), inventory.getItemStack(r, c), r, c + 1, r, c );
                            buildInventoryUI(inventory);
                        } else {
                            System.out.println("Clicked empty slot: " + r + "," + c);
                        }

                        event.stop();
                    }


                });


                cell.add(slot).grow();
                inventory.add(cell).size(slotSize).pad(slotPad);
                if(col == inventory.getCols() - 1) {
                    inventory.row();
                }
            }
        }
    }

    public void openInventory(Inventory inventory) {
        buildInventoryUI(inventory);
        inventoryVisible = !inventoryVisible;
        setVisible(inventoryVisible);
        inventory.setVisible(inventoryVisible);
        setTouchable(inventoryVisible ? Touchable.enabled : Touchable.disabled);
    }


    public boolean getInventoryVisible() {
        return inventoryVisible;
    }

    public void refresh(Inventory inventory, int row, int col) {

        if(inventory.getItemStack(row, col) != null) add(new Image(inventory.getItemStack(row, col).getItem().getItemTexture())).grow();
        else add().grow();
    }

    private SlotWidget findSlotUnderPointer(float stageX, float stageY) {   //chatgpt hat gekocht
        if (getStage() == null) return null;

        var hit = getStage().hit(stageX, stageY, true);
        while (hit != null && !(hit instanceof SlotWidget)) {
            hit = hit.getParent();
        }
        return (SlotWidget) hit;
    }

}

