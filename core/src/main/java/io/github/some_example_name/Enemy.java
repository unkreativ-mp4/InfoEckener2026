package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Enemy {
    protected int health;
    protected int maxHealth;
    protected int speed;

    protected int xPos;
    protected int yPos;
    protected Sprite EnemySprite;

    public Enemy(int xPos, int yPos, int maxHealth, Texture texture) {
        this.xPos = xPos;
        this.yPos = yPos;

        this.maxHealth = maxHealth;
        this.health = maxHealth;

        EnemySprite = new Sprite(texture);
        EnemySprite.setSize(1, 1);
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
        if (health == 0) {
            onDeath();
        }
    }

    protected void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    protected abstract void onDeath();

    public abstract void update();

    public abstract void attack(Player player);
    public abstract void attack(Enemy enemy);

}
