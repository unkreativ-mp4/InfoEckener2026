package net.eckener.dungeon_crawler.logic.json;

import com.badlogic.gdx.utils.Array;

public class RoomDefinition {
    public int gridX;
    public int gridY;

    public String background;
    public int width;
    public int height;

    public Array<WallDefinition> walls;
    public Array<EntityDefinition> entities;
}
