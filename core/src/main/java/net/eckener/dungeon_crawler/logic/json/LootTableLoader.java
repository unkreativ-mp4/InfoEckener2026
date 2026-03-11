package net.eckener.dungeon_crawler.logic.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import net.eckener.dungeon_crawler.logic.ItemRegistry;
import net.eckener.dungeon_crawler.logic.LootTable;

public class LootTableLoader {

    /**
     * Loads a lootTable from the assets/loot_tables/ directory
      * @param lootTablePath the path to the lootTable JSON
     * @return the loaded lootTable
     */
    public static LootTable loadLootTable(String lootTablePath) {

        Json json = new Json();

        FileHandle file = Gdx.files.internal("loot_tables/"+lootTablePath);

        LootTableDefinition def = json.fromJson(LootTableDefinition.class, file);
        LootTable lootTable = new LootTable();

        for (LootEntryDefinition entry : def.entries) {
            lootTable.add(ItemRegistry.getItemFromID(entry.itemID) , entry.min, entry.max, entry.weight);
        }
        return lootTable;
    }
}
