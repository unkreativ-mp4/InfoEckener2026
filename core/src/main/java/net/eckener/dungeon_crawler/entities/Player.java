package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import net.eckener.dungeon_crawler.*;
import net.eckener.dungeon_crawler.items.Bow;
import net.eckener.dungeon_crawler.items.ItemStack;
import net.eckener.dungeon_crawler.items.Weapon;

public class Player extends LivingEntity{
    private BackgroundChanger bgChanger;
    private int maxMana;
    private int mana;
    private int maxHealth;
    private int health;
    private float timeSinceLastDamage;
    private float timeSinceLastAttack;
    private final float baseDamageCooldown = 0.5F;
    private final float baseAttackCooldown = 1.0F;
    private ItemStack selectedItem; //soll später durch einen slot-Index ausgetauscht werden das kann aber erst mit funktionierendem Inventar geschehen

    private boolean transitioning;
    private final FitViewport viewport;

    public Player(int maxHealth, int maxMana, FitViewport viewport, BackgroundChanger bgChanger) {
        super(1,1, Assets.get(Assets.PLAYER_DOWN), maxHealth,2);
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.viewport = viewport;
        this.bgChanger = bgChanger;

        Bow bow = new Bow("bow","toller Bogen", Assets.get(Assets.COIN),1,1,10,2);
        selectedItem = new ItemStack(bow,1);

    }

    /**
     * changes the game background
     */
    private void changeBackground(){
        bgChanger.changeBackground();
    }

    /**
     * handles the transition from one background to another, including player movement
     */
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

    /**
     * Player movement depending on user input
     * @param delta Frametime for smooth movement even when lagging
     */
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

    /**
     * prevents the player from walking out of the screen
     * @param worldHeight the height of the world/background
     */
    public void dontGoPastScreen(float worldHeight) {
        //PlayerSprite.setX(MathUtils.clamp(PlayerSprite.getX(), 0, worldWidth - PlayerSprite.getWidth()));
        sprite.setY(MathUtils.clamp(sprite.getY(), 0, worldHeight - sprite.getHeight()));
    }

    /**Hurts the Player but respects I-frames
     * @param damage the amount of {@code health} getting removed
     */
    @Override
    public void takeDamage(int damage) {
        if(timeSinceLastDamage < baseDamageCooldown) {return;}
        health = Math.max(0, health - damage);
        timeSinceLastDamage = 0;
    }

    /**
     * waiting for implementation
     */
    @Override
    protected void onDeath() {
    }

    /**
     * Changes the {@code mana} the player has. Also works with negative amounts
     * @param mana amount of {@code mana} to add/subtract
     */
    public void addMana(int mana) {
        if(mana >= 0) {
            this.mana = Math.min(maxMana, this.mana += mana);
        } else {
            this.mana = Math.max(0, this.mana += mana);
        }
    }
    public void addHealth(int health) {
        if (health >= 0){
            this.health = Math.min(maxHealth, this.health += health);
        } else {
            this.health = Math.max(0, this.health += health);
        }
    }

    /**
     * @return how much mana the Player has left in percent
     */
    public float getManaPercent() {
        return (float) mana / maxMana;
    }
    public float getHealthPercent() {
        return (float) health / maxHealth;
    }

    /**
     * Attacks an {@link Enemy} with the selected {@link Weapon}
     * @param enemy the {@link Enemy} which to attack
     */
    public void attack(Enemy enemy) {
        if(timeSinceLastAttack > baseAttackCooldown && selectedItem.isWeapon()) {
            selectedItem.getWeapon().attack(this, enemy,viewport);
            timeSinceLastAttack = 0;

        }
    }

    /**
     * @param mana sets the amount of {@code mana} the Player has
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * @return the amount of {@code mana} the Player has
     */
    public int getMana() {
        return mana;
    }
    public int getHealth() {
        return health;
    }

    /**
     * Runs every frame and increases {@code timeSince} attributes among other things
     * @param deltaTime Frametime to satisfy smooth updating even when lagging
     */
    @Override
    public void update(float deltaTime) {
        timeSinceLastDamage += deltaTime;
        timeSinceLastAttack += deltaTime;
        move(deltaTime);
        handleScreenTransition();
    }

    /**
     * Never use, because it makes no sense
     * @param delta
     * @param player
     */
    @Override
    public void update(float delta, Player player) {}
}
