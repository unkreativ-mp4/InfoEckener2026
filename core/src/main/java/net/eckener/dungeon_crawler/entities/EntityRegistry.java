package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import net.eckener.dungeon_crawler.Main;
import net.eckener.dungeon_crawler.Room;

/**
 * An entity registry so {@link Main} has its peace
 */
public final class EntityRegistry {

    private static final Array<Entity> entities = new Array<>();
    private static final Array<LivingEntity> livingEntities = new Array<>();

    private static final Array<Entity> roomEntities = new Array<>();
    private static final Array<LivingEntity> roomLivingEntities = new Array<>();

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

    /**Enters an {@link Entity} into the Room registry and if it is a {@link LivingEntity} into the specialized registry
     * @param entity the {@link Entity} which to register
     */
    public static void registerRoom(Entity entity) {
        roomEntities.add(entity);
        if(entity instanceof LivingEntity){
            roomLivingEntities.add((LivingEntity) entity);
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
        unregisterRoom(entity);
    }

    /**Removes an {@link Entity} from the Room registry and if it is a {@link LivingEntity} from the specialized registry
     * @param entity the {@link Entity} which to remove
     */
    public static void unregisterRoom(Entity entity) {
        roomEntities.removeValue(entity, true);
        if(entity instanceof LivingEntity){
            roomEntities.removeValue(entity, true);
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

    /**
     * Calls the update method for all {@link Entity}s in the current Room so they can move and shit
     * @param delta Frametime for smooth updating
     * @param player some {@link Entity}s need it for pathfinding
     */
    public static void updateRoom(float delta, Player player) {
        for (Entity entity : roomEntities) {
            if(entity instanceof Enemy) {
                entity.update(delta, player);
            } else {
                entity.update(delta);
            }

            entity.updateHitbox();
        }
    }

    /**
     * Calls the updateMovement method for all Entities, so friction and Sprite-translation is processed
     * @param deltaTime Frametime for smooth movement
     */
    public static void updateMovementAll(float deltaTime) {
        for (Entity entity : entities) {
            entity.updateMovement(deltaTime);
        }
    }

    /**
     * Calls the updateMovement method for all Entities in the current Room, so friction and Sprite-translation is processed
     * @param deltaTime Frametime for smooth movement
     */
    public static void updateRoomMovement(float deltaTime) {
        for (Entity entity : roomEntities) {
            entity.updateMovement(deltaTime);
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
     * Renders all {@link Entity}s in the current Room
     * @param batch the {@link SpriteBatch} in which to draw/render the {@link Entity}s
     */
    public static void renderRoom(SpriteBatch batch) {
        for (Entity entity : roomEntities) {
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
     * @return all Entities in the current Room
     */
    public static Array<Entity> getAllRoomEntities() {
        return roomEntities;
    }

    /**
     * @return all LivingEntities in the current Room
     */
    public static Array<LivingEntity> getAllRoomLivingEntities() {return roomLivingEntities;}

    /**
     * Empties both global registries
     */
    public static void clear() {
        entities.clear();
        livingEntities.clear();
    }

    /**
     * Empties both Room registries
     */
    public static void clearRoomEntities() {
        roomEntities.clear();
        roomLivingEntities.clear();
    }

    /**
     * Clears the Room registries and registers the Entities of the new Room into the Room registries
     * @param room
     */
    public static void onRoomChange(Room room) {
        clearRoomEntities();

        for(Entity entity : entities) {
            if(entity.getRoom().equals(room)) {
                registerRoom(entity);
            }
             else if(entity instanceof Player) {
                registerRoom(entity);
            }
        }
    }
}
