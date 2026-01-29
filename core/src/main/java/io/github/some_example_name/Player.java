package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Player {
    private BackgroundChanger bgChanger;
    Texture upTexture, downTexture, leftTexture, rightTexture;
    private Sprite PlayerSprite;
    private float playerSpeed;
    private final int maxHealth;
    private final int maxMana;
    private int health;
    private int mana;
    private boolean transitioning;
    private FitViewport viewport;

    public Player(Texture upTexture,Texture downTexture, Texture leftTexture, Texture rightTexture, int maxHealth, int maxMana, FitViewport viewport, BackgroundChanger bgChanger) {
        this.upTexture = upTexture;
        this.downTexture = downTexture;
        this.leftTexture = leftTexture;
        this.rightTexture = rightTexture;
        PlayerSprite = new Sprite(this.downTexture);
        PlayerSprite.setSize(1, 1);
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.viewport = viewport;
        this.bgChanger = bgChanger;

    }
    private void changeBackground(){
        bgChanger.changeBackground();
    }
    public Sprite getPlayerSprite(){
        return PlayerSprite;
    }
    public void update(float amount){
        handleScreenTransition();
    }
    public void handleScreenTransition(){
        if(!transitioning) {
            if (PlayerSprite.getX() + PlayerSprite.getX() + PlayerSprite.getWidth() < 0) {
                transitioning = true;
                changeBackground();
                PlayerSprite.setX(viewport.getWorldWidth() - PlayerSprite.getWidth());
                transitioning = false;
            }
            if (PlayerSprite.getX() > viewport.getWorldWidth()) {
                transitioning = true;
                changeBackground();
                PlayerSprite.setX(0);
                transitioning = false;
            }
        }
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

    public void dontGoPastScreen(float worldHeight) {
        //PlayerSprite.setX(MathUtils.clamp(PlayerSprite.getX(), 0, worldWidth - PlayerSprite.getWidth()));
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
