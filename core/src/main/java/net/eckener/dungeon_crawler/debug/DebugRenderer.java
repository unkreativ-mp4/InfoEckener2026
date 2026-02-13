package net.eckener.dungeon_crawler.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

/**
 * Renders the {@link DebugOverlay}
 */
public class DebugRenderer {

    private final SpriteBatch batch;
    private final BitmapFont font;
    private final int lineHeight = 15;

    public DebugRenderer(SpriteBatch batch) {
        this.batch = batch;
        this.font = new BitmapFont();
    }

    /**
     * Draws/renders the left side of the {@link DebugOverlay}
     * @param lines the actual text which should be drawn/rendered
     */
    public void drawLeft(List<String> lines) {
        int x = 8;
        int y = Gdx.graphics.getHeight() - 8;

        //batch.begin();
        for (String s : lines) {
            font.draw(batch, s, x, y);
            y -= lineHeight;
        }
        //batch.end();
    }

    /**
     * Draws/renders the right side of the {@link DebugOverlay}
     * @param lines the actual text which should be drawn/rendered
     */
    public void drawRight(List<String> lines) {
        int x = Gdx.graphics.getWidth() - 260;
        int y = Gdx.graphics.getHeight() - 8;

        //batch.begin();
        for (String s : lines) {
            font.draw(batch, s, x, y);
            y -= lineHeight;
        }
        //batch.end();
    }
}
