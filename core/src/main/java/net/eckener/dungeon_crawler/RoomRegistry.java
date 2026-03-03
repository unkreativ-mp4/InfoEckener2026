package net.eckener.dungeon_crawler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.ObjectMap;
import net.eckener.dungeon_crawler.entities.EntityRegistry;
import net.eckener.dungeon_crawler.entities.Player;

import static net.eckener.dungeon_crawler.Main.viewport;

public final class RoomRegistry {

    private static final ObjectMap<GridPoint2, Room> rooms = new ObjectMap<>();

    private static GridPoint2 currentCoords = new GridPoint2(0, 0);
    private static Room currentRoom;

    private RoomRegistry() {}

    /**
     * Loads Rooms into the Grid map with their correct dimensions
     */
    public static void loadRooms() {
        rooms.clear();

        addRoom(0, 0, new Room(Assets.get(Assets.BACKGROUND_PLACEHOLDER), 16, 10));

        addRoom(1, 0, new Room(Assets.get(Assets.BACKGROUND_ORANGE), 12, 5));

        addRoom(0, 1, new Room(Assets.get(Assets.BACKGROUND_PLACEHOLDER), 10, 6));

        setRoom(0, 0);
    }

    /**
     * Adds a single Room to the grid map
     * @param x the x coordinate of the Room
     * @param y the y coordinate of the Room
     * @param room the Room object
     */
    private static void addRoom(int x, int y, Room room) {
        rooms.put(new GridPoint2(x, y), room);
    }

    /**
     * Sets the current Room
     * @param x the x coordinate in the grid map of the new Room
     * @param y the y coordinate in the grid map of the new Room
     */
    public static void setRoom(int x, int y) {
        GridPoint2 coords = new GridPoint2(x, y);
        Room room = rooms.get(coords);

        if (room == null)
            throw new IllegalStateException("No room at: " + x + "," + y);

        currentCoords = coords;
        currentRoom = room;

        updateViewport();
        EntityRegistry.onRoomChange(currentRoom);
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
            currentCoords.x + direction.dx(),
            currentCoords.y + direction.dy()
        );

        Room nextRoom = rooms.get(target);
        if (nextRoom == null) return; // no room in that direction

        currentCoords = target;
        Room oldRoom = currentRoom;
        currentRoom = nextRoom;

        updateViewport();
        repositionPlayer(direction, player, oldRoom);
        EntityRegistry.onRoomChange(currentRoom);
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

