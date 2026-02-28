package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import net.eckener.dungeon_crawler.*;
import net.eckener.dungeon_crawler.items.Bow;
import net.eckener.dungeon_crawler.items.ItemStack;
import net.eckener.dungeon_crawler.items.Maul;
import net.eckener.dungeon_crawler.items.Weapon;

public class Player extends LivingEntity{
    private int maxMana;
    private int mana;
    private float timeSinceLastDamage;
    private float timeSinceLastAttack;
    private final float baseDamageCooldown = 0.5F;
    private final float baseAttackCooldown = 1.0F;
    private ItemStack selectedItem; //soll später durch einen slot-Index ausgetauscht werden das kann aber erst mit funktionierendem Inventar geschehen



    public Player(int maxHealth, int maxMana) {
        super(1,1, Assets.get(Assets.PLAYER_DOWN), maxHealth,2);
        this.maxMana = maxMana;

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
            setTexture(Assets.get(Assets.PLAYER_UP));
            direction.add(0,1);
            matched = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            setTexture(Assets.get(Assets.PLAYER_LEFT));
            direction.add(-1,0);
            matched = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            setTexture(Assets.get(Assets.PLAYER_DOWN));
            direction.add(0,-1);
            matched = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            setTexture(Assets.get(Assets.PLAYER_RIGHT));
            direction.add(1,0);
            matched = true;
        }
        if (matched) {
            direction.nor().scl(speed - momentum.len());
            momentum.add(direction);
            direction.setZero();
        }
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
            selectedItem.getWeapon().attack(this, enemy);
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

