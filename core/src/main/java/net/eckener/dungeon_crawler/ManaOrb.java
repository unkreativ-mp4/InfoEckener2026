package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class ManaOrb {

    private float x, y;
    private final Player player;
    //private AbstractMap.SimpleEntry<Integer, Texture>[] indicators;
    private Map<Integer, Texture> indicators = new HashMap<>();

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

    public void draw(SpriteBatch batch) {

        int value = ((int)(player.getManaPercent() * 10)) * 10;

        batch.draw(indicators.get(value), x, y,100,100);
    }
}
