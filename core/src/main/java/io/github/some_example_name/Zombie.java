package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Zombie extends Enemy {
    private static final int baseHealth = 20;
    private static final int baseDamage = 3;

    private double attackCooldown;
    private double timeSinceLastAttack;

    public Zombie(int xPos, int yPos, Texture texture) {
        super(xPos, yPos, baseHealth, texture);
        this.timeSinceLastAttack = 0;
    }

    @Override
    protected void onDeath() {

    }

    @Override
    public void update() {
        timeSinceLastAttack += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void attack(Player player) {

    }

    @Override
    public void attack(Enemy enemy) {

    }

    private boolean canAttack() {
        return timeSinceLastAttack >= attackCooldown && isAlive();
    }
}
