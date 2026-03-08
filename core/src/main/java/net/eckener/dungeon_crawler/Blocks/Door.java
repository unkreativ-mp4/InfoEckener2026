package net.eckener.dungeon_crawler.Blocks;

import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.entities.Enemy;
import net.eckener.dungeon_crawler.entities.LivingEntity;
import net.eckener.dungeon_crawler.entities.Player;
import net.eckener.dungeon_crawler.logic.EntityRegistry;
import net.eckener.dungeon_crawler.logic.Room;

import static net.eckener.dungeon_crawler.logic.RoomRegistry.getCurrentRoom;

public class Door extends Block {

    public Door(Texture texture, int x, int y) {
        super(texture, x, y);
    }

    public Door(Texture texture, int x, int y, Room room) {
        super(texture, x, y, room);
    }

    /**
     * Updates the Door
     * <p>
     *     checks if there are Enemies remaining in the current Room (which should automatically be its own) and if not unregisters itself
     * </p>
     */
    @Override
    public void update(Player player) {
        if(getCurrentRoom().equals(getRoom())) {
            boolean roomHasNoEnemies = true;
            for(LivingEntity livingEntity : EntityRegistry.getAllRoomLivingEntities()) {
                if (livingEntity instanceof Enemy)  {
                    roomHasNoEnemies = false;
                    break;
                }
            }

            if(roomHasNoEnemies) {
                BlockRegistry.unregister(this);
            }
        }


    }
}
