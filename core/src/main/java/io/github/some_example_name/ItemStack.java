package io.github.some_example_name;

public class ItemStack {
    private Item item;
    private int amount;

    public ItemStack(Item pItem, int pAmount) {
        item = pItem;
        amount = pAmount;
    }

    public boolean isEmpty() {
        if(item == null || amount == 0) {
            return true;
        }
        return false;
    }

    /*public void dontGoOverMaxStackSize() {
        if(amount > item.getMaxStackSize()) {

        }
    } */

}
