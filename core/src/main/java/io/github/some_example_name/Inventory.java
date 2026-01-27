package io.github.some_example_name;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Inventory extends Table {

    private final int rows;
    private final int cols;

    private final Table[][] slots;

    public Inventory(int rows, int cols, float uiScale){

        float slotSize = 48f * uiScale;
        float slotPad = 4f * uiScale;

        this.rows = rows;
        this.cols = cols;
        this.slots = new Table[rows][cols];

        defaults().size(slotSize).pad(slotPad);
        buildInventory();
        pack();

    }

    private void buildInventory() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                Table slot = new Table();
                slots[y][x] = slot;
                add(slot);
            }
            row();
        }
    }

    public Table[][] getSlots() {
        return slots;
    }

    public Table getSlot(int row, int col) {
        return slots[row][col];
    }
}
