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

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Sprite diamondSwordSprite;
    //Sprite backgroundTextureSprite;

    Texture diamondSwordTexture;
    Texture backgroundTexture;

    @Override
    public void create() {
        Gdx.input.setInputProcessor(this);

        diamondSwordTexture = new Texture("Diamond_Sword_texture.png");
        diamondSwordSprite = new Sprite(diamondSwordTexture); // Initialize the sprite based on the texture
        diamondSwordSprite.setSize(1, 1); // Define the size of the sprite

        backgroundTexture = new Texture("BackgroundTexture.jpg");
       // backgroundTextureSprite = new Sprite(backgroundTexture);
       // backgroundTextureSprite.setSize(1, 1);

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
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
        diamondSwordSprite.draw(spriteBatch); // Sprites have their own draw method

        spriteBatch.end();
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            diamondSwordSprite.translateX(speed * delta); // move the bucket right
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            diamondSwordSprite.translateX(-speed * delta); // move the bucket left
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            diamondSwordSprite.translateY(speed * delta); // move the bucket left
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            diamondSwordSprite.translateY(-speed * delta); // move the bucket left
        }
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // Store the bucket size for brevity
        float bucketWidth = diamondSwordSprite.getWidth();
        float bucketHeight = diamondSwordSprite.getHeight();

        // Subtract the bucket width
        diamondSwordSprite.setX(MathUtils.clamp(diamondSwordSprite.getX(), 0, worldWidth - bucketWidth));
    }
    // Note: you can override methods from InputAdapter API to handle user's input.
}
