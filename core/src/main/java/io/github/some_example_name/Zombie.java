package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Zombie extends Enemy {
    private static final int baseHealth = 20;
    private static final int baseDamage = 3;

    private double attackCooldown = 1.5;
    private double timeSinceLastAttack;

    public Zombie(int xPos, int yPos, Texture texture) {
        super(xPos, yPos, baseHealth, texture);
        this.timeSinceLastAttack = 0;
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public void update(float deltaTime) {
        timeSinceLastAttack += deltaTime;
        //System.out.println(timeSinceLastAttack);
    }

    @Override
    public void attack(Player player) {
        if(timeSinceLastAttack < attackCooldown) {return;}
        double distance = Math.sqrt(   Math.pow(EnemySprite.getX()- player.getX(),2)   + Math.pow(EnemySprite.getY()- player.getY(),2)    );
        if(distance <= 5) {
            player.addHealth(-baseDamage);
        }
        timeSinceLastAttack = 0;
    }

    @Override
    public void attack(Enemy enemy) {
        if(timeSinceLastAttack < attackCooldown) {return;}
        double distance = Math.sqrt(   Math.pow(this.EnemySprite.getX()- enemy.xPos,2)   + Math.pow(this.EnemySprite.getY()- enemy.yPos,2)    );
        if(distance <= 5) {
            enemy.takeDamage(baseDamage);
        }
        timeSinceLastAttack = 0;
    }

    private boolean canAttack() {
        return timeSinceLastAttack >= attackCooldown && isAlive();
    }
}
