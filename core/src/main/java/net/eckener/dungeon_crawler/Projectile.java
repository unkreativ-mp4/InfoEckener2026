package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Projectile {

    protected final Sprite sprite;
    protected float direction;
    protected float xPos;
    protected float yPos;

    public Projectile(Texture texture,float xPos, float yPos) {
        sprite = new Sprite(texture);
        sprite.setSize(1,1);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void draw(SpriteBatch spriteBatch) {
        sprite.draw(spriteBatch);
    }

    public float getXPos() {
        return xPos;
    }

    public float getYPos() {
        return yPos;
    }

    public abstract void move(float deltaTime);

    public float getDirection() {
        return direction;
    }

    public abstract void update(float deltaTime);
}
