package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.w3c.dom.Text;

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

}
