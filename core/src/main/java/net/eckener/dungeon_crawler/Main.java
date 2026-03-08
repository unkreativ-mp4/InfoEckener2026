package net.eckener.dungeon_crawler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import net.eckener.dungeon_crawler.debug.*;
import net.eckener.dungeon_crawler.entities.*;
import net.eckener.dungeon_crawler.items.Bow;
import net.eckener.dungeon_crawler.items.Item;
import net.eckener.dungeon_crawler.items.Maul;
import net.eckener.dungeon_crawler.logic.*;
import net.eckener.dungeon_crawler.ui.*;

import static net.eckener.dungeon_crawler.logic.RoomRegistry.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener{

    SpriteBatch spriteBatch;
    public static FitViewport viewport;
    public static OrthographicCamera camera;
    public static Stage stage;

    ManaOrb manaOrb;
    Health healthIcon;

    DebugOverlay debug;

    Zombie zombie;
    Skeleton skeleton;
    Chest chest;

    public Player player;

    private final IntSet downKeys = new IntSet(20);


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

        viewport = new FitViewport(16, 10);
        stage = new Stage(new ScreenViewport(), spriteBatch);

        loadRooms();


        // ───────────────────────────────
        // Player
        // ───────────────────────────────

        player = new Player(100, 100);

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
        manaOrb = new ManaOrb(player, 0,30);
        healthIcon = new Health(player,5,100);

        // ───────────────────────────────
        // Items & Inventory
        // ───────────────────────────────
        Maul woodenSword = new Maul(Assets.get(Assets.WOODEN_SWORD));
        Item coin = new Item("coin", "Coin", Assets.get(Assets.COIN), 60, 67);
        Bow darkBow = new Bow("dark_bow", "Dark Bow", Assets.get(Assets.DARK_BOW), 100, 1, 10000, 5);

        ItemStack coinStack = new ItemStack(coin, 5);
        ItemStack woodenSwordStack = new ItemStack(woodenSword, 1);
        ItemStack darkBowStack = new ItemStack(darkBow, 1);

        LootTable chestTable1 = new LootTable();
        chestTable1.add(coin, 1,24);
        chestTable1.add(woodenSword, 1,1);
        chestTable1.add(darkBow, 1, 1);

        chest = new Chest(chestTable1);

        player.getPlayerInventory().addItemStack(coinStack, 3, 3);

        player.getPlayerHotbar().getInventory().addItemStack(darkBowStack, 0, 0);


        // ───────────────────────────────
        // Enemies
        // ───────────────────────────────
        zombie = new Zombie(1, 1, Assets.get(Assets.WOODEN_SHOVEL), Assets.get(Assets.WOODEN_HOE));
        skeleton = new Skeleton(2,2,Assets.get(Assets.IRON_SHOVEL));
        new Wall(Assets.get(Assets.STONE_BRICKS), 4,5);

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

        float YwhenChestOpen;
        if(chest.isChestOpen()) {
            YwhenChestOpen = ((stage.getHeight() - player.getPlayerInventory().getInventoryUI().getHeight()) / 5f);
        }
        else {
            YwhenChestOpen = ((stage.getHeight() - player.getPlayerInventory().getInventoryUI().getHeight()) / 2f);
        }


        player.getPlayerInventory().getInventoryUI().setPosition(
            (stage.getWidth()  - player.getPlayerInventory().getInventoryUI().getWidth())  / 2f, YwhenChestOpen);

        chest.getChestInventoryUI().setPosition(
            (stage.getWidth()  - chest.getChestInventoryUI().getWidth())  / 2f,
            (stage.getHeight() - chest.getChestInventoryUI().getHeight())
        );

        player.getPlayerHotbar().getInventoryUI().setPosition(
            (stage.getWidth()  - player.getPlayerHotbar().getInventoryUI().getWidth())  / 2f,
            20f);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // --- UPDATE ---
        EntityRegistry.updateRoom(deltaTime, player);
        EntityRegistry.updateRoomMovement(deltaTime);
        handleScreenTransition(player);

        // --- CLEAR ---
        ScreenUtils.clear(Color.BLACK);

        // =========================
        // WORLD RENDER (game space)
        // =========================
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        spriteBatch.draw(getCurrentRoom().background,0, 0, getCurrentRoom().width, getCurrentRoom().height);

        EntityRegistry.renderRoom(spriteBatch);
        WallRegistry.renderRoom(spriteBatch);
        spriteBatch.end();

        // ======================
        // UI + DEBUG (screen space)
        // ======================
        stage.getViewport().apply();
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);

        spriteBatch.begin();
        manaOrb.draw(spriteBatch);
        healthIcon.draw(spriteBatch);
        debug.render();   // draw text only
        spriteBatch.end();

        // --- STAGE ---
        stage.act(deltaTime);
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

        if (downKeys.size >= 2) {
            onMultipleKeysDown(keycode);
        } else {

            switch (keycode) {
                case Input.Keys.I:
                    player.getPlayerInventory().getInventoryUI().inventoryOpenManagement(player.getPlayerInventory());
                    break;
                case Input.Keys.H:
                    player.heal(5);
                    break;
                case Input.Keys.M:
                    player.addMana(5);
                    break;
                case Input.Keys.L:
                    new Zombie(1,2,Assets.get(Assets.DIAMOND_SWORD),Assets.get(Assets.COIN));
                    break;
                case Input.Keys.U:
                    player.attack();
                    break;
                case Input.Keys.P:
                    chest.openCloseChest(player);
                    break;
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
        if (downKeys.contains(Input.Keys.SHIFT_LEFT) && downKeys.contains(Input.Keys.M)) {
            player.addMana(-5);
        }
        if (downKeys.contains(Input.Keys.SHIFT_LEFT) && downKeys.contains(Input.Keys.H)) {
            player.takeDamage(5);
        }
    }
}
