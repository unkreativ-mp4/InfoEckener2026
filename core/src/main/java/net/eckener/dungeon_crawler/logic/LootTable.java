package net.eckener.dungeon_crawler.logic;

import com.badlogic.gdx.math.MathUtils;
import net.eckener.dungeon_crawler.items.Item;

import java.util.ArrayList;
import java.util.List;

public class LootTable {

    private final List<LootEntry> entries = new ArrayList<>();
    private int totalWeight = 0;

    public LootTable add(Item item, int minAmount, int maxAmount, int weight) {
        LootEntry entry = new LootEntry(item, minAmount, maxAmount, weight);
        entries.add(entry);
        totalWeight += entry.weight();
        return this;
    }

    public LootEntry rollEntry() {
        int r = MathUtils.random(1, totalWeight);
        int acc = 0;
        for (LootEntry entry : entries) {
            acc += entry.weight();
            if (r <= acc) return entry;
        }
        return entries.get(entries.size() - 1); // fallback
    }

    public int rollAmount(LootEntry entry) {
        return MathUtils.random(entry.minAmount(), entry.maxAmount());
    }

}
