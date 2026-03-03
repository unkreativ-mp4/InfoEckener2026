package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import net.eckener.dungeon_crawler.Main;
import net.eckener.dungeon_crawler.Room;

import static net.eckener.dungeon_crawler.RoomRegistry.getCurrentRoom;

/**
 * The most basic form of an entity. Only exists so all entities can be registered in a single {@link EntityRegistry} and only one draw and update command is needed in {@link Main}
 */
public abstract class Entity extends Sprite {

    protected float speed;
    private float[] vertices;
    private Polygon hitbox;
    protected Vector2 momentum = new Vector2();
    protected Vector2 direction = new Vector2();
    private Vector2 timescaledMomentum = new Vector2();
    private final Room room;

    public Entity(float xPos, float yPos, Texture texture, float speed) {
        super(texture);

        this.speed = speed;
        this.setSize(1,1);
        this.setX(xPos);
        this.setY(yPos);

        room = getCurrentRoom();
        EntityRegistry.register(this);

        vertices = new float[] {
            0, 0,
            getWidth(), 0,
            getWidth(), getHeight(),
            0, getHeight()
        };
        hitbox = new Polygon(vertices);
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
     * Adds to the momentum of the Entity
     * @param x the additional x momentum
     * @param y the additional y momentum
     */
    public void addMomentum(float x, float y) {
        momentum.add(x,y);
    }

    /**
     * @return the Hitbox Polygon of the Entity
     */
    public Polygon getHitbox() {
        return hitbox;
    }

    /**
     * @return the Room of the Entity
     */
    public Room getRoom() {
        return room;
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
        hitbox.setOrigin(getWidth() / 2f, getHeight() / 2f);
        hitbox.setPosition(getX(), getY());
        hitbox.setRotation(getRotation());
    }

    /**
     * Reduces the momentum to simulate friction, limits the speed, and moves the Sprite/Entity
     */
    public void updateMovement(float deltaTime) {
        momentum.scl(0.95F);
        momentum.clamp(0,200);
        timescaledMomentum.set(momentum).scl(deltaTime);
        translate(timescaledMomentum.x, timescaledMomentum.y);
    }
}
