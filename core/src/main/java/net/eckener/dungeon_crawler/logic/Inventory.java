package net.eckener.dungeon_crawler.logic;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import net.eckener.dungeon_crawler.items.Item;
import net.eckener.dungeon_crawler.ui.InventoryUI;

import java.util.Objects;

import static net.eckener.dungeon_crawler.Main.stage;

public class Inventory extends Table {

    private final int rows;
    private final int cols;
    private final String inventoryName;
    private final InventoryUI inventoryUI;
    private final ItemStack[][] itemStacks;

    public Inventory(int rows, int cols){

        inventoryName = "";

        this.rows = rows;
        this.cols = cols;

        this.itemStacks = new ItemStack[rows][cols];

        inventoryUI = new InventoryUI(this, Assets.get(Assets.INVENTORY_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), stage.getHeight(), stage.getWidth(), 2.5f);
        inventoryUI.setPosition(
            (stage.getWidth() - inventoryUI.getWidth()) / 2f,
            (stage.getHeight() - inventoryUI.getHeight()) /2f
        );

        stage.addActor(inventoryUI);

    }

    public Inventory(int rows, int cols, String inventoryName){

        this.inventoryName = inventoryName;
        this.rows = rows;
        this.cols = cols;

        this.itemStacks = new ItemStack[rows][cols];

        inventoryUI = new InventoryUI(this, Assets.get(Assets.INVENTORY_BACKGROUND), Assets.get(Assets.INVENTORY_SLOT), stage.getHeight(), stage.getWidth(), 2.5f);
        inventoryUI.setPosition(
            (stage.getWidth() - inventoryUI.getWidth()) / 2f,
            (stage.getHeight() - inventoryUI.getHeight()) /2f
        );

        stage.addActor(inventoryUI);

    }


    public boolean isSlotEmpty(int row, int col) {
        return itemStacks[row][col] == null;
    }

