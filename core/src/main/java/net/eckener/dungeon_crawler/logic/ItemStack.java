package net.eckener.dungeon_crawler.logic;

import net.eckener.dungeon_crawler.items.Item;
import net.eckener.dungeon_crawler.items.Weapon;

/**
 * Stores an {@link Item} or a {@link Weapon} and an amount
 */
public class ItemStack {


    private Item item;
    private int amount;

    public ItemStack(Item pItem, int pAmount) {
        item = pItem;
        amount = pAmount;
    }

    /**
     * @return if the ItemStack actually contains anything
     */
    public boolean isEmpty() {
        return item == null || amount == 0;
    }

    /**
     * @return the {@link Item} stored in the ItemStack
     */
    public Item getItem() {
        return item;
    }

    /**
     * @return the amount of {@link Item}s stored
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param pAmount sets the amount of {@link Item}s stored
     */
    public void setAmount(int pAmount) {
        amount = pAmount;
    }

    /**
     * @return the {@link Weapon} stored in the ItemStack
     */
    public boolean isWeapon() {
        return item instanceof Weapon;
    }

    /**
     * @return if the stored {@link Item} is just an {@link Item} or actually a {@link Weapon}
     */
    public Weapon getWeapon() {
        return item instanceof Weapon ? (Weapon) item : null;
    }
}
