package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import static net.eckener.dungeon_crawler.RoomRegistry.getCurrentRoom;

public final class WallRegistry {

    private static final Array<Wall> walls = new Array<>();
    private static final Array<Wall> roomWalls = new Array<>();

    public static void register(Wall wall) {
        walls.add(wall);

        if(wall.getRoom().equals(getCurrentRoom())) {
            registerRoom(wall);
        }
    }

    public static void registerRoom(Wall wall)  {
        roomWalls.add(wall);
    }

    public static void unregister(Wall wall) {
        walls.removeValue(wall, true);
        unregisterRoom(wall);
    }

    public static void unregisterRoom(Wall wall) {
        roomWalls.removeValue(wall, true);
    }

    public static void renderAll(SpriteBatch batch) {
        for (Wall wall : walls) {
            wall.draw(batch);
        }
    }

    public static void renderRoom(SpriteBatch batch) {
        for (Wall wall : roomWalls) {
            wall.draw(batch);
        }
    }

    public static Array<Wall> getAllWalls() {
        return walls;
    }

    public static Array<Wall> getAllRoomWalls() {
        return roomWalls;
    }

    public static void clear() {
        walls.clear();
        clearRoomWalls();
    }

    public static void clearRoomWalls() {
        roomWalls.clear();
    }

    public static void onRoomChange(Room room) {
        clearRoomWalls();

        for(Wall wall : walls) {
            if(wall.getRoom().equals(room)) {
                registerRoom(wall);
            }
        }
    }
}
