package net.eckener.dungeon_crawler.logic.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import net.eckener.dungeon_crawler.logic.ItemRegistry;
import net.eckener.dungeon_crawler.logic.LootTable;

public class LootTableLoader {

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
