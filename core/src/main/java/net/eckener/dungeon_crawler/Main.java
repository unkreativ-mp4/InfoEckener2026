package net.eckener.dungeon_crawler;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
import net.eckener.dungeon_crawler.ui.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener, RoomChanger{

    SpriteBatch spriteBatch;
    FitViewport viewport;
    OrthographicCamera camera;

    ManaOrb manaOrb;
    Health healthIcon;

    DebugOverlay debug;

    //Array<Texture> backgrounds = new Array<>();
    Array<Room> rooms = new Array<>();
    Room currentRoom;
    //int currentBackground = 0;
    private boolean transitioning;
    private int currentRoomIndex = 0;

    Zombie zombie;
    Skeleton skeleton;

    public Player player;
    Inventory inventory;
    InventoryUI inventoryUI;

    Inventory penis;
    InventoryUI penisUI;

    private Stage stage;

    private final IntSet downKeys = new IntSet(20);

    public void loadRooms() {
        rooms.add(new Room (
            Assets.get(Assets.BACKGROUND_PLACEHOLDER),
            10, 5,
            1, 1
        ));

        rooms.add(new Room(
            Assets.get(Assets.BACKGROUND_ORANGE),
            12, 5,
            2, 1
        ));
    }

    public void setRoom(int index){
        currentRoomIndex = index;
        currentRoom = rooms.get(index);
        player.setWorldWidth(currentRoom.width);
    }

    @Override
    public void changeRoom(Direction direction) {

        int delta = 0;
        if (direction == Direction.LEFT)  delta = -1;
        if (direction == Direction.RIGHT) delta = 1;

        currentRoomIndex =
            (currentRoomIndex + delta + rooms.size) % rooms.size;

        currentRoom = rooms.get(currentRoomIndex);
        viewport.setWorldSize(currentRoom.width, currentRoom.height);
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        if (direction == Direction.LEFT)
            player.setX(currentRoom.width - player.getWidth());

        if (direction == Direction.RIGHT)
            player.setX(0);
    }

    public void handleScreenTransition(){
        if (transitioning) return;

        float playerLeft = player.getX();
        float playerRight = player.getX() + player.getWidth();

        // Player exits LEFT
        if (playerRight <= 0) {
            transitioning = true;

            changeRoom(Direction.LEFT);

            transitioning = false;
        }

        // Player exits RIGHT
        if (playerLeft >= currentRoom.width) {
            transitioning = true;

            changeRoom(Direction.RIGHT);

            transitioning = false;
        }
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

        viewport = new FitViewport(16, 10);
        stage = new Stage(new ScreenViewport(), spriteBatch);

        //loadBackgrounds();
        loadRooms();


        // ───────────────────────────────
        // Player
        // ───────────────────────────────

        player = new Player(100, 100, viewport, this, 0, 0);
        setRoom(0);
        player.setWorldWidth(currentRoom.width);
        player.setPosition(currentRoom.spawnX, currentRoom.spawnY);

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
        Item woodenSword = new Item("wooden_sword", "Wooden Sword", Assets.get(Assets.WOODEN_SWORD), 1, 64);
        Item coin = new Item("coin", "Coin", Assets.get(Assets.COIN), 5, 67);

        inventory = new Inventory(4, 7);
        penis = new Inventory(3,2);

        ItemStack coinStack = new ItemStack(coin, 5);
        inventory.addItemStack(coinStack, 3, 3);
        penis.addItemStack(coinStack, 0,0);

        inventory.printInventory(inventory);

        inventoryUI = new InventoryUI(inventory, Assets.get(Assets.INVENTORY_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), 3.5f);
        penisUI = new InventoryUI(penis, Assets.get(Assets.INVENTORY_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), 2f);
        stage.addActor(inventoryUI);
        stage.addActor(penisUI);


        // ───────────────────────────────
        // Enemies
        // ───────────────────────────────
        zombie = new Zombie(1, 1, Assets.get(Assets.WOODEN_SHOVEL), Assets.get(Assets.WOODEN_HOE));
        //skeleton = new Skeleton(2,2,Assets.get(Assets.IRON_SHOVEL));

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
        float deltaTime = Gdx.graphics.getDeltaTime();

        // --- UPDATE ---
        EntityRegistry.updateAll(deltaTime, player);
        EntityRegistry.updateMovementAll(deltaTime);
        handleScreenTransition();

        // --- CLEAR ---
        ScreenUtils.clear(Color.BLACK);

        // =========================
        // WORLD RENDER (game space)
        // =========================
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        spriteBatch.draw(currentRoom.background,0, 0, currentRoom.width, currentRoom.height);
        //spriteBatch.draw(backgrounds.get(currentBackground), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        EntityRegistry.renderAll(spriteBatch);
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
                zombie.addMomentum(50,50);
            }
            if(keycode == Input.Keys.P) {
                penisUI.openInventory(penis);
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
