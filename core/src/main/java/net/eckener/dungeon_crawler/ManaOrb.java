package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.AbstractMap;

public class ManaOrb {

    private float x, y;
    private final Player player;
    private AbstractMap.SimpleEntry<Integer, Texture>[] indicators;

    public ManaOrb(Player player, float x, float y) {
        this.player = player;
        this.x = x;
        this.y = y;



        AbstractMap.SimpleEntry<Integer, Texture> ind0 = new AbstractMap.SimpleEntry<>(0, Assets.get(Assets.MANA_INDICATOR_0));
        AbstractMap.SimpleEntry<Integer, Texture> ind10 = new AbstractMap.SimpleEntry<>(10, Assets.get(Assets.MANA_INDICATOR_10));
        AbstractMap.SimpleEntry<Integer, Texture> ind20 = new AbstractMap.SimpleEntry<>(20, Assets.get(Assets.MANA_INDICATOR_20));
        AbstractMap.SimpleEntry<Integer, Texture> ind30 = new AbstractMap.SimpleEntry<>(30, Assets.get(Assets.MANA_INDICATOR_30));
        AbstractMap.SimpleEntry<Integer, Texture> ind40 = new AbstractMap.SimpleEntry<>(40, Assets.get(Assets.MANA_INDICATOR_40));
        AbstractMap.SimpleEntry<Integer, Texture> ind50 = new AbstractMap.SimpleEntry<>(50, Assets.get(Assets.MANA_INDICATOR_50));
        AbstractMap.SimpleEntry<Integer, Texture> ind60 = new AbstractMap.SimpleEntry<>(60, Assets.get(Assets.MANA_INDICATOR_60));
        AbstractMap.SimpleEntry<Integer, Texture> ind70 = new AbstractMap.SimpleEntry<>(70, Assets.get(Assets.MANA_INDICATOR_70));
        AbstractMap.SimpleEntry<Integer, Texture> ind80 = new AbstractMap.SimpleEntry<>(80, Assets.get(Assets.MANA_INDICATOR_80));
        AbstractMap.SimpleEntry<Integer, Texture> ind90 = new AbstractMap.SimpleEntry<>(90, Assets.get(Assets.MANA_INDICATOR_90));
        AbstractMap.SimpleEntry<Integer, Texture> ind100 = new AbstractMap.SimpleEntry<>(100, Assets.get(Assets.MANA_INDICATOR_100));
    }

    public void draw(SpriteBatch batch) {

        int value = ((int)(player.getManaPercent() * 10)) * 10;

        batch.draw(, x, y);
    }
}
