package net.eckener.dungeon_crawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Player extends LivingEntity{
    private BackgroundChanger bgChanger;
    private int maxMana;
    private int mana;
    private float timeSinceLastDamage;
    private float timeSinceLastAttack;
    private final float baseDamageCooldown = 0.5F;
    private final float baseAttackCooldown = 1.0F;
    private ItemStack selectedItem; //soll später durch einen slot-Index ausgetauscht werden das kann aber erst mit funktionierendem Inventar geschehen

    private boolean transitioning;
    private final FitViewport viewport;

    public Player(int maxHealth, int maxMana, FitViewport viewport, BackgroundChanger bgChanger) {
        super(1,1,Assets.get(Assets.PLAYER_DOWN), maxHealth,4);
        this.maxMana = maxMana;
        this.viewport = viewport;
        this.bgChanger = bgChanger;

        Bow bow = new Bow("bow","toller Bogen", Assets.get(Assets.COIN),1,1,10,2);
        selectedItem = new ItemStack(bow,1);

    }

    private void changeBackground(){
        bgChanger.changeBackground();
    }

    public void handleScreenTransition(){
        if(!transitioning) {
            if (getxPos() + getxPos() + sprite.getWidth() < 0) {
                transitioning = true;
                changeBackground();
                sprite.setX(viewport.getWorldWidth() - sprite.getWidth());
                transitioning = false;
            }
            if (getxPos() > viewport.getWorldWidth()) {
                transitioning = true;
                changeBackground();
                setxPos(0);
                transitioning = false;
            }
        }
    }

    public void move(float delta) {
        delta *= speed;

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            sprite.setTexture(Assets.get(Assets.PLAYER_UP));
            sprite.translateY(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.setTexture(Assets.get(Assets.PLAYER_LEFT));
            sprite.translateX(-delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            sprite.setTexture(Assets.get(Assets.PLAYER_DOWN));
            sprite.translateY(-delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.setTexture(Assets.get(Assets.PLAYER_RIGHT));
            sprite.translateX(delta);
        }
        dontGoPastScreen(viewport.getWorldHeight());
    }

    public void dontGoPastScreen(float worldHeight) {
        //PlayerSprite.setX(MathUtils.clamp(PlayerSprite.getX(), 0, worldWidth - PlayerSprite.getWidth()));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - sprite.getHeight()));
    }

    @Override
    public void takeDamage(int damage) {
        if(timeSinceLastDamage < baseDamageCooldown) {return;}
        health = Math.max(0, health - damage);
        timeSinceLastDamage = 0;
    }

    @Override
    protected void onDeath() {
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
        if(timeSinceLastAttack > baseAttackCooldown && selectedItem.isWeapon()) {
            selectedItem.getWeapon().attack(this, enemy,viewport);
            timeSinceLastAttack = 0;

        }
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMana() {
        return mana;
    }

    @Override
    public void update(float deltaTime) {
        timeSinceLastDamage += deltaTime;
        timeSinceLastAttack += deltaTime;
        handleScreenTransition();
    }
}
