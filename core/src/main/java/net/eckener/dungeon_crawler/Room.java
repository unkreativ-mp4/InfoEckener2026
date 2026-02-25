package net.eckener.dungeon_crawler;
import com.badlogic.gdx.graphics.Texture;

public class Room {
    public Texture background;
    public float width;
    public float height;
    public float spawnX;
    public float spawnY;

    public Room(Texture background,
                float width,
                float height,
                float spawnX,
                float spawnY) {

        this.background = background;
        this.width = width;
        this.height = height;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }
}
