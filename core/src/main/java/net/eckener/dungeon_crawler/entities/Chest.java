package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import net.eckener.dungeon_crawler.logic.*;
import net.eckener.dungeon_crawler.ui.InventoryUI;

public class Chest extends Entity{

    private Inventory chestInventory;
    private InventoryUI chestInventoryUI;
    private boolean isChestOpen;
    private boolean generatedLoot = false;
    private final LootTable lootTable;


    public Chest(float pXPos, float pYPos, Stage stage, LootTable pLootTable) {
        super(pXPos, pYPos, Assets.get(Assets.CHEST),0);

        chestInventory = new Inventory(4, 7, "Chest", stage);
        chestInventoryUI = chestInventory.getInventoryUI();
        this.getChestInventoryUI().setPosition(
            (stage.getWidth() - this.chestInventoryUI.getWidth()) / 2f,
            (stage.getHeight() - this.chestInventoryUI.getHeight())
        );
        stage.addActor(this.getChestInventoryUI());
        isChestOpen = false;
        lootTable = pLootTable;
        generateLootIfNeeded();
    }

    @Override
    public void update(float delta){}

    @Override
    public void update(float delta, Player player) {
        if (!isChestOpen) return;

        float range = 1.5f;
        float dx = player.getX() - getX();
        float dy = player.getY() - getY();
        boolean inRange = (dx * dx + dy * dy) <= (range * range);

        if (!inRange) {
            chestInventoryUI.closeInventory(chestInventory);
            isChestOpen = false;
        }
    }

    @Override
    public void remove() {
        if (isChestOpen) {
            chestInventoryUI.closeInventory(chestInventory);
            isChestOpen = false;
        }
        chestInventoryUI.remove();
        EntityRegistry.unregister(this);
    }

    public InventoryUI getChestInventoryUI() {
        return chestInventoryUI;
    }


    public void openCloseChest(Player player) {

        float range = 1.5f;

        float dx = player.getX() - getX();
        float dy = player.getY() - getY();
        boolean inRange = (dx * dx + dy * dy) <= (range * range);

        if (!inRange) {
            if (isChestOpen) {
                chestInventoryUI.closeInventory(chestInventory);
                isChestOpen = false;
            }
            return;
        }

        if (!isChestOpen) {
            chestInventoryUI.openInventory(chestInventory);
            isChestOpen = true;

            if (!player.getPlayerInventory().getInventoryUI().getIsOpen()) {
                player.getPlayerInventory().getInventoryUI().openInventory(player.getPlayerInventory());
            }

        } else {
            chestInventoryUI.closeInventory(chestInventory);
            player.getPlayerInventory().getInventoryUI().closeInventory(player.getPlayerInventory());
            isChestOpen = false;
        }
    }

    private void generateLootIfNeeded() {
        if (generatedLoot) return;
        generatedLoot = true;

        int rolls = MathUtils.random(1, 4);

        for (int i = 0; i < rolls; i++) {
            LootEntry entry = lootTable.rollEntry();
            int amount = lootTable.rollAmount(entry);

            ItemStack stack = new ItemStack(entry.getItem(), amount);

            for (int tries = 0; tries < 20; tries++) {
                int r = MathUtils.random(0, chestInventory.getRows() - 1);
                int c = MathUtils.random(0, chestInventory.getCols() - 1);

                if(chestInventory.isSlotEmpty(r, c)) {
                    chestInventory.addItemStack(stack, r, c);
                    break;
                }
            }
        }
    }

    public boolean isChestOpen() {
        return isChestOpen;
    }
}
