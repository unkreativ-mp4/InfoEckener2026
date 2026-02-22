package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Arrow projectile
 */

public class Arrow extends Projectile{

    Vector2 directionVector = new Vector2();
    Entity owner;

    public Arrow(Texture texture, float xPos, float yPos, Entity owner) {
        super(texture, xPos, yPos, 5);
        this. owner = owner;
    }

    /**
     * Sets the rotation of the {@link com.badlogic.gdx.graphics.g2d.Sprite} to face the cursor
     * @param camera {@link OrthographicCamera} is needed to correctly set the rotation (I think). Best acquired from a {@link com.badlogic.gdx.utils.viewport.Viewport}
     */
    public void setRotationToFaceCursor(OrthographicCamera camera){
        Vector3 vector3 = new Vector3();
        camera.unproject(vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        float cx = sprite.getX() + sprite.getWidth() * 0.5f;
        float cy = sprite.getY() + sprite.getHeight() * 0.5f;

        float dx = vector3.x - cx;
        float dy = vector3.y - cy;

        direction = (float) Math.toDegrees(Math.atan2(dy, dx));
        sprite.setOriginCenter();
        sprite.setRotation(direction);
    }

    /**
     * Sets the rotation of the {@link com.badlogic.gdx.graphics.g2d.Sprite} to face a {@link LivingEntity}
     * @param livingEntity the {@link LivingEntity} which to face
     */
    public void setRotationToFaceLivingEntity(LivingEntity livingEntity){
        Vector2 vector2 = new Vector2(livingEntity.getxPos() - getxPos(), livingEntity.getyPos() - getyPos());
        sprite.setOriginCenter();
        direction = vector2.angleDeg();
        sprite.setRotation(direction);
    }

    /**
     * Detects if the arrow has hit another {@link LivingEntity} and then unregisters itself
     */
    private void hitDetection(){
        for(LivingEntity livingEntity : EntityRegistry.getAllLivingEntities()){
            if (Intersector.overlapConvexPolygons(this.hitbox, livingEntity.hitbox) && !livingEntity.equals(owner)) {
                livingEntity.takeDamage(10);
                EntityRegistry.unregister(this);
                break;
            }
        }
    }

    /**
     * Moves the arrow in the direction it's facing
     * @param deltaTime Frametime to satisfy smooth movement even when lagging
     */

    @Override
    public void move(float deltaTime) {
        directionVector.set(1,1);
        directionVector.setAngleDeg(direction);
        sprite.translate(directionVector.nor().scl(deltaTime*speed).x, directionVector.nor().scl(deltaTime*speed).y);
        hitDetection();
    }

    /**
     * Called every frame to update the Arrow
     * @param deltaTime Frametime to satisfy smooth updating even when lagging
     */

    @Override
    public void update(float deltaTime) {
        move(deltaTime);
    }

    /**
     * Called every frame to update the Arrow, but this time the Player is passed
     * @param deltaTime Frametime to satisfy smooth updating even when lagging
     * @param player Here not used
     */

    @Override
    public void update(float deltaTime, Player player) {    }
}
