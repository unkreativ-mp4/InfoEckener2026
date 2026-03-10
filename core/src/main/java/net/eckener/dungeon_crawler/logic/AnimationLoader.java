package net.eckener.dungeon_crawler.logic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationLoader {

    public static Animation<TextureRegion> load(String path, int cols, int rows, float frameDuration) {

        Texture sheet = new Texture(Gdx.files.internal(path));

        TextureRegion[][] grid = TextureRegion.split(
            sheet,
            sheet.getWidth() / cols,
            sheet.getHeight() / rows
        );

        TextureRegion[] frames = new TextureRegion[cols * rows];

        int index = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                frames[index++] = grid[y][x];
            }
        }

        return new Animation<>(frameDuration, frames);
    }
}
