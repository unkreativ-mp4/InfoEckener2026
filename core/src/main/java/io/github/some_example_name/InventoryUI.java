package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class InventoryUI extends Table {

    private Stage stage;
    static final float BASE_SLOT_SIZE = 48f;
    static final float BASE_SLOT_PAD  = 4f;
    static final float BASE_INV_PAD   = 12f;

    private final int rows;
    private final int cols;

    private boolean inventoryVisible;

    public InventoryUI(Stage stage, Texture inventoryTexture, Texture slotTexture, float uiScale, int rows, int cols){
        this.stage = stage;

        this.rows = rows;
        this.cols = cols;



        buildRootTable();
        buildInventoryUI(inventoryTexture, slotTexture, uiScale);

    }

    public void buildRootTable() {

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        rootTable.setDebug(true);
        rootTable.addActor(this);
        setFillParent(true);


    }

    private void buildInventoryUI(Texture inventoryTexture, Texture slotTexture, float uiScale) {

        float slotSize = BASE_SLOT_SIZE * uiScale;
        float slotPad  = BASE_SLOT_PAD  * uiScale;
        float invPad   = BASE_INV_PAD   * uiScale;

        NinePatch inventoryTexturePatch = new NinePatch(inventoryTexture, 40, 40, 40, 40);
        setBackground(new NinePatchDrawable(inventoryTexturePatch));

        pad(invPad);
        defaults().size(slotSize).pad(slotPad);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                add(createSlot(slotTexture));
            }
            row();
        }

    }
    private Table createSlot(Texture slotTexture) {
        Table slot = new Table();
        NinePatch InventorySlotPatch = new NinePatch(slotTexture, 6, 6, 6, 6);
        slot.setBackground(new NinePatchDrawable(InventorySlotPatch));
        return slot;
    }

    public void draw() {
        stage.draw();
    }

    public void clear() {
        stage.clear();
    }

    public void openInventory() {
        inventoryVisible = !inventoryVisible;
        if(!inventoryVisible) draw();
        else clear();


    }
    public boolean getInventoryVisible() {
        return inventoryVisible;
    }

   /* private void buildInventoryUI{

        float slotSize = BASE_SLOT_SIZE * inventoryScale;
        float slotPad  = BASE_SLOT_PAD  * inventoryScale;
        float invPad   = BASE_INV_PAD   * inventoryScale;



        inventoryTable.pad(invPad);
        inventoryTable.defaults().size(slotSize).pad(slotPad);

        inventoryTable.setDebug(false);

        Texture inventoryTexture = new Texture("inventory_Background_Texture.png");
        NinePatch inventoryTexturePatch = new NinePatch(inventoryTexture, 40, 40, 40, 40);
        inventoryTable.setBackground(new NinePatchDrawable(inventoryTexturePatch));

        rootTable.add(inventoryTable).center();

    int rows = 4;
    int cols = 7;

        for (int y = 0; y < rows; y++) {
        for (int x = 0; x < cols; x++) {
            Table slot = createSlot();
            inventoryTable.add(createSlot());
        }
        inventoryTable.row();
    }
    private Table createSlot() {
            Table slot = new Table();

            Texture slotTexture = new Texture("Inventory_Slot_Texture.png");
            NinePatch InventorySlotPatch = new NinePatch(slotTexture, 6, 6, 6, 6);
            slot.setBackground(new NinePatchDrawable(InventorySlotPatch));

            slot.setDebug(true);
            return slot;

        } */
}

