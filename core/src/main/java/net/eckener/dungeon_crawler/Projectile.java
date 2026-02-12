package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Projectile {

    private final Texture texture;
    private Vector2 direction;
    private float xPos;
    private float yPos;

    public Projectile(Texture texture,float xPos, float yPos, Vector2 direction ) {
        this.texture = texture;
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
    }

    public Texture getTexture() {
        return texture;
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(texture, xPos, yPos);
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public abstract void move();

    public Vector2 getDirection() {
        return direction;
    }
}
