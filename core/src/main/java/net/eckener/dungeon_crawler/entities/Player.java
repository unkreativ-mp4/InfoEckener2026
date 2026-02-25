package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import net.eckener.dungeon_crawler.*;
import net.eckener.dungeon_crawler.items.Bow;
import net.eckener.dungeon_crawler.items.ItemStack;
import net.eckener.dungeon_crawler.items.Maul;
import net.eckener.dungeon_crawler.items.Weapon;

public class Player extends LivingEntity{
    private RoomChanger roomChanger;
    private int maxMana;
    private int mana;
    float x, y;
    private float timeSinceLastDamage;
    private float timeSinceLastAttack;
    private final float baseDamageCooldown = 0.5F;
    private final float baseAttackCooldown = 1.0F;
    private ItemStack selectedItem; //soll später durch einen slot-Index ausgetauscht werden das kann aber erst mit funktionierendem Inventar geschehen

    private final FitViewport viewport;
    private float worldWidth;

    public Player(int maxHealth, int maxMana, FitViewport viewport, RoomChanger roomChanger, float x, float y) {
        super(1,1, Assets.get(Assets.PLAYER_DOWN), maxHealth,2);
        this.maxMana = maxMana;
        this.viewport = viewport;
        this.roomChanger = roomChanger;
        this.x = x;
        this.y = y;

        Bow bow = new Bow("bow","toller Bogen", Assets.get(Assets.COIN),1,1,10,2);
        Maul maul = new Maul(Assets.get(Assets.IRON_SHOVEL));
        selectedItem = new ItemStack(maul,1);

    }


    /**
     * Player movement depending on user input
     */
    public void move() {
        boolean matched = false;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            sprite.setTexture(Assets.get(Assets.PLAYER_UP));
            direction.add(0,1);
            matched = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.setTexture(Assets.get(Assets.PLAYER_LEFT));
            direction.add(-1,0);
            matched = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            sprite.setTexture(Assets.get(Assets.PLAYER_DOWN));
            direction.add(0,-1);
            matched = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.setTexture(Assets.get(Assets.PLAYER_RIGHT));
            direction.add(1,0);
            matched = true;
        }
        if (matched) {
            direction.nor().scl(speed - momentum.len());
            momentum.add(direction);
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
    protected void onDeath() {}

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

    /**
     * @return how much mana the Player has left in percent
     */
    public float getManaPercent() {
        return (float) mana / maxMana;
    }

    /**
     * @return how much health the Player has left in percent
     */
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
    public void setX(float newX) {
        sprite.setX(newX);
    }
    public float getX(){return sprite.getX();}
    public float getWidth(){return sprite.getWidth();}
    public void setWorldWidth(float worldWidth) {
        this.worldWidth = worldWidth;
    }
    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Runs every frame and increases {@code timeSince} attributes among other things
     * @param deltaTime Frametime to satisfy smooth updating even when lagging
     */
    @Override
    public void update(float deltaTime) {
        timeSinceLastDamage += deltaTime;
        timeSinceLastAttack += deltaTime;
        move();
    }

    /**
     * Never use, because it makes no sense
     * @param delta Frametime stuff
     * @param player could honestly just be {@code this}
     */
    @Override
    public void update(float delta, Player player) {}
}

