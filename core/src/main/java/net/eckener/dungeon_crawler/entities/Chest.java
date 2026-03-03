package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import net.eckener.dungeon_crawler.Assets;
import net.eckener.dungeon_crawler.items.ItemStack;
import net.eckener.dungeon_crawler.ui.Inventory;
import net.eckener.dungeon_crawler.ui.InventoryUI;

import java.util.Random;

public class Chest extends Entity{
    private Inventory chestInventory;
    private InventoryUI chestInventoryUI;
    private boolean isChestOpen;


    public Chest(float pInventoryXPos, float pInventoryYPos, Stage stage) {
        super(4,2, Assets.get(Assets.CHEST),0);

        chestInventory = new Inventory(4, 7, "Chest", stage);
        chestInventoryUI = chestInventory.getInventoryUI();
        this.getChestInventoryUI().setPosition(
            (stage.getWidth() - this.getChestInventoryUI().getWidth()) / 2f,
            (stage.getHeight() - this.getChestInventoryUI().getHeight())
        );
        stage.addActor(this.getChestInventoryUI());
        isChestOpen = false;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void update(float delta, Player player) {

    }

    public InventoryUI getChestInventoryUI() {
        return chestInventoryUI;
    }


    public void openCloseChest(Player player) {

        //player.getPlayerInventory().getInventoryUI().setPosition();


        float range = 1.0f;

        float dx = player.getX() - getX();
        float dy = player.getY() - getY();

        boolean inRange = (dx * dx + dy * dy) <= (range * range);

        if (inRange) {
            chestInventoryUI.inventoryOpenManagement(chestInventory);
            player.getPlayerInventory().getInventoryUI().inventoryOpenManagement(player.getPlayerInventory());
        }
        isChestOpen = !isChestOpen;

    }

    public void generateRandomLoot() {
    }

    public void addItemStack(ItemStack pItem) {
        chestInventory.addItemStack(pItem, 2, 2);
    }

    public boolean isChestOpen() {
        return isChestOpen;
    }
}
