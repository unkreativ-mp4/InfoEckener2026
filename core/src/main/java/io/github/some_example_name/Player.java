package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Player {
    Texture upTexture, downTexture, leftTexture, rightTexture;
    Sprite PlayerSprite;
    private float playerSpeed;
    private int maxHealth;
    private int maxMana;
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
    public void moveLeft(float amount){
        PlayerSprite.setTexture(leftTexture);
        PlayerSprite.translateX(-amount);
    }
    public void moveRight(float amount){
        PlayerSprite.setTexture(rightTexture);
        PlayerSprite.translateX(amount);
    }
    public void moveUp(float amount){
        PlayerSprite.setTexture(upTexture);
        PlayerSprite.translateY(amount);
    }
    public void moveDown(float amount){
        PlayerSprite.setTexture(downTexture);
        PlayerSprite.translateY(-amount);
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
        if(health >= 0) {
            this.health = Math.min(maxHealth, this.health += health);
        } else {
            this.health = Math.max(0, this.health += health);
        }
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    public void addMana(int mana) {
        if(mana >= 0) {
            this.mana = Math.min(maxMana, this.mana += mana);
        } else {
            this.mana = Math.max(0, this.mana += mana);
        }
    }

    public float getX() {
        return PlayerSprite.getX();
    }

    public float getY() {
        return PlayerSprite.getY();
    }

    public void draw(SpriteBatch spriteBatch) {
        PlayerSprite.draw(spriteBatch);
    }
}
