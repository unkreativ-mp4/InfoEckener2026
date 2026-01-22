package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Texture backgroundTexture;

    Player player;
    GameUI ui;

    @Override
    public void create() {

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

        backgroundTexture = new Texture("BackgroundTexture.jpg");
        Texture swordTexture = new Texture("Diamond_Sword_Texture.png");

        player = new Player(swordTexture);
        ui = new GameUI(spriteBatch);
        ui.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(ui.getStage()); // Stage first
        multiplexer.addProcessor(this);         // Main for keypresses

        Gdx.input.setInputProcessor(multiplexer);


    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
        if(width <= 0 || height <= 0) return;

        viewport.update(width, height, true);
        ui.resize(width, height);
    }

    @Override
    public void render() {
        player.playerMovement();
        player.dontGoPastScreen(viewport.getWorldWidth(), viewport.getWorldHeight());

        float delta = Gdx.graphics.getDeltaTime();

        ui.update(delta);
        draw();
        ui.draw();

        ui.toggleInventory();


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

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        player.draw(spriteBatch);

        spriteBatch.end();
    }
}
