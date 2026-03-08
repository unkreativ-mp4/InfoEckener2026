package net.eckener.dungeon_crawler.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import net.eckener.dungeon_crawler.entities.Player;
import net.eckener.dungeon_crawler.logic.Room;

import static net.eckener.dungeon_crawler.logic.RoomRegistry.getCurrentRoom;

public abstract class Block extends Sprite {

    private Polygon hitbox;
    private final Room room;

    public Block(Texture texture, int x, int y) {
        super(texture);
        setSize(1,1);
        setX(x);
        setY(y);

        room = getCurrentRoom();
        BlockRegistry.register(this);

        float[] vertices = new float[]{
            0, 0,
            getWidth(), 0,
            getWidth(), getHeight(),
            0, getHeight()
        };
        hitbox = new Polygon(vertices);
        hitbox.setPosition(x, y);
    }

    public Block(Texture texture, int x, int y, Room room) {
        super(texture);
        setSize(1,1);
        setX(x);
        setY(y);

        this.room = room;
        BlockRegistry.register(this);

        float[] vertices = new float[]{
            0, 0,
            getWidth(), 0,
            getWidth(), getHeight(),
            0, getHeight()
        };
        hitbox = new Polygon(vertices);
        hitbox.setPosition(x, y);
    }

    /**
     * @return the Hitbox Polygon of the Block
     */
    public Polygon getHitbox() {
        return hitbox;
    }

    /**
     * @return the Room the Block is in
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Method which runs every frame
     * @param player {@link Player} in case it is needed, e.g. for distance calculations
     */
    public abstract void update(Player player);
}
