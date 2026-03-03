package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import net.eckener.dungeon_crawler.Assets;

public class Skeleton extends Enemy {

    private static final int baseHealth = 20;
    private static final int baseDamage = 3;

    private final Vector2 direction = new Vector2();

    public Skeleton(float xPos, float yPos, Texture aliveTexture) {
        super(xPos, yPos, baseHealth, aliveTexture, 1);
        attackCooldown = 3;
    }

    /**
     * Moves the Skeleton to the {@link Player}, but keeps a certain distance
     * @param player the {@link Player} which to target
     */
    public void move(Player player) {
        direction.set(player.getX() - getX(), player.getY() - getY());
        if (direction.len2() > 2f) {
            direction.nor().scl(speed - MathUtils.clamp(momentum.len(),0, speed ));
            momentum.add(direction);
        } else if (direction.len2() > 0f) {
            direction.nor().scl(speed - MathUtils.clamp(momentum.len(),0, speed ));
            momentum.add(direction.scl(-1));
        }
    }

    /**
     * runs every frame; here not needed
     * @param delta Frametime to satisfy smooth updating even when lagging
     */
    @Override
    public void update(float delta) {    }

    /**
     * Updates the Skeleton every frame
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
    }

    /**
     * @param livingEntity Attacks a {@link LivingEntity} if it can
     */
    @Override
    public void attack(LivingEntity livingEntity) {
        if(canAttack()) {
            Arrow arrow = new Arrow(Assets.get(Assets.ARROW), getX(), getY(), this);
            arrow.setRotationToFaceLivingEntity(livingEntity);
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
     * changes the {@code isAlive} attribute
     */
    @Override
    protected void onDeath() {
        isAlive=false;
        //sprite.setTexture(deathTexture);
    }
}
