package net.eckener.dungeon_crawler.logic.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import net.eckener.dungeon_crawler.Blocks.Door;
import net.eckener.dungeon_crawler.Blocks.Wall;
import net.eckener.dungeon_crawler.entities.Skeleton;
import net.eckener.dungeon_crawler.entities.Zombie;
import net.eckener.dungeon_crawler.logic.*;

public class RoomLoader {

    /**
     * Loads all Rooms and what they should contain from the selected folder by reading the JSON files
     * @param folder the folder from which to load
     */
    public static void loadRooms(String folder) {

        Json json = new Json();

        FileHandle dir = Gdx.files.internal(folder);
        FileHandle[] files = dir.list(".json");

        for (FileHandle file : files) {

            RoomDefinition def = json.fromJson(RoomDefinition.class, file);

            Texture background = Assets.get(def.background);

            Room room = new Room(background, def.width, def.height);

            // Blocks
            for (BlockDefinition block : def.blocks) {
                spawnBlock(room, block);
            }

            // Entities
            for (EntityDefinition entity : def.entities) {
                spawnEntity(room, entity);
            }

            RoomRegistry.addRoom(def.gridX, def.gridY, room);
        }
    }

    /**
     * Spawns an Entity with the attributes, that were specified in the JSON file
     * @param room the Room in which to spawn the Entity
     * @param entity the EntityDefinition which specifies the Entity type and its attributes
     */
    private static void spawnEntity(Room room, EntityDefinition entity) {

        switch (entity.type) {
            case "Skeleton":
                new Skeleton( entity.x, entity.y,Assets.get(Assets.IRON_SHOVEL), room);
                break;

            case "Zombie":
                new Zombie(entity.x, entity.y,Assets.get(Assets.WOODEN_SHOVEL),Assets.get(Assets.WOODEN_HOE), room);
                break;
        }
    }

    /**
     * Spawns a Block with the attributes, that were specified in the JSON file
     * @param room the Room in which to spawn the Block
     * @param block the BlockDefinition which specifies the Block type and its attributes
     */
    private static void spawnBlock(Room room, BlockDefinition block) {

        switch (block.type) {
            case "Wall":
                new Wall(Assets.get(block.texture), block.x, block.y, room);
                break;

            case "Door":
                new Door(Assets.get(block.texture), block.x, block.y, room);
                break;
        }
    }
}
