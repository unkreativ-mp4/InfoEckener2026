package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import net.eckener.dungeon_crawler.*;
import net.eckener.dungeon_crawler.logic.EntityRegistry;
import net.eckener.dungeon_crawler.logic.Room;
import net.eckener.dungeon_crawler.logic.Wall;
import net.eckener.dungeon_crawler.logic.WallRegistry;

import static net.eckener.dungeon_crawler.logic.RoomRegistry.getCurrentRoom;

/**
 * The most basic form of an entity. Only exists so all entities can be registered in a single {@link EntityRegistry} and only one draw and update command is needed in {@link Main}
 */
public abstract class Entity extends Sprite {

    protected float speed;
    private Polygon hitbox;
    protected Vector2 momentum = new Vector2();
    protected Vector2 direction = new Vector2();
    private Vector2 timescaledMomentum = new Vector2();
    private final Room room;

    public Entity(float xPos, float yPos, Texture texture, float speed) {
        super(texture);

        this.speed = speed;
        setSize(1,1);
        setX(xPos);
        setY(yPos);

        room = getCurrentRoom();
        EntityRegistry.register(this);

        float[] vertices = new float[] {
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
     * @param delta Frame time to satisfy smooth updating even when lagging
     */
    public abstract void update(float delta);

    /**
     * Method which runs every frame
     * @param delta Frame time to satisfy smooth updating even when lagging
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
     * Reduces the momentum to simulate friction, limits the speed, moves the Sprite/Entity and calls methods for collision handling
     */
    public void updateMovement(float deltaTime) {
        momentum.scl(0.95F);
        momentum.clamp(0,200);
        timescaledMomentum.set(momentum).scl(deltaTime);

        translate(timescaledMomentum.x, 0);
        updateHitbox();
        resolveCollisions();

        translate(0, timescaledMomentum.y);
        updateHitbox();
        resolveCollisions();

    }

    /**
     * Resolves collisions between this Entity and Walls/other Entities by using libGDX's MinimumTranslationVector feature to push this Entity out of the Wall or both Entities out of each other
     */
    private void resolveCollisions() {

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();

        //Walls
        for (Wall wall : WallRegistry.getAllRoomWalls()) {

            if (Intersector.overlapConvexPolygons(hitbox, wall.getHitbox(), mtv)) {

                translate(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);

                updateHitbox();

                float dot = momentum.dot(mtv.normal);
                if (dot < 0) {
                    momentum.sub(
                        mtv.normal.x * dot,
                        mtv.normal.y * dot
                    );
                }
            }
        }

        //Entities
        for (Entity entity : EntityRegistry.getAllEntities()) {

            if (entity == this) break;

            if (Intersector.overlapConvexPolygons(hitbox, entity.hitbox, mtv)) {

                float halfDepth = mtv.depth * 0.5f;

                translate(mtv.normal.x * halfDepth, mtv.normal.y * halfDepth);

                entity.translate(-mtv.normal.x * halfDepth, -mtv.normal.y * halfDepth);

                updateHitbox();
                entity.updateHitbox();

                float dotA = momentum.dot(mtv.normal);
                if (dotA < 0)
                    momentum.sub(mtv.normal.x * dotA,
                        mtv.normal.y * dotA);
            }
        }
    }
}
