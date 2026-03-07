package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

import static net.eckener.dungeon_crawler.RoomRegistry.getCurrentRoom;

public class Wall extends Sprite {

    private Polygon hitbox;
    private final Room room;

    public Wall(Texture texture, int x, int y) {
        super(texture);
        setSize(1,1);
        setX(x);
        setY(y);

        room = getCurrentRoom();
        WallRegistry.register(this);

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
     * @return the Hitbox Polygon of the Wall
     */
    public Polygon getHitbox() {
        return hitbox;
    }

    /**
     * @return the Room the Wall is in
     */
    public Room getRoom() {
        return room;
    }
}
