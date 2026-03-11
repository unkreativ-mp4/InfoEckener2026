package net.eckener.dungeon_crawler.logic;

import net.eckener.dungeon_crawler.items.*;

import java.util.HashMap;
import java.util.Map;

public final class ItemRegistry {

    private static final Map<String, Item> items = new HashMap<>();

    /**
     * Called once upon game start and fills the hashMap with items.
     * <p>
     *     The HashMap String must match the itemID!
     */
    public static void loadItems() {
        items.put("bow", new Bow("bow", "Bow", Assets.get(Assets.DARK_BOW), 1,100,2));
        items.put("maul",new Maul("maul", "Maul", Assets.get(Assets.WOODEN_SWORD), 1,5,2,4));
        items.put("coin", new Item("coin", "Coin", Assets.get(Assets.COIN), 67));
        items.put("lesser_healing_potion", new HealingPotion("lesser_healing_potion", "Lesser Healing Potion", Assets.get(Assets.LESSER_HEALING_POTION), 3, 10));
        items.put("normal_healing_potion", new HealingPotion("normal_healing_potion", "Normal Healing Potion", Assets.get(Assets.NORMAL_HEALING_POTION), 3, 25));
        items.put("greater_healing_potion", new HealingPotion("greater_healing_potion", "Greater Healing Potion", Assets.get(Assets.GREATER_HEALING_POTION), 3, 50));
        items.put("vampire_dagger", new VampireDagger("vampire_dagger", "Vampire Dagger", Assets.get(Assets.DIAMOND_SWORD),1,5,0.5f,false,2));
        items.put("wand", new Wand("wand", "Wand", Assets.get(Assets.WAND), 1, 50, 2, 10));
        items.put("arrow", new ArrowItem("arrow", "Arrow", Assets.get(Assets.ARROW), 64));
    }

    /**
     * @param item Adds an Item to the HashMap
     */
    public static void addItem(Item item) {
        items.put(item.getItemID(),  item);
    }

    /**
     * Retrieves an Item from the HashMap by its {@code itemID}
     * @param id the itemID of the wanted Item
     * @return the wanted Item
     */
    public static Item getItemFromID(String id) {
        return items.get(id);
    }
}