    public void fillInventoryWithItemStack(ItemStack itemStack){
        outerLoop:
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(isSlotEmpty(row, col)) {
                    itemStacks[row][col] = itemStack;
                    break outerLoop;
                }
            }
        }
    }

    public void addItemStack(ItemStack itemStack, int row, int col){
        itemStacks[row][col] = itemStack;
    }

    public void removeItemStack(int row, int col) {
        itemStacks[row][col] = null;
    }

    @Override
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }


    public int getInventorySize() {
        return cols * rows;
    }

    public ItemStack[][] getItemStacks() {
        return itemStacks;
    }

    public String getInventoryName() { return inventoryName; }

    public ItemStack getItemStack( int row, int col) {
        return itemStacks[row][col];
    }

    public void printInventory(Inventory inventory) {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if(isSlotEmpty(y, x)) System.out.print("[]");
                else System.out.print(" [" + inventory.itemStacks[y][x].getItem().getItemName() + "] ");
            }
            System.out.println(" ");
        }

    }

    public void moveItemToSlot(ItemStack[][] itemStacks, ItemStack itemStack, int newRow, int newCol, int orgRow, int orgCol) {

        if(itemStacks[orgRow][orgCol] == null) {
            return;
        }

        ItemStack targetStack = itemStacks[newRow][newCol];

        if (targetStack == null) {
            ItemStack movedStack = new ItemStack(itemStack.getItem(), 1);
            itemStacks[newRow][newCol] = movedStack;
            if(itemStacks[orgRow][orgCol].getAmount() > 1) {
                itemStacks[orgRow][orgCol].setAmount(itemStacks[orgRow][orgCol].getAmount() - 1);
            }
            else {
                itemStacks[orgRow][orgCol] = null;
            }
            return;
        }

        boolean sameItem = Objects.equals(targetStack.getItem().getItemID(), itemStack.getItem().getItemID());

        if (sameItem) {
            targetStack.setAmount(targetStack.getAmount() + 1);
            if (itemStacks[orgRow][orgCol].getAmount() > 1) {
                itemStacks[orgRow][orgCol].setAmount(itemStacks[orgRow][orgCol].getAmount() - 1);
            } else {
                itemStacks[orgRow][orgCol] = null;
            }
        }
    }

    public void moveWholeItemStackToSlot(ItemStack[][] itemStacks, ItemStack itemStack, int newRow, int newCol, int orgRow, int orgCol) {
        int stackAmount = itemStacks[orgRow][orgCol].getAmount();

        if(itemStacks[orgRow][orgCol] == null) {
            return;
        }

        ItemStack targetStack = itemStacks[newRow][newCol];

        if (targetStack == null) {
            ItemStack movedStack = new ItemStack(itemStack.getItem(), stackAmount);
            itemStacks[newRow][newCol] = movedStack;
            itemStacks[orgRow][orgCol] = null;
            return;
        }

        boolean sameItem = Objects.equals(targetStack.getItem().getItemID(), itemStack.getItem().getItemID());

        if (sameItem) {
            targetStack.setAmount(targetStack.getAmount() + stackAmount);
            itemStacks[orgRow][orgCol] = null;
        }
    }

    public void transferOneTo(Inventory targetInv, int fromRow, int fromCol, int toRow, int toCol) {
        ItemStack[][] from = this.itemStacks;
        ItemStack[][] to = targetInv.itemStacks;

        if (from[fromRow][fromCol] == null) {
            return;
        }

        ItemStack sourceStack = from[fromRow][fromCol];
        ItemStack targetStack = to[toRow][toCol];

        if (targetStack == null) {
            // place 1 into target
            to[toRow][toCol] = new ItemStack(sourceStack.getItem(), 1);

            // remove 1 from source
            if (sourceStack.getAmount() > 1) sourceStack.setAmount(sourceStack.getAmount() - 1);
            else from[fromRow][fromCol] = null;

            return;
        }

        boolean sameItem = Objects.equals(targetStack.getItem().getItemID(), sourceStack.getItem().getItemID());
        if (sameItem) {
            targetStack.setAmount(targetStack.getAmount() + 1);

            if (sourceStack.getAmount() > 1) sourceStack.setAmount(sourceStack.getAmount() - 1);
            else from[fromRow][fromCol] = null;
        }
    }

    public void transferWholeStackTo(Inventory targetInv, int fromRow, int fromCol, int toRow, int toCol) {
        ItemStack[][] from = this.itemStacks;
        ItemStack[][] to = targetInv.itemStacks;

        if (from[fromRow][fromCol] == null) {
            return;
        }

        ItemStack sourceStack = from[fromRow][fromCol];
        int stackAmount = sourceStack.getAmount();

        ItemStack targetStack = to[toRow][toCol];

        if (targetStack == null) {
            to[toRow][toCol] = new ItemStack(sourceStack.getItem(), stackAmount);
            from[fromRow][fromCol] = null;
            return;
        }

        boolean sameItem = Objects.equals(targetStack.getItem().getItemID(), sourceStack.getItem().getItemID());
        if (sameItem) {
            targetStack.setAmount(targetStack.getAmount() + stackAmount);
            from[fromRow][fromCol] = null;
        }
    }

    public boolean containsItemType(Class<? extends Item> itemClass) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                ItemStack stack = itemStacks[row][col];

                if (stack != null && itemClass.isInstance(stack.getItem())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ItemStack findItemStack(Class<? extends Item> itemClass) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                ItemStack stack = itemStacks[row][col];

                if (stack != null && itemClass.isInstance(stack.getItem())) {
                    return stack;
                }
            }
        }

        return null;
    }

    public int[] findItemStackPosition(ItemStack targetStack) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(itemStacks[row][col] == targetStack) {
                    return new int[]{row, col};
                }
            }
        }

        return null;
    }

    public InventoryUI getInventoryUI() {
        return inventoryUI;
    }
}
