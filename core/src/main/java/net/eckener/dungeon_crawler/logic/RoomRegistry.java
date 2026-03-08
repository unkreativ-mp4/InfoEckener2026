package net.eckener.dungeon_crawler.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ObjectMap;
import net.eckener.dungeon_crawler.entities.Player;
import net.eckener.dungeon_crawler.logic.json.RoomLoader;

import static net.eckener.dungeon_crawler.Main.viewport;

public final class RoomRegistry {

    private static final ObjectMap<GridPoint2, Room> rooms = new ObjectMap<>();

    private static GridPoint2 currentPosition = new GridPoint2(0, 0);
    private static Room currentRoom;

    private RoomRegistry() {}

    /**
     * Loads Rooms into the Grid map from json
     */
    public static void loadRooms() {
        rooms.clear();

        RoomLoader.loadRooms("rooms");

        setRoom(0, 0);
    }

    /**
     * Adds a single Room to the grid map
     * @param x the x coordinate of the Room
     * @param y the y coordinate of the Room
     * @param room the Room object
     */
    public static void addRoom(int x, int y, Room room) {
        rooms.put(new GridPoint2(x, y), room);
    }

    /**
     * Sets the current Room
     * @param x the x coordinate in the grid map of the new Room
     * @param y the y coordinate in the grid map of the new Room
     */
    public static void setRoom(int x, int y) {
        GridPoint2 position = new GridPoint2(x, y);
        Room room = rooms.get(position);

        if (room == null)
            throw new IllegalStateException("No room at: " + x + "," + y);

        currentPosition = position;
        currentRoom = room;

        updateViewport();
        EntityRegistry.onRoomChange(currentRoom);
        WallRegistry.onRoomChange(currentRoom);
    }

    /**
     * @return the currently displayed Room
     */
    public static Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Transitions the player to a new Room if possible and calls the next methods for clean transition
     * @param direction the Direction the Player is moving in
     * @param player the Player that is moving
     */
    public static void tryTransition(Direction direction, Player player) {
        GridPoint2 target = new GridPoint2(
            currentPosition.x + direction.dx(),
            currentPosition.y + direction.dy()
        );

        Room nextRoom = rooms.get(target);
        if (nextRoom == null) return; // no room in that direction

        currentPosition = target;
        Room oldRoom = currentRoom;
        currentRoom = nextRoom;

        updateViewport();
        repositionPlayer(direction, player, oldRoom);
        EntityRegistry.onRoomChange(currentRoom);
        WallRegistry.onRoomChange(currentRoom);
    }

    /**
     * Sets the Player position to the edge of the screen depending on the Direction the Player is moving in, also respects the Player's position in relation to the Room width/height
     * @param direction the Direction the Player is moving in
     * @param player the moving Player
     */
    private static void repositionPlayer(Direction direction, Player player, Room oldRoom) {
        float playerWidth = player.getWidth();
        float playerHeight = player.getHeight();

        switch (direction) {
            case LEFT -> player.setPosition(currentRoom.width - playerWidth, player.getY() / oldRoom.height * currentRoom.height);
            case RIGHT -> player.setPosition(0, player.getY() / oldRoom.height * currentRoom.height);
            case UP -> player.setPosition(player.getX() / oldRoom.width * currentRoom.width , 0);
            case DOWN -> player.setPosition(player.getX() / oldRoom.width * currentRoom.width , currentRoom.height - playerHeight);
        }
    }

    /**
     *Updates the Viewport to fit the dimensions of the new Room
     */
    private static void updateViewport() {
        viewport.setWorldSize(currentRoom.width, currentRoom.height);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    /**
     * Detects if the Player is near a screen edge and transitions screen and player accordingly
     * @param player the moving Player
     */
    public static void handleScreenTransition(Player player) {
        if (currentRoom == null) return;

        float left = player.getX();
        float right = left + player.getWidth();
        float bottom = player.getY();
        float top = bottom + player.getHeight();

        if (right <= 0) {
            tryTransition(Direction.LEFT, player);
        }
        else if (left >= currentRoom.width) {
            tryTransition(Direction.RIGHT, player);
        }
        else if (top <= 0) {
            tryTransition(Direction.DOWN, player);
        }
        else if (bottom >= currentRoom.height) {
            tryTransition(Direction.UP, player);
        }
    }
}

enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, 1),
    DOWN(0, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dx() { return dx; }
    public int dy() { return dy; }
}

