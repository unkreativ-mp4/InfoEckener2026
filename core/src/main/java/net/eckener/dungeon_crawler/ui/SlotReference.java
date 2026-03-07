package net.eckener.dungeon_crawler.ui;

import net.eckener.dungeon_crawler.logic.Inventory;

public class SlotReference {
    public final Inventory inv;
    public final int row;
    public final int col;

    public SlotReference(Inventory inv, int row, int col) {
        this.inv = inv;
        this.row = row;
        this.col = col;
    }
}
