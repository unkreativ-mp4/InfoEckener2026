package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Projectile extends Entity {

    protected float direction;

    public Projectile(Texture texture,float xPos, float yPos) {
        super(xPos,yPos,texture);
    }

    public abstract void move(float deltaTime);

    public float getDirection() {
        return direction;
    }

    public abstract void update(float deltaTime);
}
