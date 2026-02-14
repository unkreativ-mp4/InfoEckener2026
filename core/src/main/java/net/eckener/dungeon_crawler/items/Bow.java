package net.eckener.dungeon_crawler.items;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.eckener.dungeon_crawler.Assets;
import net.eckener.dungeon_crawler.entities.Arrow;
import net.eckener.dungeon_crawler.entities.Enemy;
import net.eckener.dungeon_crawler.entities.Player;

/**
 * Simple bow {@link Weapon} which can shoot {@link Arrow}s
 */
public class Bow extends Weapon{

    public Bow(String pItemID, String pItemName, Texture pItemTexture, int pRarity, int pMaxStackSize, int damage, float cooldownModifier) {
        super(pItemID, pItemName, pItemTexture, pRarity, pMaxStackSize, damage, cooldownModifier);
    }

    /**
     * Spawns an arrow at {@link Player}'s position facing the cursor
     * @param player The {@link Player} at which to spawn the {@link Arrow}
     * @param enemy An {@link Enemy} that is only passed because the {@link Weapon} class requires it to; isn't used
     * @param viewport {@link Viewport} so the orientation of the arrow works correctly
     */
    @Override
    public void attack(Player player, Enemy enemy, Viewport viewport) {
        Arrow arrow = new Arrow(Assets.get(Assets.ARROW), player.getxPos(), player.getyPos());
        arrow.setRotationToFaceCursor((OrthographicCamera) viewport.getCamera());
    }
}
