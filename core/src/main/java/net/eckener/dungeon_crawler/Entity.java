package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class Entity {

    protected Sprite sprite;

    public Entity(float xPos, float yPos, Texture texture) {
        sprite = new Sprite(texture);
        sprite.setSize(1,1);
        sprite.setX(xPos);
        sprite.setY(yPos);
    }

    public float getxPos() {
        return sprite.getX();
    }

    public void setxPos(float xPos) {
        sprite.setX(xPos);
    }

    public float getyPos() {
        return sprite.getY();
    }

    public void setyPos(float yPos) {
        sprite.setY(yPos);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public abstract void update(float delta);
}
