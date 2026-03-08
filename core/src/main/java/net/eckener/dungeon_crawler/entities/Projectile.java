package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Abstract Projectile class
 */
public abstract class Projectile extends Entity {

    protected float directionDeg;
    protected Entity owner;

    public Projectile(Texture texture,float xPos, float yPos, float speed, Entity owner) {
        super(xPos,yPos,texture,speed);
        this.owner = owner;
    }

    public abstract void move(float deltaTime);

    /**
     * @return the direction the Projectile is facing/heading in
     */
    public float getDirectionDeg() {
        return directionDeg;
    }

    /**
     * @return the Owner of the Projectile
     */
    public Entity getOwner() {
        return owner;
    }

    public abstract void update(float deltaTime);
}
