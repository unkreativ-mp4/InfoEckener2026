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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;

    BitmapFont font;
    Label healthLabel;

    Texture backgroundTexture;
    Texture up, down, left, right;

    Player player;
    InventoryUI inventory;
    private Stage stage;

    @Override
    public void create() {

        spriteBatch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), spriteBatch);
        viewport = new FitViewport(8, 5);

        font = new BitmapFont();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;

        healthLabel = new Label("Player Health: ", style);
        healthLabel.setPosition(20, 20); // screen-space pixels
        stage.addActor(healthLabel);

        backgroundTexture = new Texture("BackgroundTexture.jpg");
        down = new Texture("Character_Sprite_Front.png");
        up = new Texture("Character_Sprite_Back.png");
        left = new Texture("Character_Sprite_Left.png");
        right = new Texture ("Character_Sprite_Right.png");


        player = new Player(up, down, left, right, 100, 100);

        Texture slotTexture = new Texture("Inventory_Slot_Texture.png");
        Texture inventoryTexture = new Texture("inventory_Background_Texture.png");
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
        //font.draw(spriteBatch, "Player Health: ", 100, 100);
        healthLabel.setText("Player Health: " + player.getHealth());
        spriteBatch.end();

        stage.act(delta);
        stage.draw();



        //draw();
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

    /*private void draw() {
        //System.out.println("DRAW (Main)");
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        player.setHealth(10); player.setMana(20);
        standardFont.draw(spriteBatch, "Player Health: "+player.getHealth(), 10, 0);
        standardFont.draw(spriteBatch, "Player Mana: "+player.getMana(), 10, 20);

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        player.draw(spriteBatch);

        spriteBatch.end();

    } */

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode+" Taste wurde gedrückt (Keycode)");
        if(keycode == Input.Keys.I) {
            inventory.openInventory();
            return true;
        }
        return false;
    }
}
