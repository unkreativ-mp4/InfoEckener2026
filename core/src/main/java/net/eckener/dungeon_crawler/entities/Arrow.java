package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static net.eckener.dungeon_crawler.Main.camera;

/**
 * Arrow projectile
 */

public class Arrow extends Projectile{

    Entity owner;

    public Arrow(Texture texture, float xPos, float yPos, Entity owner) {
        super(texture, xPos, yPos, 5);
        this.owner = owner;
    }

    /**
     * Sets the rotation of the {@link Sprite} to face the cursor
     */
    public void setRotationToFaceCursor(){
        Vector3 vector3 = new Vector3();
        camera.unproject(vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        float cx = getX() + getWidth() * 0.5f;
        float cy = getY() + getHeight() * 0.5f;

        float dx = vector3.x - cx;
        float dy = vector3.y - cy;

        directionDeg = (float) Math.toDegrees(Math.atan2(dy, dx));
        setOriginCenter();
        setRotation(directionDeg);
    }

    /**
     * Sets the rotation of the {@link com.badlogic.gdx.graphics.g2d.Sprite} to face a {@link LivingEntity}
     * @param livingEntity the {@link LivingEntity} which to face
     */
    public void setRotationToFaceLivingEntity(LivingEntity livingEntity){
        Vector2 vector2 = new Vector2(livingEntity.getX() - getX(), livingEntity.getY() - getY());
        setOriginCenter();
        directionDeg = vector2.angleDeg();
        setRotation(directionDeg);
    }

    /**
     * Detects if the arrow has hit another {@link LivingEntity} and then unregisters itself
     */
    private void hitDetection(){
        for(LivingEntity livingEntity : EntityRegistry.getAllRoomLivingEntities()){
            if (Intersector.overlapConvexPolygons(this.getHitbox(), livingEntity.getHitbox()) && !livingEntity.equals(owner)) {
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
        direction.set(1,1);
        direction.setAngleDeg(directionDeg);
        direction.nor().scl(speed - momentum.len());
        momentum.add(direction);
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
