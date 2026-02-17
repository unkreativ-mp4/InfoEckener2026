package net.eckener.dungeon_crawler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import net.eckener.dungeon_crawler.debug.*;
import net.eckener.dungeon_crawler.entities.EntityRegistry;
import net.eckener.dungeon_crawler.entities.Player;
import net.eckener.dungeon_crawler.entities.Skeleton;
import net.eckener.dungeon_crawler.entities.Zombie;
import net.eckener.dungeon_crawler.items.Item;
import net.eckener.dungeon_crawler.items.ItemStack;
import net.eckener.dungeon_crawler.ui.CustomLabel;
import net.eckener.dungeon_crawler.ui.Inventory;
import net.eckener.dungeon_crawler.ui.InventoryUI;
import net.eckener.dungeon_crawler.ui.ManaOrb;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;
    OrthographicCamera camera;

    ManaOrb manaOrb;

    DebugOverlay debug;

    Array<Texture> backgrounds = new Array<>();
    int currentBackground = 0;

    CustomLabel healthLabel;
    CustomLabel manaLabel;

    Zombie zombie;
    Skeleton skeleton;

    public Player player;
    Inventory inventory;
    InventoryUI inventoryUI;

    Inventory chest;
    InventoryUI chestUI;

    private Stage stage;

    private final IntSet downKeys = new IntSet(20);
    public void loadBackgrounds(){
        backgrounds.add(Assets.get(Assets.BACKGROUND_PLACEHOLDER));
        backgrounds.add(Assets.get(Assets.BACKGROUND_ORANGE));
    }

    @Override
    public void create() {

        // ───────────────────────────────
        // Asset loading
        // ───────────────────────────────
        Assets.load();
        Assets.finishLoading();

        // ───────────────────────────────
        // Rendering & Viewports
        // ───────────────────────────────
        spriteBatch = new SpriteBatch();

        viewport = new FitViewport(8, 5);
        stage = new Stage(new ScreenViewport(), spriteBatch);

        loadBackgrounds();

        // ───────────────────────────────
        // UI Labels
        // ───────────────────────────────
        healthLabel = new CustomLabel("Player Health:", 10, 25);
        manaLabel   = new CustomLabel("Player Mana:",   10, 10);

        stage.addActor(healthLabel.getLabel());
        stage.addActor(manaLabel.getLabel());

        // ───────────────────────────────
        // Player
        // ───────────────────────────────
        player = new Player(100, 100, viewport, () -> {
                currentBackground++;
                if (currentBackground >= backgrounds.size) {
                    currentBackground = 0;
                }
            }
        );
        EntityRegistry.register(player);

        // ───────────────────────────────
        // Debug Overlay
        // ───────────────────────────────
        camera = (OrthographicCamera) viewport.getCamera();

        DebugStats stats     = new DebugStats(camera);
        DebugLayout layout  = new DebugLayout(stats);
        DebugRenderer debugRenderer = new DebugRenderer(spriteBatch);
        DebugInput debugInput = new DebugInput();

        debug = new DebugOverlay(layout, debugRenderer, debugInput);

        stage.setDebugAll(false);

        // ───────────────────────────────
        // GUI
        // ───────────────────────────────
        manaOrb = new ManaOrb(player, 500,50);

        // ───────────────────────────────
        // Items & Inventory
        // ───────────────────────────────
        Item woodenSword = new Item("wooden_sword", "Wooden Sword", Assets.get(Assets.WOODEN_SWORD), 1, 64);
        Item coin = new Item("coin", "Coin", Assets.get(Assets.COIN), 5, 67);

        inventory = new Inventory(4, 7, "Inventory");
        chest = new Inventory(3,2, "Chest");

        ItemStack coinStack = new ItemStack(coin, 5);
        inventory.addItemStack(coinStack, 3, 3);
        chest.addItemStack(coinStack, 0,0);

        inventory.printInventory(inventory);

        inventoryUI = new InventoryUI(inventory, Assets.get(Assets.INVENTORY_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), 3.5f);
        chestUI = new InventoryUI(chest, Assets.get(Assets.INVENTORY_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), 2f);
        stage.addActor(inventoryUI);
        stage.addActor(chestUI);


        // ───────────────────────────────
        // Enemies
        // ───────────────────────────────
        zombie = new Zombie(1, 1, Assets.get(Assets.WOODEN_SHOVEL), Assets.get(Assets.WOODEN_HOE));
        skeleton = new Skeleton(2,2,Assets.get(Assets.IRON_SHOVEL));

        // ───────────────────────────────
        // Input Handling
        // ───────────────────────────────
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(debug.input());
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        if(width <= 0 || height <= 0) return;

        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // --- UPDATE ---
        EntityRegistry.updateAll(delta, player);

        healthLabel.setText("Player Health: " + player.getHealth());
        manaLabel.setText("Player Mana: " + player.getMana());

        // --- CLEAR ---
        ScreenUtils.clear(Color.BLACK);

        // =========================
        // WORLD RENDER (game space)
        // =========================
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        spriteBatch.draw(backgrounds.get(currentBackground), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        EntityRegistry.renderAll(spriteBatch);
        spriteBatch.end();

        // ======================
        // UI + DEBUG (screen space)
        // ======================
        stage.getViewport().apply();
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);

        spriteBatch.begin();
        manaOrb.draw(spriteBatch);
        debug.render();   // draw text only
        spriteBatch.end();

        // --- STAGE ---
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Assets.dispose();
    }

    @Override
    public boolean keyDown (int keycode) {
        downKeys.add(keycode);
        System.out.println(downKeys+" Tasten wurde gedrückt (Keycode)");

        if (downKeys.size >= 2){
            onMultipleKeysDown(keycode);
        } else {

            if(keycode == Input.Keys.I) {
                inventoryUI.openInventory(inventory);
            }
            if(keycode == Input.Keys.H) {
                player.heal(5);
            }
            if(keycode == Input.Keys.M) {
                player.addMana(5);
            }
            if(keycode == Input.Keys.C) {
                zombie.attack(player);
            }
            if(keycode == Input.Keys.U) {
                player.attack(zombie);
            }
            if(keycode == Input.Keys.P) {
                chestUI.openInventory(chest);
            }

        }
        return true;
    }

    @Override
    public boolean keyUp (int keycode) {
        downKeys.remove(keycode);
        return true;
    }

    private void onMultipleKeysDown (int mostRecentKeycode){
        //Keys that are currently down are in the IntSet.
        if (downKeys.contains(Input.Keys.SHIFT_LEFT) && downKeys.contains(Input.Keys.M)){
            player.addMana(-5);
        }
        if (downKeys.contains(Input.Keys.SHIFT_LEFT) && downKeys.contains(Input.Keys.H)){
            player.takeDamage(5);
        }
    }
}
