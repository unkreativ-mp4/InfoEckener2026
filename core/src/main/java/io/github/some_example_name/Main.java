package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;

    BitmapFont standardFont;

    Texture backgroundTexture;

    Player player;
    //GameUI ui;
    InventoryUI inventory;
    private Stage stage;

    @Override
    public void create() {

        spriteBatch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), spriteBatch);
        viewport = new FitViewport(8, 5);

        standardFont = new BitmapFont();
        standardFont.setColor(Color.RED);

        backgroundTexture = new Texture("BackgroundTexture.jpg");
        Texture swordTexture = new Texture("Diamond_Sword_Texture.png");

        player = new Player(swordTexture);
        //ui = new GameUI(spriteBatch);
        //ui.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        //ui.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float delta = Gdx.graphics.getDeltaTime();
        //System.out.println("RENDER");

        player.playerMovement();
        player.dontGoPastScreen(viewport.getWorldWidth(), viewport.getWorldHeight());

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        player.draw(spriteBatch);
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
        //standardFont.draw(spriteBatch, "Player Health: "+player.getHealth(), 10, 0);
        standardFont.draw(spriteBatch, "Player Mana: "+player.getMana(), 10, 20);

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        player.draw(spriteBatch);

        spriteBatch.end();

    } */

    @Override
    public boolean keyDown(int keycode) {
        //System.out.println("ASDF");
        System.out.println(keycode);
        if(keycode == Input.Keys.I) {
            System.out.println("i pressed");
            inventory.openInventory();
            return true;
        }
        return false;
    }
}
