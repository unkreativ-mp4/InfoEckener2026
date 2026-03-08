package net.eckener.dungeon_crawler.Blocks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import net.eckener.dungeon_crawler.entities.Player;
import net.eckener.dungeon_crawler.logic.Room;

import static net.eckener.dungeon_crawler.logic.RoomRegistry.getCurrentRoom;

public final class BlockRegistry {

    private static final Array<Block> blocks = new Array<>();
    private static final Array<Block> roomBlocks = new Array<>();

    /**
     * Registers a Block into the global registry and if its Room matches into the roomRegistry
     * @param block the Block getting registered
     */
    public static void register(Block block) {
        blocks.add(block);

        if(block.getRoom().equals(getCurrentRoom())) {
            registerRoom(block);
        }
    }

    /**
     * Registers a block into the roomRegistry
     * @param block the Block getting registered
     */
    public static void registerRoom(Block block)  {
        roomBlocks.add(block);
    }

    /**
     * Unregisters a Block from both the global registry and the roomRegistry
     * @param block the Block getting unregistered
     */
    public static void unregister(Block block) {
        blocks.removeValue(block, true);
        unregisterRoom(block);
    }

    /**
     * Unregisters a Block from the roomRegistry
     * @param block the Block getting registered
     */
    public static void unregisterRoom(Block block) {
        roomBlocks.removeValue(block, true);
    }

    /**
     * Renders all Blocks in the global registry
     * @param batch the spriteBatch to use
     */
    public static void renderAll(SpriteBatch batch) {
        for (Block block : blocks) {
            block.draw(batch);
        }
    }

    /**
     * Renders all Blocks in the current Room
     * @param batch the spriteBatch to use
     */
    public static void renderRoom(SpriteBatch batch) {
        for (Block block : roomBlocks) {
            block.draw(batch);
        }
    }

    /**
     * @return the global registry
     */
    public static Array<Block> getAllBlocks() {
        return blocks;
    }

    /**
     * @return the roomRegistry
     */
    public static Array<Block> getAllRoomBlocks() {
        return roomBlocks;
    }

    /**
     * Clears both the global registry and the roomRegistry
     */
    public static void clear() {
        blocks.clear();
        clearRoomBlocks();
    }

    /**
     * Clears the roomRegistry
     */
    public static void clearRoomBlocks() {
        roomBlocks.clear();
    }

    /**
     * Updates all Blocks
     */
    public static void updateAllBlocks(Player player) {
        for(Block block : blocks) {
           block.update(player);
        }
    }

    /**
     * Updates the blocks in the current Room
     */
    public static void updateRoomBlocks(Player player) {
        for(Block block : roomBlocks) {
            block.update(player);
        }
    }

    /**
     * Clears the roomRegistry and registers the Blocks of the new Room into the roomRegistry
     * @param room the newly entered Room
     */
    public static void onRoomChange(Room room) {
        clearRoomBlocks();

        for(Block block : blocks) {
            if(block.getRoom().equals(room)) {
                registerRoom(block);
            }
        }
    }
}
