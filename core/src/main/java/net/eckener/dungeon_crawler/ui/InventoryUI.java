package net.eckener.dungeon_crawler.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Array;
import net.eckener.dungeon_crawler.items.ItemStack;

public class InventoryUI extends Table {

    private boolean inventoryVisible;
    private float slotSize = 32f;
    private float slotPad = 3f;

    private Texture slotBackgroundTexture;
    private final BitmapFont uiFont = new BitmapFont();
    private boolean dragging = false;
    private SlotReference dragOrigin = null;
    private ItemStack draggedStack = null;
    private Inventory dragOriginInv = null; // optional (dragOrigin already contains inv)   // the stack we are moving 1 from
    private Image dragGhost = new Image();// image following the pointer
    private final Array<Inventory> openInventories = new Array<>();


    public InventoryUI(Inventory inventory, Texture inventoryTexture, Texture pSlotBackgroundTexture, float uiScale) {

        //this.setFillParent(true);

        this.setVisible(false);
        inventory.setVisible(false);
        setTouchable(Touchable.disabled);

        //center();
        //this.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        slotSize = slotSize * uiScale;
        slotPad = slotPad * uiScale;
        slotBackgroundTexture = pSlotBackgroundTexture;


        NinePatch invPatch = new NinePatch(inventoryTexture, 11, 11, 11, 11);
        invPatch.scale(uiScale, uiScale);
        Table invPanel = new Table();
        invPanel.setBackground(new NinePatchDrawable(invPatch));
        invPanel.pad(12f);

        buildInventoryUI(inventory);

        Skin titleSkin = new Skin(Gdx.files.internal("uiskin.json"));
        Label title = new Label(inventory.getInventoryName(), titleSkin);
        invPanel.add(title).center().padBottom(8f * uiScale).row();
        invPanel.add(inventory).center();
        add(invPanel).center();

        dragGhost.setVisible(false);
        addActor(dragGhost);

        pack();
    }

