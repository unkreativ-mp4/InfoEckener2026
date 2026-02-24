package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * very basic Zombie {@link Enemy}
 */
public class Zombie extends Enemy {
    private static final int baseHealth = 20;
    private static final int baseDamage = 3;
    private static final int baseReach = 2;
    private final Texture deathTexture;



    public Zombie(float xPos, float yPos, Texture aliveTexture, Texture deathTexture) {
        super(xPos, yPos, baseHealth, aliveTexture,1);
        attackCooldown = 1.5;
        this.deathTexture = deathTexture;
    }

    /**
     * Moves the Zombie straight to the {@link Player}
     * @param player the {@link Player} which to target
     */
    public void move(Player player) {
        direction.set(player.getxPos() - getxPos(), player.getyPos() - getyPos());
        if (direction.len2() > 0f) {
            direction.nor().scl(speed - momentum.len());
            momentum.add(direction);
        }
    }

    /**
     * @param livingEntity Attacks a {@link LivingEntity} if it can
     */
    @Override
    public void attack(LivingEntity livingEntity) {
        if(canAttack()) {
            double distance = Math.sqrt(   Math.pow(sprite.getX()- livingEntity.getxPos(),2)   + Math.pow(sprite.getY()- livingEntity.getyPos(),2)    );
            if(distance <= baseReach) {
                livingEntity.takeDamage(baseDamage);
            }
            timeSinceLastAttack = 0;
        }
    }

    /**
     * @return whether the Zombie can attack ({@code attackCooldown} etc.)
     */
    private boolean canAttack() {
        return timeSinceLastAttack >= attackCooldown;
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
        if(isAlive) {
            timeSinceLastAttack += deltaTime;
            move(player);
            attack(player);
        }
        System.out.println(momentum);
    }

    /**
     * runs every frame; here not needed
     * @param delta Frametime to satisfy smooth updating even when lagging
     */
    @Override
    public void update(float delta) {}
}
