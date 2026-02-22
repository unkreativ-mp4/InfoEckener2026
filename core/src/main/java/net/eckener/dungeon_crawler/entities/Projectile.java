package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Abstract Projectile class
 */
public abstract class Projectile extends Entity {

    protected float direction;

    public Projectile(Texture texture,float xPos, float yPos, int speed) {
        super(xPos,yPos,texture,speed);
    }

    public abstract void move(float deltaTime);

    /**
     * @return the direction the Projectile is facing/heading in
     */
    public float getDirection() {
        return direction;
    }

    public abstract void update(float deltaTime);
}
