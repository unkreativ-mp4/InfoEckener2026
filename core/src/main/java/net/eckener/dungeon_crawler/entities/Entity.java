package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import net.eckener.dungeon_crawler.Main;

/**
 * The most basic form of an entity. Only exists so all entities can be registered in a single {@link EntityRegistry} and only one draw and update command is needed in {@link Main}
 */
public abstract class Entity {

    protected Sprite sprite;
    protected int speed;
    float[] vertices;
    Polygon hitbox;
    Vector2 momentum = new Vector2();

    public Entity(float xPos, float yPos, Texture texture, int speed) {
        this.speed = speed;
        sprite = new Sprite(texture);
        sprite.setSize(1,1);
        sprite.setX(xPos);
        sprite.setY(yPos);
        EntityRegistry.register(this);

        vertices = new float[] {
            0, 0,
            sprite.getWidth(), 0,
            sprite.getWidth(), sprite.getHeight(),
            0, sprite.getHeight()
        };
        hitbox = new Polygon(vertices);
    }

    /**
     * @return the x-Position of the {@link Sprite} and the entity
     */
    public float getxPos() {
        return sprite.getX();
    }

    /**
     * @param xPos sets the x-Position of the {@link Sprite} and the entity
     */
    public void setxPos(float xPos) {
        sprite.setX(xPos);
    }

    /**
     * @return the y-Position of the {@link Sprite} and the entity
     */
    public float getyPos() {
        return sprite.getY();
    }

    /**
     * @param yPos sets the x-Position of the {@link Sprite} and the entity
     */
    public void setyPos(float yPos) {
        sprite.setY(yPos);
    }

    /**
     * @return the {@link Sprite} of the entity
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * @param momentum sets the momentum vector of the Entity
     */
    public void setMomentum(Vector2 momentum) {
        this.momentum = momentum;
    }

    /**
     * @return the momentum vector of the Entity
     */
    public Vector2 getMomentum() {
        return momentum;
    }

    /**
     * @param momentum adds to the momentum vector of the Entity
     */
    public void addMomentum(Vector2 momentum) {
        this.momentum.add(momentum);
    }

    /**
     * The draw method, so the entity actually gets rendered
     * @param batch the {@link com.badlogic.gdx.graphics.g2d.SpriteBatch} in which to draw the {@link Sprite}
     */
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    /**
     * @return the {@link Texture} of the {@link Sprite}
     */
    public Texture getTexture() {
        return sprite.getTexture();
    }

    /**
     * Method which runs every frame
     * @param delta Frametime to satisfy smooth updating even when lagging
     */
    public abstract void update(float delta);

    /**
     * Method which runs every frame
     * @param delta Frametime to satisfy smooth updating even when lagging
     * @param player {@link Player} in case it is needed, e.g. for pathfinding
     */
    public abstract void update(float delta, Player player);


    /**
     * Updates the entities {@code hitbox} to match position and rotation
     */
    public void updateHitbox() {
        hitbox.setOrigin(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
        hitbox.setPosition(sprite.getX(), sprite.getY());
        hitbox.setRotation(sprite.getRotation());
    }

    /**
     * Reduces the momentum to simulate friction, limits the speed, and moves the Sprite/Entity
     */
    public void updateMovement(float deltaTime) {
        momentum.scl(0.95F);
        momentum.clamp(0,20);
        sprite.translate(momentum.scl(deltaTime).x, momentum.scl(deltaTime).y);
    }
}
