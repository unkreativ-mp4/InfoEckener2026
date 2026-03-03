package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

import static net.eckener.dungeon_crawler.RoomRegistry.getCurrentRoom;

public class Wall extends Sprite {

    private float[] vertices;
    private Polygon hitbox;
    private final Room room;

    public Wall(Texture texture, int x, int y) {
        super(texture, x, y, 1, 1);
        room = getCurrentRoom();
        WallRegistry.register(this);

        vertices = new float[] {
            0, 0,
            getWidth(), 0,
            getWidth(), getHeight(),
            0, getHeight()
        };
        hitbox = new Polygon(vertices);
    }

    public Polygon getHitbox() {
        return hitbox;
    }

    public Room getRoom() {
        return room;
    }
}
