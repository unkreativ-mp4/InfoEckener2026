package net.eckener.dungeon_crawler.logic.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import net.eckener.dungeon_crawler.entities.Skeleton;
import net.eckener.dungeon_crawler.entities.Zombie;
import net.eckener.dungeon_crawler.logic.*;

public class RoomLoader {

    public static void loadRooms(String folder) {

        Json json = new Json();

        FileHandle dir = Gdx.files.internal(folder);
        FileHandle[] files = dir.list(".json");

        for (FileHandle file : files) {

            RoomDefinition def = json.fromJson(RoomDefinition.class, file);

            Texture background = Assets.get(def.background);

            Room room = new Room(background, def.width, def.height);

            // walls
            for (WallDefinition wall : def.walls) {
                new Wall(Assets.get(wall.texture), wall.x, wall.y, room);
            }

            // entities
            for (EntityDefinition entity : def.entities) {
                spawnEntity(room, entity);
            }

            RoomRegistry.addRoom(def.gridX, def.gridY, room);
        }
    }

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
}
