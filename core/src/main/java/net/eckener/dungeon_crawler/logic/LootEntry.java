package net.eckener.dungeon_crawler.logic;

import net.eckener.dungeon_crawler.items.Item;

public class LootEntry {

    private Item item;
    private int weight;
    private int minAmount;
    private int maxAmount;

    public LootEntry(Item pItem, int pMinAmount, int pMaxAmount) {
        if (pItem == null) throw new IllegalArgumentException("ItemStack is null");

        item = pItem;
        weight = item.getRarity();
        minAmount = pMinAmount;
        maxAmount = pMaxAmount;
    }

    public Item getItem() {
        return item;
    }

    public int getWeight() {
        return weight;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }
}
