package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Abstract Projectile class
 */
public abstract class Projectile extends Entity {

    protected float directionDeg;

    public Projectile(Texture texture,float xPos, float yPos, float speed) {
        super(xPos,yPos,texture,speed);
    }

    public abstract void move(float deltaTime);

    /**
     * @return the direction the Projectile is facing/heading in
     */
    public float getDirectionDeg() {
        return directionDeg;
    }

    public abstract void update(float deltaTime);
}
