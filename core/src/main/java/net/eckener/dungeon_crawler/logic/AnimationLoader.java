package net.eckener.dungeon_crawler.logic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationLoader {

    public static Animation<TextureRegion> load(String folderPath, int frameCount, float frameDuration) {

        TextureRegion[] frames = new TextureRegion[frameCount];

        for (int i = 0; i < frameCount; i++) {
            Texture texture = Assets.manager.get(folderPath + "frame_" + i + ".png", Texture.class);
            frames[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, frames);
    }
}
