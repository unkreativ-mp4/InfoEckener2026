package net.eckener.dungeon_crawler.items;

import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.entities.Arrow;
import net.eckener.dungeon_crawler.entities.LivingEntity;
import net.eckener.dungeon_crawler.entities.Player;
import net.eckener.dungeon_crawler.logic.Assets;

public class Wand extends Weapon{

    private final int manaCost;

    public Wand(String itemID, String itemName, Texture itemTexture, int maxStackSize, int damage, float cooldownModifier, int pManaCost) {
        super(itemID, itemName, itemTexture, maxStackSize, damage, cooldownModifier, false,0);
        this.manaCost = pManaCost;
    }

    @Override
    public void attack(LivingEntity attacker, LivingEntity attacked) {
        if(!(attacker instanceof Player player)) {
            return;
        }

        if(player.getMana() < manaCost)  {
            return;
        }

        player.addMana(-manaCost);
        Arrow arrow = new Arrow(Assets.get(Assets.FIREBALL), attacker.getX(), attacker.getY(),attacker, this.getDamage());
        arrow.setRotationToFaceCursor();
    }

    public int getManaCost() {
        return manaCost;
    }

}
