package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;

/**
 * Stores an {@link Item} or a {@link Weapon} and an amount
 */
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
    public Weapon getWeapon() {
        return weapon;
    }

    /**
     * @return if the stored {@link Item} is just an {@link Item} or actually a {@link Weapon}
     */
    public boolean isWeapon() {
        return weapon != null;
    }
}
