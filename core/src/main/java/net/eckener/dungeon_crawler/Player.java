package net.eckener.dungeon_crawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Player {
    private BackgroundChanger bgChanger;
    private Texture upTexture, downTexture, leftTexture, rightTexture;
    private Sprite PlayerSprite;
    private float playerSpeed = 4;
    private int maxHealth;
    private int maxMana;
    private int health;
    private int mana;
    private float timeSinceLastDamage;
    private float timeSinceLastAttack;
    private final float baseDamageCooldown = 0.5F;
    private final float baseAttackCooldown = 1.0F;
    private ItemStack selectedItem; //soll später durch einen slot-Index ausgetauscht werden das kann aber erst mit funktionierendem Inventar geschehen

    private boolean transitioning;
    private FitViewport viewport;

    public Player(int maxHealth, int maxMana, FitViewport viewport, BackgroundChanger bgChanger) {
        this.upTexture = Assets.get(Assets.PLAYER_UP);
        this.downTexture = Assets.get(Assets.PLAYER_DOWN);
        this.leftTexture = Assets.get(Assets.PLAYER_LEFT);
        this.rightTexture = Assets.get(Assets.PLAYER_RIGHT);
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.viewport = viewport;
        this.bgChanger = bgChanger;

        PlayerSprite = new Sprite(this.downTexture);
        PlayerSprite.setSize(1, 1);


        Weapon weapon = new Weapon("schwert", "Ultra Krasses Testschwert", Assets.get(Assets.DIAMOND_SWORD),1,1,11,2.0F);
        selectedItem = new ItemStack(weapon,1);
    }

    private void changeBackground(){
        bgChanger.changeBackground();
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
        delta *= playerSpeed;
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
        dontGoPastScreen(viewport.getWorldHeight());
    }

    public void dontGoPastScreen(float worldHeight) {
        //PlayerSprite.setX(MathUtils.clamp(PlayerSprite.getX(), 0, worldWidth - PlayerSprite.getWidth()));
        PlayerSprite.setY(MathUtils.clamp(PlayerSprite.getY(), 0, worldHeight - PlayerSprite.getHeight()));
    }


    public void addHealth(int health) {
        if(health >= 0) {
            this.health = Math.min(maxHealth, this.health += health);
        } else {
            if(timeSinceLastDamage < baseDamageCooldown) {return;}
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

    public float getManaPercent() {
        return (float) mana / maxMana;
    }

    public void attack(Enemy enemy) {
        if(timeSinceLastAttack <= baseAttackCooldown && selectedItem.isWeapon()) {
            enemy.takeDamage(selectedItem.getWeapon().getDamage());
            timeSinceLastAttack = 0;
        }
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

    public Sprite getPlayerSprite(){
        return PlayerSprite;
    }

    public void update(float deltaTime) {
        timeSinceLastDamage += deltaTime;
        timeSinceLastAttack += deltaTime;
        handleScreenTransition();
    }
}
