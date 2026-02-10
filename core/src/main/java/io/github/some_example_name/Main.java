package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Array<Texture> backgrounds = new Array<>();
    int currentBackground = 0;
    Texture characterTextureUp, characterTextureDown, characterTextureLeft, characterTextureRight;
    Texture slotTexture, inventoryTexture;

    CustomLabel healthLabel;
    CustomLabel manaLabel;

    Player player;
    Inventory inventory;
    InventoryUI inventoryUI;
    private Stage stage;

    private final IntSet downKeys = new IntSet(20);
    public void loadBackgrounds(){
        backgrounds.add(new Texture("BackgroundTexture.jpg"));
        backgrounds.add(new Texture("Background2.png"));
    }

    @Override
    public void create() {

        spriteBatch = new SpriteBatch();
        loadBackgrounds();
        stage = new Stage(new ScreenViewport(), spriteBatch);
        viewport = new FitViewport(8, 5);

        healthLabel = new CustomLabel("Player Health: ", 10, 25);
        manaLabel = new CustomLabel("Player Mana: ", 10, 10);
        stage.addActor(healthLabel.getLabel());
        stage.addActor(manaLabel.getLabel());

        characterTextureDown = new Texture("Character_Texture_Front.png");
        characterTextureUp = new Texture("Character_Texture_Back.png");
        characterTextureLeft = new Texture("Character_Texture_Left.png");
        characterTextureRight = new Texture ("Character_Texture_Right.png");
        slotTexture = new Texture("Inventory_Slot_Texture.png");
        inventoryTexture = new Texture("inventory_Background_Texture.png");

        player = new Player(characterTextureUp, characterTextureDown, characterTextureLeft, characterTextureRight, 100, 100, viewport, new BackgroundChanger(){
            @Override
            public void changeBackground(){
                currentBackground++;
                if(currentBackground >= backgrounds.size) {currentBackground = 0;}
            }
        });

        Texture slotTexture = new Texture("Inventory_Slot_Texture.png");
        Texture inventoryTexture = new Texture("inventory_Background_Texture.png");
        Texture woodenSwordTexture = new Texture("Wooden_Sword_Texture.png");
        Texture coinTexture= new Texture("coin.png");

        Item woodenSword = new Item("wooden_sword", "Wooden Sword", woodenSwordTexture, 1, 64);
        Item coin = new Item("coin", "Coin", coinTexture, 5, 67);

        inventory = new Inventory(5, 6);

        for(int i = 0; i < inventory.getInventorySize() - 5 ; i++) {

            ItemStack woodenSwordStack = new ItemStack(woodenSword, 3);
            inventory.fillInventoryWithItemStack(woodenSwordStack);
        }

        ItemStack coinStack = new ItemStack(coin, 5);
        inventory.addItemStack(coinStack, 4, 3);

        inventory.moveItemtoSlot(inventory.getItemStacks(), inventory.getItemStack(4, 3), 4, 4, 4, 3);
        inventory.moveItemtoSlot(inventory.getItemStacks(), inventory.getItemStack(4, 4), 3, 4, 4, 3);
        inventory.moveItemtoSlot(inventory.getItemStacks(), inventory.getItemStack(2, 3), 2, 4, 2, 3);

        inventory.printInventory(inventory);

        inventoryUI = new InventoryUI(inventory, inventoryTexture, slotTexture, 3.5f);
        stage.addActor(inventoryUI);

        stage.setDebugAll(true);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage); // Stage first
        multiplexer.addProcessor(this);         // Main for keypresses
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

        float deltaTime = 4f * Gdx.graphics.getDeltaTime();

        player.move(deltaTime);
        /*
        player.dontGoPastScreen(viewport.getWorldWidth(), viewport.getWorldHeight());
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight(deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft(deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp(deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveDown(deltaTime);
        }

         */
        player.dontGoPastScreen(viewport.getWorldHeight());
        player.update();

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgrounds.get(currentBackground), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        player.getPlayerSprite().draw(spriteBatch);

        healthLabel.setText("Player Health: " + player.getHealth());
        manaLabel.setText("Player Mana: " + player.getMana());

        spriteBatch.end();

        stage.act(deltaTime);
        stage.draw();



        //draw();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
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
