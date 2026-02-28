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
import net.eckener.dungeon_crawler.items.ItemStack;

public class InventoryUI extends Table {

    private boolean inventoryVisible;
    private float slotSize = 32f;
    private float slotPad = 3f;
    private Texture slotBackgroundTexture;
    private final BitmapFont uiFont = new BitmapFont();
    private boolean dragging = false;
    private GridPoint2 dragOrigin = null;     // (col,row) like your userObject
    private ItemStack draggedStack = null;    // the stack we are moving 1 from
    private Image dragGhost = new Image();    // image following the pointer


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

        Skin titleSkin = new Skin(Gdx.files.internal("uiskin.json"));
        Label title = new Label("Inventory", titleSkin);
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

                SlotWidget slot = new SlotWidget(inventory.getItemStack(row,col), uiFont);
                cell.setUserObject(slot);
                slot.setTouchable(Touchable.enabled);

                slot.setUserObject(new GridPoint2(col, row));
                final int r = row;
                final int c = col;

                slot.addListener(new com.badlogic.gdx.scenes.scene2d.InputListener()  {


                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                        // Only start dragging if slot has an item
                        ItemStack stackHere = inventory.getItemStack(r, c);
                        if (stackHere == null) return false;

                        dragging = true;
                        dragOrigin = new GridPoint2(c, r);
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

                        if (target != null && target.getUserObject() instanceof GridPoint2 targetPos) {

                            int newCol = targetPos.x;
                            int newRow = targetPos.y;

                            int orgCol = dragOrigin.x;
                            int orgRow = dragOrigin.y;

                            // Ignore dropping back on same slot if you want
                            if (!(newRow == orgRow && newCol == orgCol)) {
                                // Move ONE item using your current moveItemtoSlot implementation
                                if(!shiftClicked) {
                                    inventory.moveItemtoSlot(
                                        inventory.getItemStacks(),
                                        inventory.getItemStack(orgRow, orgCol),
                                        newRow, newCol,
                                        orgRow, orgCol

                                    );
                                }
                                else {
                                    inventory.moveWholeItemStacktoSlot(
                                        inventory.getItemStacks(),
                                        inventory.getItemStack(orgRow, orgCol),
                                        newRow,
                                        newCol,
                                        orgRow,
                                        orgCol
                                    );
                                }
                            }
                        }

                        // Reset drag state
                        dragging = false;
                        dragOrigin = null;
                        draggedStack = null;

                        // Rebuild UI so numbers/textures update
                        buildInventoryUI(inventory);

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
