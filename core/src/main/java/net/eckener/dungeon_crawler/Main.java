package net.eckener.dungeon_crawler;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import net.eckener.dungeon_crawler.debug.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener, RoomChanger{

    SpriteBatch spriteBatch;
    FitViewport viewport;
    private OrthographicCamera camera;

    DebugOverlay debug;

    //Array<Texture> backgrounds = new Array<>();
    Array<Room> rooms = new Array<>();
    Room currentRoom;
    //int currentBackground = 0;
    private boolean transitioning;
    private int currentRoomIndex = 0;

    CustomLabel healthLabel;
    CustomLabel manaLabel;

    Zombie zombie;

    public Player player;
    Inventory inventory;
    InventoryUI inventoryUI;
    private Stage stage;

    public void update2(){
        handleScreenTransition();
    }
    private final IntSet downKeys = new IntSet(20);
    /*public void loadBackgrounds(){
        backgrounds.add(Assets.get(Assets.BACKGROUND_PLACEHOLDER));
        backgrounds.add(Assets.get(Assets.BACKGROUND_ORANGE));
    }*/
    public void loadRooms() {
        rooms.add(new Room (
            Assets.get(Assets.BACKGROUND_PLACEHOLDER),
            10, 5,
            1, 1
        ));

        rooms.add(new Room(
            Assets.get(Assets.BACKGROUND_ORANGE),
            10, 5,
            2, 1
        ));
    }

    public void setRoom(int index){
        currentRoomIndex = index;
        currentRoom = rooms.get(index);
        player.setWorldWidth(currentRoom.width);
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

        //loadBackgrounds();
        loadRooms();


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
        /*player = new Player(100, 100, viewport, () -> {
                currentBackground++;
                if (currentBackground >= backgrounds.size) {
                    currentBackground = 0;
                }
            }
        );*/
        player = new Player(100, 100, viewport, this, 0, 0);
        setRoom(0);
        player.setWorldWidth(currentRoom.width);
        player.setPosition(currentRoom.spawnX, currentRoom.spawnY);

        // ───────────────────────────────
        // Debug Overlay
        // ───────────────────────────────
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();

        DebugStats stats     = new DebugStats(camera);
        DebugLayout layout  = new DebugLayout(stats);
        DebugRenderer debugRenderer = new DebugRenderer(spriteBatch);
        DebugInput debugInput = new DebugInput();

        debug = new DebugOverlay(layout, debugRenderer, debugInput);

        // ───────────────────────────────
        // Items & Inventory
        // ───────────────────────────────
        Item woodenSword = new Item("wooden_sword", "Wooden Sword", Assets.get(Assets.WOODEN_SWORD), 1, 64);

        inventory = new Inventory(5, 6);

        for (int i = 0; i < inventory.getInventorySize() - 5; i++) {
            inventory.fillInventoryWithItemStack(new ItemStack(woodenSword, 3));
        }

        inventory.addItemStack(new ItemStack(woodenSword, 5), 4, 3);
        inventory.printInventory(inventory);

        inventoryUI = new InventoryUI(inventory, Assets.get(Assets.INVENTORY_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), 2.5f);

        stage.addActor(inventoryUI);

        // ───────────────────────────────
        // Debug flags
        // ───────────────────────────────
        stage.setDebugAll(true);

        // ───────────────────────────────
        // Enemies
        // ───────────────────────────────
        zombie = new Zombie(1, 1, Assets.get(Assets.WOODEN_SHOVEL), Assets.get(Assets.WOODEN_HOE));

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
    public void changeRoom(Direction direction) {
        int nextIndex = currentRoomIndex;

        if (direction == Direction.LEFT) nextIndex--;
        if (direction == Direction.RIGHT) nextIndex++;

        if (nextIndex < 0 || nextIndex >= rooms.size) return;

        currentRoomIndex = nextIndex;
        currentRoom = rooms.get(currentRoomIndex);

        // reposition player based on direction
        if (direction == Direction.LEFT) player.setX((currentRoom.width - player.getX()));
        if (direction == Direction.RIGHT) player.setPosition((currentRoom.width - player.getX()), 0);
    }
    public void handleScreenTransition(){
        if (transitioning) return;

        float playerLeft = player.getX();
        System.out.println("playerLeft perceived x: "+ playerLeft);
        float playerRight = player.getX() + player.getWidth();
        System.out.println("playerRight perceived x: "+ playerRight);
        //System.out.println("currentRoom width: " + currentRoom.width);
        //System.out.println("player width: " + player.getWidth());

        // Player exits LEFT
        if (playerRight < 0) {
            transitioning = true;

            changeRoom(Direction.LEFT);

            transitioning = false;
        }

        // Player exits RIGHT
        if (playerLeft > currentRoom.width) {
            transitioning = true;

            changeRoom(Direction.RIGHT);

            transitioning = false;
        }
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
        player.move(delta);
        zombie.update(delta, player);
        player.update(delta);
        update2();
        System.out.println("Player X: " + player.getX());

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
        spriteBatch.draw(currentRoom.background,0, 0, currentRoom.width, currentRoom.height);
        //spriteBatch.draw(backgrounds.get(currentBackground), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        player.getPlayerSprite().draw(spriteBatch);
        zombie.draw(spriteBatch);
        spriteBatch.end();

        // ======================
        // UI + DEBUG (screen space)
        // ======================
        stage.getViewport().apply();
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);

        spriteBatch.begin();
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
                player.addHealth(1);
            }
            if(keycode == Input.Keys.M) {
                player.addMana(1);
            }
            if(keycode == Input.Keys.C) {
                zombie.attack(player);
            }
            if(keycode == Input.Keys.P) {
                player.attack(zombie,9);
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
            player.addMana(-1);
        }
        if (downKeys.contains(Input.Keys.SHIFT_LEFT) && downKeys.contains(Input.Keys.H)){
            player.addHealth(-1);
        }
    }
}
