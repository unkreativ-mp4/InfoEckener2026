package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Expands on {@link Entity} with attributes like {@code health}
 */
public abstract class LivingEntity extends Entity {

    protected int health;
    protected int maxHealth;
    protected boolean isAlive = true;


    public LivingEntity(float xPos, float yPos, Texture texture, int maxHealth, float speed) {
        super(xPos, yPos, texture, speed);

        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    /**
     * @param amount heals the LivingEntity up to its {@code maxHealth}
     */
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    /**
     * Removes {@code health} from the LivingEntity until it reaches zero, then calls {@code onDeath()}
     * @param damage the amount of {@code health} getting removed
     */
    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
        if (health == 0) {
            onDeath();
        }
    }

    protected abstract void onDeath();

    /**
     * @return if the LivingEntity is alive and presumably doing stuff
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * @return the amount of {@code health} the LivingEntity has
     */
    public int getHealth() {
        return health;
    }

    /**
     * @return the amount of {@code maxHealth} the LivingEntity has
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * @return the {@code speed} of the LivingEntity
     */
    public float getSpeed() {
        return speed;
    }

}
