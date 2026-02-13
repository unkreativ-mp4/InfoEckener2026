package net.eckener.dungeon_crawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Arrow extends Projectile{
    Vector2 directionVector = new Vector2();
    public Arrow(Texture texture, float xPos, float yPos, OrthographicCamera camera) {
        super(texture, xPos, yPos);

        Vector3 vector3 = new Vector3();
        camera.unproject(vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        float cx = sprite.getX() + sprite.getWidth() * 0.5f;
        float cy = sprite.getY() + sprite.getHeight() * 0.5f;

        float dx = vector3.x - cx;
        float dy = vector3.y - cy;

        direction = (float) Math.toDegrees(Math.atan2(dy, dx));
        sprite.setOriginCenter();
        sprite.setRotation(direction);
    }

    @Override
    public void move(float deltaTime) {
        directionVector.set(1,1);
        directionVector.setAngleDeg(direction);
        sprite.translate(directionVector.nor().scl(deltaTime).x, directionVector.nor().scl(deltaTime).y);
    }

    @Override
    public void update(float deltaTime) {
        move(deltaTime);
    }

    @Override
    public void update(float delta, Player player) {    }
}
