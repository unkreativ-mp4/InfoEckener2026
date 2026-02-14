package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * very basic Zombie {@link Enemy}
 */
public class Zombie extends Enemy {
    private static final int baseHealth = 20;
    private static final int baseDamage = 3;
    private static final int baseReach = 2;
    private final Texture deathTexture;
    private final Vector2 direction = new Vector2();

    private double attackCooldown = 1.5;
    private double timeSinceLastAttack;

    public Zombie(float xPos, float yPos, Texture aliveTexture, Texture deathTexture) {
        super(xPos, yPos, baseHealth, aliveTexture,1);
        this.timeSinceLastAttack = 0;
        this.deathTexture = deathTexture;
    }

    /**
     * Moves the Zombie straight to the {@link Player}
     * @param player the {@link Player} which to target
     * @param delta Frametime for smooth movement even when lagging
     */
    public void move(Player player, float delta) {
        if(isAlive) {
            direction.set(player.getxPos() - getxPos(), player.getyPos() - getyPos());

            if (direction.len2() > 0f) {
                direction.nor().scl(speed * delta);
                sprite.translate(direction.x, direction.y);
            }
        }
    }

    /**
     * @param player Attacks a {@link Player} if it can
     */
    @Override
    public void attack(Player player) {
        if(canAttack()) {
            double distance = Math.sqrt(   Math.pow(sprite.getX()- player.getxPos(),2)   + Math.pow(sprite.getY()- player.getyPos(),2)    );
            if(distance <= baseReach) {
                player.takeDamage(baseDamage);
            }
            timeSinceLastAttack = 0;
        }

    }

    /**
     * @param enemy Attacks another {@link Enemy} if it can
     */
    @Override
    public void attack(Enemy enemy) {
        if(canAttack()) {
            double distance = Math.sqrt(   Math.pow(this.sprite.getX()- enemy.getxPos(),2)   + Math.pow(this.sprite.getY()- enemy.getyPos(),2)    );
            if(distance <= baseReach) {
                enemy.takeDamage(baseDamage);
            }
            timeSinceLastAttack = 0;
        }
    }

    /**
     * @return whether the Zombie can attack ({@code attackCooldown} etc.)
     */
    private boolean canAttack() {
        return timeSinceLastAttack >= attackCooldown && isAlive();
    }

    /**
     * changes the {@link Texture} and the {@code isAlive} attribute
     */
    @Override
    protected void onDeath() {
        isAlive=false;
        sprite.setTexture(deathTexture);
    }

    /**
     * Updates the Zombie every frame
     * @param deltaTime Frametime to satisfy smooth updating even when lagging
     * @param player {@link Player} in case it is needed, e.g. for pathfinding
     */
    @Override
    public void update(float deltaTime, Player player) {
        timeSinceLastAttack += deltaTime;
        move(player, deltaTime);
        attack(player);
    }

    /**
     * runs every frame; here not needed
     * @param delta Frametime to satisfy smooth updating even when lagging
     */
    @Override
    public void update(float delta) {}
}
