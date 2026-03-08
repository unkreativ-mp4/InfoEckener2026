package net.eckener.dungeon_crawler.Blocks;

import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.entities.Player;
import net.eckener.dungeon_crawler.logic.Room;

public class Wall extends Block {
    public Wall(Texture texture, int x, int y) {
        super(texture, x, y);
    }

    public Wall(Texture texture, int x, int y, Room room) {
        super(texture, x, y, room);
    }

    @Override
    public void update(Player player) {

    }
}
