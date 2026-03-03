package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * {@link LivingEntity} but with the possibility to attack
 */
public abstract class Enemy extends LivingEntity {

    protected double attackCooldown;
    protected double timeSinceLastAttack = 0;

    public Enemy(float xPos, float yPos, int maxHealth, Texture aliveTexture, float speed) {
        super(xPos,yPos, aliveTexture,maxHealth, speed);
    }

    public abstract void update(float deltaTime, Player player);

    public abstract void attack(LivingEntity livingEntity);

}
