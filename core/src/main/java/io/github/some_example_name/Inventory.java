package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class Inventory extends Table {

    private final Stage stage;
    private final int rows;
    private final int cols;

    private final Table[][] slots;

    public Inventory(Stage stage, int rows, int cols){
        this.stage = stage;
        this.rows = rows;
        this.cols = cols;
        this.slots = new Table[rows][cols];

        buildRootTable();
        buildInventory();

    }

    public void buildRootTable() {

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        rootTable.setDebug(true);
        rootTable.center();
        rootTable.add(this);

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
        pack();
    }

    public Table[][] getSlots() {
        return slots;
    }

    public Table getSlot(int row, int col) {
        return slots[row][col];
    }
}
