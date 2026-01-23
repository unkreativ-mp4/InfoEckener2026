package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Player {

    private Sprite playerSprite;
    private float playerSpeed;

    private int health;
    private int mana;

    public Player(Texture texture) {
        playerSprite = new Sprite(texture);
        playerSprite.setSize(1, 1);
    }


    public void playerMovement() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerSprite.translateX(speed * delta);
            playerSprite.setFlip(false, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerSprite.translateX(-speed * delta);
            playerSprite.setFlip(true, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerSprite.translateY(speed * delta);
            playerSprite.setFlip(true, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerSprite.translateY(-speed * delta);
            playerSprite.setFlip(false, true);
        }
    }

    public void dontGoPastScreen(float worldWidth, float worldHeight) {
        playerSprite.setX(MathUtils.clamp(playerSprite.getX(), 0, worldWidth - playerSprite.getWidth()));
        playerSprite.setY(MathUtils.clamp(playerSprite.getY(), 0, worldHeight - playerSprite.getHeight()));
    }

    public void setHealth(int newHealth) {
        health = newHealth;
    }

    public int getHealth() {
        return mana;
    }

    public void setMana(int newMana) {
        mana = newMana;
    }

    public int getMana() {
        return mana;
    }

    public void draw(SpriteBatch batch) {
        playerSprite.draw(batch);
    }
}
