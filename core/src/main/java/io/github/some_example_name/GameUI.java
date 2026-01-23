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

    public GameUI(SpriteBatch batch) {
        stage = new Stage(new ScreenViewport(), batch);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        Texture slotTexture = new Texture("Inventory_Slot_Texture.png");
        Texture inventoryTexture = new Texture("inventory_Background_Texture.png");
        InventoryUI inventory = new InventoryUI(inventoryTexture, slotTexture, 2.5f, 4, 7);

        rootTable.setDebug(true);
        inventory.setDebug(true);
        rootTable.add(inventory).center();
    }

    public void update() {
        stage.act(Gdx.graphics.getDeltaTime());
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
}



