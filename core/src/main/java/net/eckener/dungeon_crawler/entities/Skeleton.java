package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import net.eckener.dungeon_crawler.Assets;

public class Skeleton extends Enemy {

    private static final int baseHealth = 20;
    private static final int baseDamage = 3;

    private final Vector2 direction = new Vector2();

    public Skeleton(float xPos, float yPos, Texture aliveTexture) {
        super(xPos, yPos, baseHealth, aliveTexture, 1);
        attackCooldown = 3;
    }

    public void move(float deltaTime, Player player) {
        direction.set(player.getxPos() - getxPos(), player.getyPos() - getyPos());
        if (direction.len2() > 2f) {
            direction.nor().scl(speed * deltaTime);
            sprite.translate(direction.x, direction.y);
        } else if (direction.len2() > 0f) {
            direction.nor().scl(speed * deltaTime);
            sprite.translate(-direction.x, -direction.y);
        }

    }

    @Override
    public void update(float delta) {    }

    @Override
    public void update(float deltaTime, Player player) {
        if(isAlive) {
            timeSinceLastAttack += deltaTime;
            move(deltaTime,player);
            attack(player);
        }
    }

    @Override
    public void attack(LivingEntity livingEntity) {
        if(canAttack()) {
            Arrow arrow = new Arrow(Assets.get(Assets.ARROW), getxPos(), getyPos());
            arrow.setRotationToFaceLivingEntity(livingEntity);
            timeSinceLastAttack = 0;
        }
    }

    private boolean canAttack() {
        return timeSinceLastAttack >= attackCooldown;
    }

    @Override
    protected void onDeath() {
        isAlive=false;
        //sprite.setTexture(deathTexture);
    }
}
