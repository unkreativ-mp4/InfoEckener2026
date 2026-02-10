package net.eckener.dungeon_crawler;

public class ItemStack {


    private Item item;
    private int amount;

    public ItemStack(Item pItem, int pAmount) {
        item = pItem;
        amount = pAmount;
    }

    public boolean isEmpty() {
        return item == null || amount == 0;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

}
