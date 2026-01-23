package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Player {
    Sprite up, down, left, right;
    Sprite currentSprite;
    private float playerSpeed;

    public Player(Texture upTex, Texture downTex, Texture leftTex, Texture rightTex) {
        up = new Sprite(upTex);
        down = new Sprite (downTex);
        left = new Sprite (leftTex);
        right = new Sprite (rightTex);
        currentSprite = down;
        currentSprite.setSize(1, 1);
        /*playerSprite = new Sprite(texture);
        */
    }


    public void playerMovement() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            currentSprite.translateX(speed * delta);
            currentSprite = right;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            currentSprite.translateX(-speed * delta);
            currentSprite = left;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            currentSprite.translateY(speed * delta);
            currentSprite = up;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            currentSprite.translateY(-speed * delta);
            currentSprite = down;
        }
    }
    private void switchSprite(Sprite newSprite){
        newSprite.setPosition(
            currentSprite.getX(),
            currentSprite.getY()
        );
        currentSprite = newSprite;
    }
    public void dontGoPastScreen(float worldWidth, float worldHeight) {
        currentSprite.setX(MathUtils.clamp(currentSprite.getX(), 0, worldWidth - currentSprite.getWidth()));
        currentSprite.setY(MathUtils.clamp(currentSprite.getY(), 0, worldHeight - currentSprite.getHeight()));
    }

    public void draw(SpriteBatch batch) {
        currentSprite.draw(batch);
    }
}
