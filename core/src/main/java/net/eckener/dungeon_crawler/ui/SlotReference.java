package net.eckener.dungeon_crawler.ui;

import net.eckener.dungeon_crawler.logic.Inventory;

public record SlotReference(Inventory inv, int row, int col) {
}
