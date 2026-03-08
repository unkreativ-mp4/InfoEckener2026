package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.logic.Room;

/**
 * {@link LivingEntity} but with the possibility to attack
 */
public abstract class Enemy extends LivingEntity {

    protected double attackCooldown;
    protected double timeSinceLastAttack = 0;

    public Enemy(float xPos, float yPos, int maxHealth, Texture aliveTexture, float speed) {
        super(xPos,yPos, aliveTexture,maxHealth, speed);
    }

    public Enemy(float xPos, float yPos, int maxHealth, Texture aliveTexture, float speed, Room room) {
        super(xPos, yPos, aliveTexture, maxHealth, speed, room);
    }

        public abstract void update(float deltaTime, Player player);

    public abstract void attack(LivingEntity livingEntity);

}
