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
        super(texture);
        setSize(1,1);
        setX(x);
        setY(y);

        room = getCurrentRoom();
        WallRegistry.register(this);

        System.out.println("WallRegistry: " + WallRegistry.getAllWalls());
        System.out.println("WallRoomRegistry: " + WallRegistry.getAllRoomWalls());

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
