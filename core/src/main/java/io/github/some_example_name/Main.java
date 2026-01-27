package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Texture backgroundTexture;
    Texture characterTextureUp, characterTextureDown, characterTextureLeft, characterTextureRight;
    Texture slotTexture, inventoryTexture;

    CustomLabel healthLabel;
    CustomLabel manaLabel;

    Player player;
    InventoryUI inventory;
    private Stage stage;

    private final IntSet downKeys = new IntSet(20);

    @Override
    public void create() {

        spriteBatch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), spriteBatch);
        viewport = new FitViewport(8, 5);

        healthLabel = new CustomLabel("Player Health: ", 10, 25);
        manaLabel = new CustomLabel("Player Mana: ", 10, 10);
        stage.addActor(healthLabel.getLabel());
        stage.addActor(manaLabel.getLabel());

        backgroundTexture = new Texture("BackgroundTexture.jpg");
        characterTextureDown = new Texture("Character_Texture_Front.png");
        characterTextureUp = new Texture("Character_Texture_Back.png");
        characterTextureLeft = new Texture("Character_Texture_Left.png");
        characterTextureRight = new Texture ("Character_Texture_Right.png");
        slotTexture = new Texture("Inventory_Slot_Texture.png");
        inventoryTexture = new Texture("inventory_Background_Texture.png");

        player = new Player(characterTextureUp, characterTextureDown, characterTextureLeft, characterTextureRight, 100, 100);

        inventory = new InventoryUI(stage, inventoryTexture, slotTexture, 1.3f, 4, 7);
        inventory.setDebug(false);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage); // Stage first
        multiplexer.addProcessor(this);         // Main for keypresses
        Gdx.input.setInputProcessor(multiplexer);


    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
        if(width <= 0 || height <= 0) return;

        viewport.update(width, height, true);
    }

    @Override
    public void render() {

        float delta = 4f * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveDown(delta);
        }
        player.dontGoPastScreen(viewport.getWorldWidth(), viewport.getWorldHeight());

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        player.draw(spriteBatch);

        healthLabel.setText("Player Health: " + player.getHealth());
        manaLabel.setText("Player Mana: " + player.getMana());

        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown (int keycode) {
        downKeys.add(keycode);
        System.out.println(downKeys+" Tasten wurde gedrückt (Keycode)");

        if (downKeys.size >= 2){
            onMultipleKeysDown(keycode);
        } else {

            if(keycode == Input.Keys.I) {
                inventory.openInventory();
            }
            if(keycode == Input.Keys.H) {
                player.addHealth(1);
            }
            if(keycode == Input.Keys.M) {
                player.addMana(1);
            }
        }
        return true;
    }

    @Override
    public boolean keyUp (int keycode) {
        downKeys.remove(keycode);
        return true;
    }

    private void onMultipleKeysDown (int mostRecentKeycode){
        //Keys that are currently down are in the IntSet.
        if (downKeys.contains(Input.Keys.SHIFT_LEFT) && downKeys.contains(Input.Keys.M)){
            player.addMana(-1);
        }
        if (downKeys.contains(Input.Keys.SHIFT_LEFT) && downKeys.contains(Input.Keys.H)){
            player.addHealth(-1);
        }
    }
}
