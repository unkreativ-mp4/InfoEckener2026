package net.eckener.dungeon_crawler.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import net.eckener.dungeon_crawler.items.ItemStack;

import java.util.Objects;

public class Inventory extends Table {

    private final int rows;
    private final int cols;

    private ItemStack[][] itemStacks;

    public Inventory(int rows, int cols){

        this.rows = rows;
        this.cols = cols;

        this.itemStacks = new ItemStack[rows][cols];

    }

    public boolean isSlotEmpty(int row, int col) {
        return itemStacks[row][col] == null;
    }

    public void fillInventoryWithItemStack(ItemStack itemStack){
        outerloop:
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if(isSlotEmpty(row, col)) {
                    itemStacks[row][col] = itemStack;
                    //System.out.println(y + " " + x);
                    break outerloop;
                }
            }
        }
    }

    public void addItemStack(ItemStack itemStack, int row, int col){
        itemStacks[row][col] = itemStack;
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

    public ItemStack getItemStack(int row, int col) {
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

    public void moveItemtoSlot(ItemStack[][] itemStacks, ItemStack itemStack, int newRow, int newCol, int orgRow, int orgCol) {

        if(itemStacks[orgRow][orgCol] == null) {
            System.out.println("No Item to move at this slot");
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
            System.out.println("Moved " + itemStack.getItem().getItemName() + " to pos: " + newRow + ", " + newCol);
            return;
        }

        boolean sameItem = Objects.equals(targetStack.getItem().getItemID(), itemStack.getItem().getItemID());

        if (sameItem) {
            targetStack.setAmount(targetStack.getAmount() + 1);
            if(itemStacks[orgRow][orgCol].getAmount() > 1) {
                itemStacks[orgRow][orgCol].setAmount(itemStacks[orgRow][orgCol].getAmount() - 1);
            }
            else {
                itemStacks[orgRow][orgCol] = null;
            }

            System.out.println("Moved " + itemStack.getItem().getItemName()
                + " to pos: " + newRow + ", " + newCol);
        } else {
            System.out.println("Inventory Slot occupied!");
        }
    }

    public void moveWholeItemStacktoSlot(ItemStack[][] itemStacks, ItemStack itemStack, int stackAmount, int newRow, int newCol, int orgRow, int orgCol) {
        stackAmount = itemStacks[orgRow][orgCol].getAmount();

        if(itemStacks[orgRow][orgCol] == null) {
            System.out.println("No Item to move at this slot");
            return;
        }

        ItemStack targetStack = itemStacks[newRow][newCol];

        if (targetStack == null) {
            ItemStack movedStack = new ItemStack(itemStack.getItem(), stackAmount);
            itemStacks[newRow][newCol] = movedStack;
            itemStacks[orgRow][orgCol] = null;
            System.out.println("Moved " + itemStack.getItem().getItemName() + " to pos: " + newRow + ", " + newCol);
            return;
        }

        boolean sameItem = Objects.equals(targetStack.getItem().getItemID(), itemStack.getItem().getItemID());

        if (sameItem) {
            targetStack.setAmount(targetStack.getAmount() + stackAmount);
            itemStacks[orgRow][orgCol] = null;

            System.out.println("Moved " + itemStack.getItem().getItemName()
                + " to pos: " + newRow + ", " + newCol);
        } else {
            System.out.println("Inventory Slot occupied!");
        }
    }
}
