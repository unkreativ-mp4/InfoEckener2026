package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. Listens to user input. */
public class Main extends InputAdapter implements ApplicationListener {

    SpriteBatch spriteBatch;
    FitViewport viewport;

    Array<Texture> backgrounds = new Array<>();
    int currentBackground = 0;

    CustomLabel healthLabel;
    CustomLabel manaLabel;

    Zombie zombie;

    public Player player;
    Inventory inventory;
    InventoryUI inventoryUI;
    private Stage stage;

    private final IntSet downKeys = new IntSet(20);
    public void loadBackgrounds(){
        backgrounds.add(Assets.get(Assets.BACKGROUND_PLACEHOLDER));
        backgrounds.add(Assets.get(Assets.BACKGROUND_ORANGE));
    }

    @Override
    public void create() {

        Assets.load();
        Assets.finishLoading();

        spriteBatch = new SpriteBatch();
        loadBackgrounds();
        stage = new Stage(new ScreenViewport(), spriteBatch);
        viewport = new FitViewport(8, 5);

        healthLabel = new CustomLabel("Player Health: ", 10, 25);
        manaLabel = new CustomLabel("Player Mana: ", 10, 10);
        stage.addActor(healthLabel.getLabel());
        stage.addActor(manaLabel.getLabel());

        player = new Player(100, 100, viewport, new BackgroundChanger(){
            @Override
            public void changeBackground(){
                currentBackground++;
                if(currentBackground >= backgrounds.size) {currentBackground = 0;}
            }
        });

        Item woodenSword = new Item("wooden_sword", "Wooden Sword", Assets.get(Assets.WOODEN_SWORD), 1, 64);

        inventory = new Inventory(5, 6);

        for(int i = 0; i < inventory.getInventorySize() - 5 ; i++) {
            ItemStack woodenSwordStack = new ItemStack(woodenSword, 3);
            inventory.fillInventoryWithItemStack(woodenSwordStack);
        }

        ItemStack woodenSwordStack = new ItemStack(woodenSword, 5);
        inventory.addItemStack(woodenSwordStack, 4, 3);

        inventory.printInventory(inventory);

        inventoryUI = new InventoryUI(inventory, Assets.get(Assets.INVENTORY_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), 2.5f);
        stage.addActor(inventoryUI);

        stage.setDebugAll(true);
        zombie = new Zombie(1,1,Assets.get(Assets.WOODEN_SHOVEL), Assets.get(Assets.WOODEN_HOE));

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
      /*if (!Assets.isFinished()) {
            Assets.update();   // keep loading
            return;            // optionally render loading screen
        } */

        float deltaTime = Gdx.graphics.getDeltaTime();

        player.move(deltaTime);
        player.dontGoPastScreen(viewport.getWorldHeight());

        zombie.update(deltaTime, player);
        player.update(deltaTime);

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(backgrounds.get(currentBackground), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        player.getPlayerSprite().draw(spriteBatch);

        zombie.draw(spriteBatch);

        healthLabel.setText("Player Health: " + player.getHealth());
        manaLabel.setText("Player Mana: " + player.getMana());

        spriteBatch.end();

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
