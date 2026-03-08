package net.eckener.dungeon_crawler.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.entities.Enemy;
import net.eckener.dungeon_crawler.entities.LivingEntity;

import static net.eckener.dungeon_crawler.logic.RoomRegistry.getCurrentRoom;

public class Door extends Wall {

    public Door(Texture texture, int x, int y) {
        super(texture, x, y);
    }

    public Door(Texture texture, int x, int y, Room room) {
        super(texture, x, y, room);
    }

    public void open() {
        if(getCurrentRoom().equals(getRoom())) {
            boolean roomHasNoEnemies = true;
            for(LivingEntity livingEntity : EntityRegistry.getAllRoomLivingEntities()) {
                if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                    System.out.println(EntityRegistry.getAllRoomLivingEntities());
                }
                if (livingEntity instanceof Enemy)  {
                    roomHasNoEnemies = false;
                    break;
                }
            }

            if(roomHasNoEnemies) {
                WallRegistry.unregister(this);
            }
        }


    }
}