    private void buildInventoryUI(Inventory inventory) {

        inventory.clearChildren();
        inventory.defaults().size(slotSize).pad(slotPad);

        NinePatch slotBackgroundPatch = new NinePatch(slotBackgroundTexture, 0, 0, 0, 0);

        for (int row = 0; row < inventory.getRows(); row++) {
            for (int col = 0; col < inventory.getCols(); col++) {
                Table cell = new Table();
                cell.setBackground(new NinePatchDrawable(slotBackgroundPatch));

                SlotWidget slot = new SlotWidget(inventory.getItemStack(row, col), uiFont);
                slot.setUserObject(new SlotReference(inventory, row, col));
                cell.setUserObject(slot);
                slot.setTouchable(Touchable.enabled);

                slot.setUserObject(new SlotReference(inventory, row, col));
                final int r = row;
                final int c = col;

                slot.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener() {


                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                        SlotReference origin = (SlotReference) slot.getUserObject();

                        // Only start dragging if slot has an item
                        ItemStack stackHere = origin.inv.getItemStack(origin.row, origin.col);
                        if (stackHere == null) return false;

                        dragging = true;
                        dragOrigin = origin;
                        draggedStack = stackHere;


                        // Setup ghost image from the item's texture
                        var region = new com.badlogic.gdx.graphics.g2d.TextureRegion(stackHere.getItem().getItemTexture());
                        dragGhost.setDrawable(new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(region));
                        dragGhost.setSize(slotSize, slotSize);
                        dragGhost.setVisible(true);
                        dragGhost.setTouchable(Touchable.disabled);

                        // Place ghost at current pointer (convert to stage)
                        float stageX = event.getStageX();
                        float stageY = event.getStageY();
                        setGhostAt(stageX, stageY);

                        event.stop();
                        return true; // we handle the drag

                    }

                    @Override
                    public void touchDragged(InputEvent event, float x, float y, int pointer) {
                        if (!dragging) return;

                        setGhostAt(event.getStageX(), event.getStageY());
                        event.stop();
                    }

                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                        boolean shiftClicked = Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT);
                        if (!dragging) return;

                        // Hide ghost
                        dragGhost.setVisible(false);

                        // Determine drop target slot
                        SlotWidget target = findSlotUnderPointer(event.getStageX(), event.getStageY());
                        if (target != null && target.getUserObject() instanceof SlotReference) {
                            SlotReference targetRef = (SlotReference) target.getUserObject();

                            SlotReference originRef = dragOrigin;

                            // same slot? ignore
                            if (!(originRef.inv == targetRef.inv &&
                                originRef.row == targetRef.row &&
                                originRef.col == targetRef.col)) {

                                if (originRef.inv == targetRef.inv) {
                                    // SAME inventory: use your existing methods
                                    if (!shiftClicked) {
                                        originRef.inv.moveItemtoSlot(
                                            originRef.inv.getItemStacks(),
                                            originRef.inv.getItemStack(originRef.row, originRef.col),
                                            targetRef.row, targetRef.col,
                                            originRef.row, originRef.col
                                        );
                                    } else {
                                        originRef.inv.moveWholeItemStacktoSlot(
                                            originRef.inv.getItemStacks(),
                                            originRef.inv.getItemStack(originRef.row, originRef.col),
                                            originRef.inv.getItemStack(originRef.row, originRef.col).getAmount(),
                                            targetRef.row, targetRef.col,
                                            originRef.row, originRef.col
                                        );
                                    }
                                } else {
                                    // DIFFERENT inventories:
                                    // You need a method that transfers from A -> B
                                    // (recommended: implement a transfer helper, see below)
                                    if (!shiftClicked) {
                                        originRef.inv.transferOneTo(targetRef.inv, originRef.row, originRef.col, targetRef.row, targetRef.col);
                                    } else {
                                        originRef.inv.transferWholeStackTo(targetRef.inv, originRef.row, originRef.col, targetRef.row, targetRef.col);
                                    }
                                }
                            }
                        }

                        // Reset drag state
                        dragging = false;
                        dragOrigin = null;
                        draggedStack = null;

                        // Rebuild UI so numbers/textures update
                        buildInventoryUI(inventory);
                        rebuildAllOpenInventories();

                        event.stop();
                    }
                });


                cell.add(slot).grow();
                inventory.add(cell).size(slotSize).pad(slotPad);
                if (col == inventory.getCols() - 1) {
                    inventory.row();
                }
            }
        }
    }

    public void inventoryVisebilityManagement(Inventory inventory) {
        if (!inventoryVisible) {
            openInventory(inventory);
            inventoryVisible = !inventoryVisible;
        }

        else {
            closeInventory(inventory);
            inventoryVisible = !inventoryVisible;
        }
    }

    public void openInventory(Inventory inventory) {
        if (!openInventories.contains(inventory, true)) openInventories.add(inventory);
        inventory.setVisible(true);
        setVisible(true);
        setTouchable(Touchable.enabled);
        rebuildAllOpenInventories();
    }

    public void closeInventory(Inventory inventory) {
        openInventories.removeValue(inventory, true);
        inventory.setVisible(false);
        if (openInventories.size == 0) {
            setVisible(false);
            setTouchable(Touchable.disabled);
        }
    }

    private void rebuildAllOpenInventories() {
        for (Inventory inv : openInventories) {
            buildInventoryUI(inv);
        }
    }


    public boolean getInventoryVisible() {
        return inventoryVisible;
    }

    private void setGhostAt(float stageX, float stageY) {
        dragGhost.setPosition(
            stageX - dragGhost.getWidth() / 2f,
            stageY - dragGhost.getHeight() / 2f
        );
    }


    private SlotWidget findSlotUnderPointer(float stageX, float stageY) {   //chatgpt hat gekocht
        if (getStage() == null) return null;

        var hit = getStage().hit(stageX, stageY, true);
        if(hit == dragGhost) hit = null;
        if (hit instanceof Table && hit.getUserObject() instanceof SlotWidget) {
            return (SlotWidget) hit.getUserObject();
        }

        while (hit != null && !(hit instanceof SlotWidget)) {
            hit = hit.getParent();
        }
        return (SlotWidget) hit;
    }
}
