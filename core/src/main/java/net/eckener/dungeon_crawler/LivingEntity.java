package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;

public abstract class LivingEntity extends Entity {

    protected int health;
    protected int maxHealth;
    protected int speed;
    protected boolean isAlive = true;


    public LivingEntity(float xPos, float yPos, Texture texture, int maxHealth, int speed) {
        super(xPos, yPos, texture);

        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.speed = speed;
    }

    protected void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
        if (health == 0) {
            onDeath();
        }
    }

    protected abstract void onDeath();

    public boolean isAlive() {
        return isAlive;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getSpeed() {
        return speed;
    }

}
