package net.eckener.dungeon_crawler.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import net.eckener.dungeon_crawler.Assets;
import net.eckener.dungeon_crawler.entities.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Aesthetically pleasing way of displaying {@code mana}
 */
public class ManaOrb {

    private final float x;
    private final float y;
    private final Player player;
    private final Map<Integer, Texture> indicators = new HashMap<>();

    public ManaOrb(Player player, float x, float y) {
        this.player = player;
        this.x = x;
        this.y = y;

        indicators.put(0,  Assets.get(Assets.MANA_INDICATOR_0));
        indicators.put(10, Assets.get(Assets.MANA_INDICATOR_10));
        indicators.put(20, Assets.get(Assets.MANA_INDICATOR_20));
        indicators.put(30, Assets.get(Assets.MANA_INDICATOR_30));
        indicators.put(40, Assets.get(Assets.MANA_INDICATOR_40));
        indicators.put(50, Assets.get(Assets.MANA_INDICATOR_50));
        indicators.put(60, Assets.get(Assets.MANA_INDICATOR_60));
        indicators.put(70, Assets.get(Assets.MANA_INDICATOR_70));
        indicators.put(80, Assets.get(Assets.MANA_INDICATOR_80));
        indicators.put(90, Assets.get(Assets.MANA_INDICATOR_90));
        indicators.put(100,Assets.get(Assets.MANA_INDICATOR_100));
    }

    /** Draws the ManaOrb with its correct fill level
     * @param batch the {@link SpriteBatch} in which to draw
     */
    public void draw(SpriteBatch batch) {

        int value = ((int)(player.getManaPercent() * 10)) * 10;

        batch.draw(indicators.get(value), x, y,100,100);
    }
}
