package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;

public class Zombie extends Enemy {
    private static final int baseHealth = 20;
    private static final int baseDamage = 3;
    private final Texture deathTexture;

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

    public void move(Player player, float delta) {
        float playerXPos = player.getX();
        float playerYPos = player.getY();

        float movementDelta = delta * speed;
        //System.out.print("Player X: "+ playerXPos+ "     Zombie X: "+xPos);
        if (playerXPos > xPos) {
            EnemySprite.translateX(movementDelta);
            //System.out.println("+x");
        } else {
            EnemySprite.translateX(-movementDelta);
            //System.out.println("-x");
        }
        if (playerYPos > yPos) {
            EnemySprite.translateY(movementDelta);
            //System.out.println("+y");
        } else {
            EnemySprite.translateY(-movementDelta);
            //System.out.println("-y");
        }

    }

    private boolean canAttack() {
        return timeSinceLastAttack >= attackCooldown && isAlive();
    }
}
