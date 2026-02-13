package net.eckener.dungeon_crawler;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Zombie extends Enemy {
    private static final int baseHealth = 20;
    private static final int baseDamage = 3;
    private final Texture deathTexture;
    private final Vector2 direction = new Vector2();

    private double attackCooldown = 1.5;
    private double timeSinceLastAttack;

    public Zombie(float xPos, float yPos, Texture aliveTexture, Texture deathTexture) {
        super(xPos, yPos, baseHealth, aliveTexture,1);
        this.timeSinceLastAttack = 0;
        this.deathTexture = deathTexture;
    }

    public void move(Player player, float delta) {
        if(isAlive) {
            direction.set(player.getxPos() - getxPos(), player.getyPos() - getyPos());

            if (direction.len2() > 0f) {
                direction.nor().scl(speed * delta);
                sprite.translate(direction.x, direction.y);
            }
        }
    }

    @Override
    public void attack(Player player) {
        if(canAttack()) {
            double distance = Math.sqrt(   Math.pow(sprite.getX()- player.getxPos(),2)   + Math.pow(sprite.getY()- player.getyPos(),2)    );
            if(distance <= 5) {
                player.takeDamage(baseDamage);
            }
            timeSinceLastAttack = 0;
        }

    }

    @Override
    public void attack(Enemy enemy) {
        if(canAttack()) {double distance = Math.sqrt(   Math.pow(this.sprite.getX()- enemy.getxPos(),2)   + Math.pow(this.sprite.getY()- enemy.getyPos(),2)    );
            if(distance <= 5) {
                enemy.takeDamage(baseDamage);
            }
            timeSinceLastAttack = 0;
        }
    }

    private boolean canAttack() {
        return timeSinceLastAttack >= attackCooldown && isAlive();
    }

    @Override
    protected void onDeath() {
        isAlive=false;
        sprite.setTexture(deathTexture);
    }

    @Override
    public void update(float deltaTime, Player player) {
        timeSinceLastAttack += deltaTime;
        move(player, deltaTime);
    }

    @Override
    public void update(float delta) {}
}
