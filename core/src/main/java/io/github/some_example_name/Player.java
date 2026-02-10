package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Player {
    private BackgroundChanger bgChanger;
    private Texture upTexture, downTexture, leftTexture, rightTexture;
    private Sprite PlayerSprite;
    private float playerSpeed;
    private int maxHealth;
    private int maxMana;
    private int health;
    private int mana;
    private float timeSinceLastDamage;
    private float damageCooldown = 0.5F;

    private boolean transitioning;
    private FitViewport viewport;

    public Player(int maxHealth, int maxMana, FitViewport viewport, BackgroundChanger bgChanger) {
        this.upTexture = Assets.get(Assets.PLAYER_UP);
        this.downTexture = Assets.get(Assets.PLAYER_DOWN);;
        this.leftTexture = Assets.get(Assets.PLAYER_LEFT);;
        this.rightTexture = Assets.get(Assets.PLAYER_RIGHT);;
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.viewport = viewport;
        this.bgChanger = bgChanger;

        PlayerSprite = new Sprite(this.downTexture);
        PlayerSprite.setSize(1, 1);
    }

    private void changeBackground(){
        bgChanger.changeBackground();
    }

    public Sprite getPlayerSprite(){
        return PlayerSprite;
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

    public void move(float delta) {
        delta *= 4;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            PlayerSprite.setTexture(upTexture);
            PlayerSprite.translateY(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            PlayerSprite.setTexture(leftTexture);
            PlayerSprite.translateX(-delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            PlayerSprite.setTexture(downTexture);
            PlayerSprite.translateY(-delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            PlayerSprite.setTexture(rightTexture);
            PlayerSprite.translateX(delta);
        }
    }

    public void dontGoPastScreen(float worldHeight) {
        //PlayerSprite.setX(MathUtils.clamp(PlayerSprite.getX(), 0, worldWidth - PlayerSprite.getWidth()));
        PlayerSprite.setY(MathUtils.clamp(PlayerSprite.getY(), 0, worldHeight - PlayerSprite.getHeight()));
    }


    public void addHealth(int health) {
        if(health >= 0) {
            this.health = Math.min(maxHealth, this.health += health);
        } else {
            if(timeSinceLastDamage < damageCooldown) {return;}
            this.health = Math.max(0, this.health += health);
            timeSinceLastDamage = 0;
        }
    }

    public void addMana(int mana) {
        if(mana >= 0) {
            this.mana = Math.min(maxMana, this.mana += mana);
        } else {
            this.mana = Math.max(0, this.mana += mana);
        }
    }

    public void attack(Enemy enemy, int damage) {
        enemy.takeDamage(damage);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
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

    public void update(float deltaTime) {
        timeSinceLastDamage += deltaTime;
        handleScreenTransition();
    }
}
