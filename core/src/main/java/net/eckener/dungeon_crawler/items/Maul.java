package net.eckener.dungeon_crawler.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import net.eckener.dungeon_crawler.entities.LivingEntity;

public class Maul extends Weapon{
    public Maul(Texture pItemTexture) {
        super("maul", "Maul", pItemTexture, 100, 1, 10, 4, true, 4);
    }

    /**
     * Damages the attacked LivingEntity and adds knockback pointing away from the player
     * @param attacker the attacking LivingEntity
     * @param attacked the attacked LivingEntity
     */
    @Override
    public void attack(LivingEntity attacker, LivingEntity attacked) {
        Vector2 vector2 = new Vector2(attacked.getX() - attacker.getX(), attacked.getY() - attacker.getY());
        vector2.nor().scl(8f);
        attacked.addMomentum(vector2);
        attacked.takeDamage(getDamage());
    }
}
