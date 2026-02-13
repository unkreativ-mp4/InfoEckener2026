package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public final class EntityRegistry {

    private static final Array<Entity> entities = new Array<>();

    private EntityRegistry() {}

    public static void register(Entity entity) {
        entities.add(entity);
    }

    public static void unregister(Entity entity) {
        entities.removeValue(entity, true);
    }

    public static void updateAll(float delta) {
        for (Entity entity : entities) {
            entity.update(delta);
        }
    }

    public static void renderAll(SpriteBatch batch) {
        for (Entity entity : entities) {
            entity.draw(batch);
        }
    }

    public static void clear() {
        entities.clear();
    }
}
