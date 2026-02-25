package net.eckener.dungeon_crawler.items;

import com.badlogic.gdx.graphics.Texture;
import net.eckener.dungeon_crawler.entities.LivingEntity;

public class Maul extends Weapon{
    public Maul(Texture pItemTexture) {
        super("maul", "Maul", pItemTexture, 3, 1, 10, 4);
    }

    @Override
    public void attack(LivingEntity attacker, LivingEntity attacked) {

    }
}
