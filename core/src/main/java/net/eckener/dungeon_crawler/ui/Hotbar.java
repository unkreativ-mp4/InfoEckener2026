package net.eckener.dungeon_crawler.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import net.eckener.dungeon_crawler.Assets;
import net.eckener.dungeon_crawler.logic.Inventory;

public class Hotbar {
    private Inventory inventory;
    private InventoryUI inventoryUI;
    private Stage stage;

    public Hotbar(Stage pStage) {

        stage = pStage;

        inventory = new Inventory(1, 5, stage);
        inventoryUI = new InventoryUI(inventory, Assets.get(Assets.HOTBAR_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), stage.getHeight(), stage.getWidth(), 2.5f);

        float marginBottom = 20f;
        this.getInventoryUI().setPosition(
            (stage.getWidth() - this.getInventoryUI().getWidth()) / 2f,
            marginBottom
        );
        stage.addActor(this.getInventoryUI());
        inventoryUI.inventoryOpenManagement(inventory);
    }


    public Inventory getInventory() {
        return inventory;
    }

    public InventoryUI getInventoryUI() {
        return inventoryUI;
    }










}
