package net.eckener.dungeon_crawler;
import com.badlogic.gdx.graphics.Texture;

public class Room {
    public Texture background;
    public float width;
    public float height;

    public Room(Texture background, float width, float height) {

        this.background = background;
        this.width = width;
        this.height = height;
    }
}
