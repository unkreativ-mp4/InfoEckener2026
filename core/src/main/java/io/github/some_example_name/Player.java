package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Player {
    Texture upTexture, downTexture, leftTexture, rightTexture;
    Sprite PlayerSprite;
    private float playerSpeed;
    private final int maxHealth;
    private final int maxMana;
    private int health;
    private int mana;

    public Player(Texture upTexture,Texture downTexture, Texture leftTexture, Texture rightTexture, int maxHealth, int maxMana) {
        this.upTexture = upTexture;
        this.downTexture = downTexture;
        this.leftTexture = leftTexture;
        this.rightTexture = rightTexture;
        PlayerSprite = new Sprite(this.downTexture);
        PlayerSprite.setSize(1, 1);
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;

    }

    public void move(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            PlayerSprite.setTexture(upTexture);
            PlayerSprite.translateY(deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            PlayerSprite.setTexture(leftTexture);
            PlayerSprite.translateX(-deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            PlayerSprite.setTexture(downTexture);
            PlayerSprite.translateY(-deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            PlayerSprite.setTexture(rightTexture);
            PlayerSprite.translateX(deltaTime);
        }
    }

    public void dontGoPastScreen(float worldWidth, float worldHeight) {
        PlayerSprite.setX(MathUtils.clamp(PlayerSprite.getX(), 0, worldWidth - PlayerSprite.getWidth()));
        PlayerSprite.setY(MathUtils.clamp(PlayerSprite.getY(), 0, worldHeight - PlayerSprite.getHeight()));
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void addHealth(int health) {
        if (health+this.health >= maxHealth ) {
            this.health = maxHealth;
        } else {
            this.health += health;
        }
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void addMana(int mana) {
        if (mana+this.mana >= maxMana ) {
            this.mana = maxMana;
        } else {
            this.mana += mana;
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        PlayerSprite.draw(spriteBatch);
    }
}
