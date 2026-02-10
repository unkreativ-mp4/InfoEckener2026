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
        super(xPos, yPos, baseHealth, aliveTexture);
        this.timeSinceLastAttack = 0;
        this.deathTexture = deathTexture;
        this.speed = 1;
    }

    @Override
    protected void onDeath() {
        isAlive=false;
        EnemySprite.setTexture(deathTexture);
    }

    @Override
    public void update(float deltaTime, Player player) {
        timeSinceLastAttack += deltaTime;
        xPos = EnemySprite.getX();
        yPos = EnemySprite.getY();
        move(player, deltaTime);
    }

    @Override
    public void attack(Player player) {
        if(canAttack()) {
            double distance = Math.sqrt(   Math.pow(EnemySprite.getX()- player.getX(),2)   + Math.pow(EnemySprite.getY()- player.getY(),2)    );
            if(distance <= 5) {
                player.addHealth(-baseDamage);
            }
            timeSinceLastAttack = 0;
        }

    }

    @Override
    public void attack(Enemy enemy) {
        if(canAttack()) {double distance = Math.sqrt(   Math.pow(this.EnemySprite.getX()- enemy.xPos,2)   + Math.pow(this.EnemySprite.getY()- enemy.yPos,2)    );
            if(distance <= 5) {
                enemy.takeDamage(baseDamage);
            }
            timeSinceLastAttack = 0;
        }
    }

    public void move(Player player, float delta) {
        float playerXPos = player.getX();
        float playerYPos = player.getY();

        direction.set(playerXPos - xPos, playerYPos - yPos);

        if (direction.len2() > 0f) {
            direction.nor().scl(speed * delta);
            EnemySprite.translate(direction.x, direction.y);
        }
    }

    private boolean canAttack() {
        return timeSinceLastAttack >= attackCooldown && isAlive();
    }
}
