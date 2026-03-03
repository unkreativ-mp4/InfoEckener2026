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


    public Chest(float pInventoryXPos, float pInventoryYPos, Stage stage) {
        super(4,2, Assets.get(Assets.CHEST),0);

        chestInventory = new Inventory(4, 7, "Chest", stage);
        chestInventoryUI = new InventoryUI(chestInventory, Assets.get(Assets.INVENTORY_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), pInventoryXPos, pInventoryYPos, 3.5f);

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

        player.getPlayerInventory().getInventoryUI().inventoryOpenManagement(chestInventory);

        float range = 1.0f;

        float dx = player.getX() - getX();
        float dy = player.getY() - getY();

        boolean inRange = (dx * dx + dy * dy) <= (range * range);

        if (inRange) chestInventoryUI.inventoryOpenManagement(chestInventory);

    }

    public void generateRandomLoot() {
    }

    public void addItemStack(ItemStack pItem) {
        chestInventory.addItemStack(pItem, 2, 2);
    }
}
