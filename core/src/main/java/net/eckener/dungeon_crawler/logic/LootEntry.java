package net.eckener.dungeon_crawler.logic;

import net.eckener.dungeon_crawler.items.Item;

public class LootEntry {

    private Item item;
    private int weight;
    private int minAmount;
    private int maxAmount;

    public LootEntry(Item item, int minAmount, int maxAmount, int weight) {
        if (item == null) throw new IllegalArgumentException("Item is null");

        this.item = item;
        this.weight = weight;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
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
