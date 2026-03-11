package net.eckener.dungeon_crawler.logic;

import net.eckener.dungeon_crawler.items.Item;

public record LootEntry(Item item, int minAmount, int maxAmount, int weight) {

    public LootEntry {
        if (item == null) throw new IllegalArgumentException("Item is null");

    }
}
