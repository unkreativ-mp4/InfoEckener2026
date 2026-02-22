package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import net.eckener.dungeon_crawler.Main;

/**
 * An entity registry so {@link Main} has its peace
 */
public final class EntityRegistry {

    private static final Array<Entity> entities = new Array<>();
    private static final Array<LivingEntity> livingEntities = new Array<>();

    private EntityRegistry() {}

    /**Enters an {@link Entity} into the registry and if it is a {@link LivingEntity} into the specialized registry
     * @param entity the {@link Entity} which to register
     */
    public static void register(Entity entity) {
        entities.add(entity);
        if(entity instanceof LivingEntity){
            livingEntities.add((LivingEntity) entity);
        }
    }

    /**Removes an {@link Entity} from the registry and if it is a {@link LivingEntity} from the specialized registry
     * @param entity the {@link Entity} which to remove
     */
    public static void unregister(Entity entity) {
        entities.removeValue(entity, true);
        if(entity instanceof LivingEntity){
            livingEntities.removeValue((LivingEntity) entity, true);
        }
    }

    /**
     * Calls the update method for all {@link Entity}s so they can move and shit
     * @param delta Frametime for smooth updating
     * @param player some {@link Entity}s need it for pathfinding
     */
    public static void updateAll(float delta, Player player) {
        for (Entity entity : entities) {
            if(entity instanceof Enemy) {
                entity.update(delta, player);
            } else {
                entity.update(delta);
            }

            entity.updateHitbox();
        }
    }

    public static void updateMovementAll(float deltaTime) {
        for (Entity entity : entities) {
            if(entity instanceof Zombie){
                entity.updateMovement(deltaTime);
            }
            //entity.updateMovement();
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
     * @return all {@link Entity}s
     */
    public static Array<Entity> getAllEntities() {
        return entities;
    }

    /**
     * @return all {@link LivingEntity}s
     */
    public static Array<LivingEntity> getAllLivingEntities() {return livingEntities;}

    /**
     * Empties both registries
     */
    public static void clear() {
        entities.clear();
        livingEntities.clear();
    }
}
