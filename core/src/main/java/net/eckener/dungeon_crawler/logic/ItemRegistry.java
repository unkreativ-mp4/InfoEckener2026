package net.eckener.dungeon_crawler.logic;

import net.eckener.dungeon_crawler.items.Bow;
import net.eckener.dungeon_crawler.items.HealingPotion;
import net.eckener.dungeon_crawler.items.Item;
import net.eckener.dungeon_crawler.items.Maul;

import java.util.HashMap;
import java.util.Map;

public final class ItemRegistry {

    private static final Map<String, Item> items = new HashMap<>();

    public static void loadItems() {
        items.put("bow", new Bow("bow", "Bow", Assets.get(Assets.DARK_BOW), 1,100,2));
        items.put("maul",new Maul("maul", "Maul", Assets.get(Assets.WOODEN_SWORD), 1,5,2,4));
        items.put("coin", new Item("coin", "Coin", Assets.get(Assets.COIN), 67));
        items.put("lesser_healing_potion", new HealingPotion("lesser_healing_potion", "Lesser Healing Potion", Assets.get(Assets.LESSER_HEALING_POTION), 3, 10));
        items.put("normal_healing_potion", new HealingPotion("normal_healing_potion", "Normal Healing Potion", Assets.get(Assets.NORMAL_HEALING_POTION), 3, 25));
        items.put("greater_healing_potion", new HealingPotion("greater_healing_potion", "Greater Healing Potion", Assets.get(Assets.GREATER_HEALING_POTION), 3, 50));
    }

    public static void addItem(Item item) {
        items.put(item.getItemID(),  item);
    }

    public static Item getItemFromID(String id) {
        return items.get(id);
    }
}
