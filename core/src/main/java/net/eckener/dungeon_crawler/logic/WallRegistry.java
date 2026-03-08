package net.eckener.dungeon_crawler.logic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import static net.eckener.dungeon_crawler.logic.RoomRegistry.getCurrentRoom;

public final class WallRegistry {

    private static final Array<Wall> walls = new Array<>();
    private static final Array<Wall> roomWalls = new Array<>();

    /**
     * Registers a Wall into the global registry and if its Room matches into the roomRegistry
     * @param wall the Wall getting registered
     */
    public static void register(Wall wall) {
        walls.add(wall);

        if(wall.getRoom().equals(getCurrentRoom())) {
            registerRoom(wall);
        }
    }

    /**
     * Registers a wall into the roomRegistry
     * @param wall the Wall getting registered
     */
    public static void registerRoom(Wall wall)  {
        roomWalls.add(wall);
    }

    /**
     * Unregisters a Wall from both the global registry and the roomRegistry
     * @param wall the Wall getting unregistered
     */
    public static void unregister(Wall wall) {
        walls.removeValue(wall, true);
        unregisterRoom(wall);
    }

    /**
     * Unregisters a Wall from the roomRegistry
     * @param wall the Wall getting registered
     */
    public static void unregisterRoom(Wall wall) {
        roomWalls.removeValue(wall, true);
    }

    /**
     * Renders all Walls in the global registry
     * @param batch the spriteBatch to use
     */
    public static void renderAll(SpriteBatch batch) {
        for (Wall wall : walls) {
            wall.draw(batch);
        }
    }

    /**
     * Renders all Walls in the current Room
     * @param batch the spriteBatch to use
     */
    public static void renderRoom(SpriteBatch batch) {
        for (Wall wall : roomWalls) {
            wall.draw(batch);
        }
    }

    /**
     * @return the global registry
     */
    public static Array<Wall> getAllWalls() {
        return walls;
    }

    /**
     * @return the roomRegistry
     */
    public static Array<Wall> getAllRoomWalls() {
        return roomWalls;
    }

    /**
     * Clears both the global registry and the roomRegistry
     */
    public static void clear() {
        walls.clear();
        clearRoomWalls();
    }

    /**
     * Clears the roomRegistry
     */
    public static void clearRoomWalls() {
        roomWalls.clear();
    }

    /**
     * Updates all Walls
     */
    public static void updateAllWalls() {
        for(Wall wall : walls) {
            if(wall instanceof Door) {
                ((Door) wall).update();
            }
        }
    }

    /**
     * Updates the walls in the current Room
     */
    public static void updateRoomWalls() {
        for(Wall wall : roomWalls) {
            if(wall instanceof Door) {
                ((Door) wall).update();
            }
        }
    }

    /**
     * Clears the roomRegistry and registers the Walls of the new Room into the roomRegistry
     * @param room the newly entered Room
     */
    public static void onRoomChange(Room room) {
        clearRoomWalls();

        for(Wall wall : walls) {
            if(wall.getRoom().equals(room)) {
                registerRoom(wall);
            }
        }
    }
}
