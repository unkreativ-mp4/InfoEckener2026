package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class GameUI {

    private Stage stage;
    private Table inventoryTable;
    private boolean inventoryVisible;
    private float inventoryScale = 2.5f;

    static final float BASE_SLOT_SIZE = 48f;
    static final float BASE_SLOT_PAD  = 4f;
    static final float BASE_INV_PAD   = 12f;

    float slotSize = BASE_SLOT_SIZE * inventoryScale;
    float slotPad  = BASE_SLOT_PAD  * inventoryScale;
    float invPad   = BASE_INV_PAD   * inventoryScale;


    public GameUI(SpriteBatch batch) {
        stage = new Stage(new ScreenViewport(), batch);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);
        rootTable.setDebug(true);

        inventoryTable = new Table();

        inventoryTable.pad(invPad);
        inventoryTable.defaults().size(slotSize).pad(slotPad);

        inventoryTable.setDebug(false);

        Texture inventoryTexture = new Texture("inventory_Background_Texture.png");
        NinePatch inventoryTexturePatch = new NinePatch(inventoryTexture, 40, 40, 40, 40);
        inventoryTable.setBackground(new NinePatchDrawable(inventoryTexturePatch));

        rootTable.center();
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
    }

    public void update(float delta) {
        stage.act(delta);
    }

    public void draw() {
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    private Table createSlot() {
        Table slot = new Table();

        Texture slotTexture = new Texture("Inventory_Slot_Texture.png");
        NinePatch InventorySlotPatch = new NinePatch(slotTexture, 6, 6, 6, 6);
        slot.setBackground(new NinePatchDrawable(InventorySlotPatch));

        slot.setDebug(true);
        return slot;
    }

    public void toggleInventory() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            inventoryVisible = true;
        }

    }

}
