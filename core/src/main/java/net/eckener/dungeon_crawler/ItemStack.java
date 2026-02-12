package net.eckener.dungeon_crawler;

public class ItemStack {


    private Item item;
    private Weapon weapon;
    private int amount;

    public ItemStack(Item pItem, int pAmount) {
        item = pItem;
        amount = pAmount;
    }

    public ItemStack(Weapon weapon, int amount) {
        this.weapon = weapon;
        this.amount = amount;
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

    public Weapon getWeapon() {
        return weapon;
    }

    public boolean isWeapon() {
        return weapon != null;
    }
}
