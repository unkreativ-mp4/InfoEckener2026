package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Arrow extends Projectile{
    Vector2 directionVector = new Vector2();
    public Arrow(Texture texture, float xPos, float yPos, float direction) {
        super(texture, xPos, yPos, direction);
        sprite.setOriginCenter();
        sprite.setRotation(direction);
        sprite.setX(xPos);
        sprite.setY(yPos);
    }

    @Override
    public void move(float deltaTime) {
        directionVector.setAngleDeg(direction);
        sprite.translate(directionVector.nor().scl(deltaTime).x, directionVector.nor().scl(deltaTime).y);
        System.out.println(directionVector.nor().x);
    }

    @Override
    public void update(float deltaTime) {
        move(deltaTime);
    }
}
