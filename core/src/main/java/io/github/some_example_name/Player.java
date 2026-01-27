package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Player {
    Texture up, down, left, right;
    Sprite currentSprite;
    private float playerSpeed;
    private final int maxHealth;
    private final int maxMana;
    private int health;
    private int mana;

    public Player(Texture upTex,Texture downTex, Texture leftTex, Texture rightTex, int maxHealth, int maxMana) {
        up = upTex;
        down = downTex;
        left = leftTex;
        right = rightTex;
        currentSprite = new Sprite(down);
        currentSprite.setSize(1, 1);
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;

    }
    public void moveLeft(float amount){
        currentSprite.setTexture(left);
        currentSprite.translateX(-amount);
    }
    public void moveRight(float amount){
        currentSprite.setTexture(right);
        currentSprite.translateX(amount);
    }
    public void moveUp(float amount){
        currentSprite.setTexture(up);
        currentSprite.translateY(amount);
    }
    public void moveDown(float amount){
        currentSprite.setTexture(down);
        currentSprite.translateY(-amount);
    }

    /*public void playerMovement() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            currentSprite.translateX(speed * delta);
            switchSprite(right);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            currentSprite.translateX(-speed * delta);
            switchSprite(left);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            currentSprite.translateY(speed * delta);
            switchSprite(up);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            currentSprite.translateY(-speed * delta);
            switchSprite(down);
        }
    }*/
    /*private void switchSprite(Sprite newSprite){
        newSprite.setPosition(
            currentSprite.getX(),
            currentSprite.getY()
        );
        currentSprite = newSprite;
    }*/
    public void dontGoPastScreen(float worldWidth, float worldHeight) {
        currentSprite.setX(MathUtils.clamp(currentSprite.getX(), 0, worldWidth - currentSprite.getWidth()));
        currentSprite.setY(MathUtils.clamp(currentSprite.getY(), 0, worldHeight - currentSprite.getHeight()));
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void draw(SpriteBatch spriteBatch) {
        currentSprite.draw(spriteBatch);
        //System.out.println("Draw (Player)");

    }
}
