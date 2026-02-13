package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Enemy extends LivingEntity {

    public Enemy(float xPos, float yPos, int maxHealth, Texture aliveTexture, int speed) {
        super(xPos,yPos, aliveTexture,maxHealth, speed);
    }

    public abstract void update(float deltaTime, Player player);

    public abstract void attack(Player player);
    public abstract void attack(Enemy enemy);

}
