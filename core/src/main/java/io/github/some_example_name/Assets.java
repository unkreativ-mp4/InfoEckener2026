package io.github.some_example_name;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public final class Assets {

    public static final AssetManager manager = new AssetManager();

    // Asset paths (single source of truth)
    public static final String PLAYER_DOWN = "textures/entities/player_down.png";
    public static final String PLAYER_UP = "textures/entities/player_up.png";
    public static final String PLAYER_LEFT  = "textures/entities/player_left.png";
    public static final String PLAYER_RIGHT  = "textures/entities/player_right.png";
    public static final String INVENTORY_SLOT  = "textures/gui/inventory/inventory_slot.png";
    public static final String INVENTORY_BACKGROUND  = "textures/gui/inventory/inventory_background.png";
    public static final String WOODEN_SHOVEL  = "textures/placeholders/wooden_shovel.png";
    public static final String WOODEN_HOE  = "textures/placeholders/wooden_hoe.png";
    public static final String WOODEN_SWORD  = "textures/placeholders/wooden_sword.png";
    public static final String DIAMOND_SWORD  = "textures/placeholders/diamond_sword.png";
    public static final String BACKGROUND_ORANGE  = "textures/backgrounds/background_orange.png";
    public static final String BACKGROUND_PLACEHOLDER  = "textures/backgrounds/background_placeholder.jpg";
    public static final String COIN  = "textures/items/coin.png";
    public static final String MANA_INDICATOR_0   = "textures/gui/mana/indicator_0.png";
    public static final String MANA_INDICATOR_10  = "textures/gui/mana/indicator_10.png";
    public static final String MANA_INDICATOR_20  = "textures/gui/mana/indicator_20.png";
    public static final String MANA_INDICATOR_25  = "textures/gui/mana/indicator_25.png";
    public static final String MANA_INDICATOR_30  = "textures/gui/mana/indicator_30.png";
    public static final String MANA_INDICATOR_40  = "textures/gui/mana/indicator_40.png";
    public static final String MANA_INDICATOR_50  = "textures/gui/mana/indicator_50.png";
    public static final String MANA_INDICATOR_60  = "textures/gui/mana/indicator_60.png";
    public static final String MANA_INDICATOR_70  = "textures/gui/mana/indicator_70.png";
    public static final String MANA_INDICATOR_75  = "textures/gui/mana/indicator_75.png";
    public static final String MANA_INDICATOR_80  = "textures/gui/mana/indicator_80.png";
    public static final String MANA_INDICATOR_90  = "textures/gui/mana/indicator_90.png";
    public static final String MANA_INDICATOR_100 = "textures/gui/mana/indicator_100.png";




    private Assets() {} // prevent instantiation

    public static void load() {
        manager.load(PLAYER_DOWN, Texture.class);
        manager.load(PLAYER_UP, Texture.class);
        manager.load(PLAYER_LEFT , Texture.class);
        manager.load(PLAYER_RIGHT, Texture.class);
        manager.load(INVENTORY_SLOT, Texture.class);
        manager.load(INVENTORY_BACKGROUND, Texture.class);
        manager.load(WOODEN_SHOVEL, Texture.class);
        manager.load(WOODEN_HOE, Texture.class);
        manager.load(WOODEN_SWORD, Texture.class);
        manager.load(DIAMOND_SWORD, Texture.class);
        manager.load(BACKGROUND_ORANGE, Texture.class);
        manager.load(BACKGROUND_PLACEHOLDER, Texture.class);
        manager.load(COIN, Texture.class);
        manager.load(MANA_INDICATOR_0, Texture.class);
        manager.load(MANA_INDICATOR_10, Texture.class);
        manager.load(MANA_INDICATOR_20, Texture.class);
        manager.load(MANA_INDICATOR_25, Texture.class);
        manager.load(MANA_INDICATOR_30, Texture.class);
        manager.load(MANA_INDICATOR_40, Texture.class);
        manager.load(MANA_INDICATOR_50, Texture.class);
        manager.load(MANA_INDICATOR_60, Texture.class);
        manager.load(MANA_INDICATOR_70, Texture.class);
        manager.load(MANA_INDICATOR_75, Texture.class);
        manager.load(MANA_INDICATOR_80, Texture.class);
        manager.load(MANA_INDICATOR_90, Texture.class);
        manager.load(MANA_INDICATOR_100, Texture.class);


    }

    public static void update() {
        manager.update(); // async loading step
    }

    public static boolean isFinished() {
        return manager.isFinished();
    }

    public static void finishLoading() {
        manager.finishLoading();
    }

    public static Texture get(String path) {
        return manager.get(path, Texture.class);
    }

    public static void dispose() {
        manager.dispose();
    }
}
