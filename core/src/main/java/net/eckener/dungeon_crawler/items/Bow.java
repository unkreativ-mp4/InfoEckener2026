package net.eckener.dungeon_crawler.items;

import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.logic.Assets;
import net.eckener.dungeon_crawler.entities.Arrow;
import net.eckener.dungeon_crawler.entities.LivingEntity;
import net.eckener.dungeon_crawler.entities.Player;
import net.eckener.dungeon_crawler.logic.ItemStack;

/**
 * Simple bow {@link Weapon} which can shoot {@link Arrow}s
 */
public class Bow extends Weapon{

    public Bow(String itemID, String itemName, Texture itemTexture, int maxStackSize, int damage, float cooldownModifier) {
        super(itemID, itemName, itemTexture, maxStackSize, damage, cooldownModifier, false,0);
    }

    /**
     * Spawns an arrow at {@link Player}'s position facing the cursor
     * @param attacker The {@link LivingEntity} at which to spawn the {@link Arrow}
     * @param attacked An {@link LivingEntity} that is only passed because the {@link Weapon} class requires it to; isn't used
     */
    @Override
    public void attack(LivingEntity attacker, LivingEntity attacked) {
        if(!(attacker instanceof Player player)) {
            return;
        }

        if(player.getPlayerHotbar().getInventory().containsItemType(ArrowItem.class)) {
            ItemStack stack = player.getPlayerHotbar().getInventory().findItemStack(ArrowItem.class);
            if (stack.getAmount() > 1) {
                stack.setAmount(stack.getAmount() - 1);
            } else {
                player.getPlayerHotbar().getInventory().removeItemStack(player.getPlayerHotbar().getInventory().findItemStackPosition(stack)[0], player.getPlayerHotbar().getInventory().findItemStackPosition(stack)[1]);
            }
            Arrow arrow = new Arrow(Assets.get(Assets.ARROW), attacker.getX(), attacker.getY(), attacker, this.getDamage());
            arrow.setRotationToFaceCursor();
        }
    }
}
