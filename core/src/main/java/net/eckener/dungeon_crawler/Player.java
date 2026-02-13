package net.eckener.dungeon_crawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Player {
    //private BackgroundChanger bgChanger;
    private RoomChanger roomChanger;
    private Texture upTexture, downTexture, leftTexture, rightTexture;
    public Sprite PlayerSprite;
    private float playerSpeed = 4;
    private int maxHealth;
    private int maxMana;
    private int health;
    private int mana;
    private float timeSinceLastDamage;
    private float damageCooldown = 0.5F;
    float x, y;
    private float worldWidth;


    private boolean transitioning;
    private FitViewport viewport;

    public Player(int maxHealth, int maxMana, FitViewport viewport, RoomChanger roomChanger) {
        this.upTexture = Assets.get(Assets.PLAYER_UP);
        this.downTexture = Assets.get(Assets.PLAYER_DOWN);
        this.leftTexture = Assets.get(Assets.PLAYER_LEFT);
        this.rightTexture = Assets.get(Assets.PLAYER_RIGHT);
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.viewport = viewport;
        this.roomChanger = roomChanger;
        //this.bgChanger = bgChanger;

        PlayerSprite = new Sprite(this.downTexture);
        PlayerSprite.setSize(1, 1);
    }
    public void setWorldWidth(float worldWidth) {
        this.worldWidth = worldWidth;
    }
    private void checkRoomExit() {

        float left = PlayerSprite.getX();
        float right = left + PlayerSprite.getWidth();

        if (right < 0) {
            roomChanger.changeRoom(Direction.LEFT);
        }

        if (left > worldWidth) {
            roomChanger.changeRoom(Direction.RIGHT);
        }
    }

    //private void changeBackground(){bgChanger.changeBackground();}


    /*public void handleScreenTransition(){
        if(!transitioning) {
            if (PlayerSprite.getX() + PlayerSprite.getX() + PlayerSprite.getWidth() < 0) {
                transitioning = true;
                //changeBackground();
                PlayerSprite.setX(currentRoom.width - PlayerSprite.getWidth());
                transitioning = false;
            }
            if (PlayerSprite.getX() > currentRoom.width) {
                transitioning = true;
                //changeBackground();
                PlayerSprite.setX(0);
                transitioning = false;
            }
        }
    }*/

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
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
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

    public void setX(float newX) { x = newX; }
    public void setY(float newY) { y = newY; }
    public float getWidth() {return PlayerSprite.getWidth();}

    public Sprite getPlayerSprite(){
        return PlayerSprite;
    }

    public void update(float deltaTime) {
        timeSinceLastDamage += deltaTime;
        checkRoomExit();
        //handleScreenTransition();
    }
}
