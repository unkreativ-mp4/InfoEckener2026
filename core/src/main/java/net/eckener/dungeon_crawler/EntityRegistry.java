package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * An entity registry so {@link Main} has its peace
 */
public final class EntityRegistry {

    private static final Array<Entity> entities = new Array<>();

    private EntityRegistry() {}

    /**Enters an {@link Entity} into the registry
     * @param entity the {@link Entity} which to register
     */
    public static void register(Entity entity) {
        entities.add(entity);
    }

    /**Removes an {@link Entity} from the registry
     * @param entity the {@link Entity} which to remove
     */
    public static void unregister(Entity entity) {
        entities.removeValue(entity, true);
    }

    /**
     * Calls the update method for all {@link Entity}s so they can move and shit
     * @param delta Frametime for smooth updating
     * @param player some {@link Entity}s need it for pathfinding
     */
    public static void updateAll(float delta, Player player) {
        for (Entity entity : entities) {
            if(entity instanceof Zombie) {
                entity.update(delta, player);
            } else {
                entity.update(delta);
            }
        }
    }

    /**
     * Renders all registered {@link Entity}s
     * @param batch the {@link SpriteBatch} in which to draw/render the {@link Entity}s
     */
    public static void renderAll(SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.draw(batch);
        }
    }

    /**
     * Empties the registry
     */
    public static void clear() {
        entities.clear();
    }
}
