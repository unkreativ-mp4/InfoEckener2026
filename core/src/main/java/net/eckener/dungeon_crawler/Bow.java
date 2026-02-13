package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Bow extends Weapon{

    public Bow(String pItemID, String pItemName, Texture pItemTexture, int pRarity, int pMaxStackSize, int damage, float cooldownModifier) {
        super(pItemID, pItemName, pItemTexture, pRarity, pMaxStackSize, damage, cooldownModifier);
    }

    @Override
    public void attack(Player player, Enemy enemy, Viewport viewport) {
        Arrow arrow = new Arrow(Assets.get(Assets.ARROW), player.getxPos(), player.getyPos(), (OrthographicCamera) viewport.getCamera());
        EntityRegistry.register(arrow);
        System.out.println("attacking arrow");
    }
}
