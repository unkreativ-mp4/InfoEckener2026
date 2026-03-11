package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import net.eckener.dungeon_crawler.logic.Assets;
import net.eckener.dungeon_crawler.logic.EntityRegistry;
import net.eckener.dungeon_crawler.logic.ItemStack;
import net.eckener.dungeon_crawler.items.Weapon;
import net.eckener.dungeon_crawler.logic.Inventory;
import net.eckener.dungeon_crawler.ui.Hotbar;
import net.eckener.dungeon_crawler.logic.AnimationLoader;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import net.eckener.dungeon_crawler.logic.EntityDirection;

import java.util.EnumMap;


import static net.eckener.dungeon_crawler.Main.camera;
import static net.eckener.dungeon_crawler.Main.stage;

public class Player extends LivingEntity{
    private int maxMana;
    private int mana;
    private final Hotbar hotbar;
    private final Inventory inventory;
    private float timeSinceLastDamage;
    private float timeSinceLastAttack;
    private float timeSinceLastManaRegen;
    private final float baseDamageCooldown = 0.5F;
    private final float baseAttackCooldown = 1.0f;
    private ItemStack selectedItem;
    private boolean isMoving = false;
    private final EnumMap<EntityDirection, Animation<TextureRegion>> walkAnimations;


    public Player(int maxHealth, int maxMana) {
        super(1,1, Assets.get(Assets.PLAYER_DOWN + "frame_0.png"), maxHealth,2);
        this.maxMana = maxMana;

        inventory = new Inventory(4, 7, "Inventory");
        inventory.getInventoryUI().setPosition(
            (stage.getWidth() - inventory.getInventoryUI().getWidth()) / 2f,
            (stage.getHeight() - inventory.getInventoryUI().getHeight())
        );
        hotbar = new Hotbar();
        selectedItem = hotbar.getInventory().getItemStack(0, 0);

        walkAnimations = new EnumMap<>(EntityDirection.class);
        // load animations
        walkAnimations.put(EntityDirection.UP, AnimationLoader.load(Assets.PLAYER_UP, 14, 0.08f));
        walkAnimations.put(EntityDirection.DOWN, AnimationLoader.load(Assets.PLAYER_DOWN, 14, 0.08f));
        walkAnimations.put(EntityDirection.LEFT, AnimationLoader.load(Assets.PLAYER_LEFT, 14, 0.08f));
        walkAnimations.put(EntityDirection.RIGHT, AnimationLoader.load(Assets.PLAYER_RIGHT, 14, 0.08f));

        TextureRegion firstFrame = walkAnimations.get(EntityDirection.DOWN).getKeyFrame(0);
    }

    /**
     * Player movement depending on user input
     */
    public void move() {
        boolean matched = false;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            //setTexture(Assets.get(Assets.PLAYER_UP));
            direction.add(0,1);
            matched = true;
            facing = EntityDirection.UP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            //setTexture(Assets.get(Assets.PLAYER_LEFT));
            direction.add(-1,0);
            matched = true;
            facing = EntityDirection.LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            //setTexture(Assets.get(Assets.PLAYER_DOWN));
            direction.add(0,-1);
            matched = true;
            facing = EntityDirection.DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            //setTexture(Assets.get(Assets.PLAYER_RIGHT));
            direction.add(1,0);
            matched = true;
            facing = EntityDirection.RIGHT;
        }
        isMoving = matched;
        if (matched) {
            direction.nor().scl(speed - momentum.len());
            momentum.add(direction);
            direction.setZero();
        }
    }

    /**Hurts the Player but respects I-frames
     * @param damage the amount of {@code health} getting removed
     */
    @Override
    public void takeDamage(int damage) {
        if(timeSinceLastDamage < baseDamageCooldown) {return;}
        health = Math.max(0, health - damage);
        timeSinceLastDamage = 0;
    }

    /**
     * waiting for implementation
     */
    @Override
    protected void onDeath() {}

    /**
     * Changes the {@code mana} the player has. Also works with negative amounts
     * @param mana amount of {@code mana} to add/subtract
     */
    public void addMana(int mana) {
        if(mana >= 0) {
            this.mana = Math.min(maxMana, this.mana += mana);
        } else {
            this.mana = Math.max(0, this.mana += mana);
        }
    }

    /**
     * @return how much mana the Player has left in percent
     */
    public float getManaPercent() {
        return (float) mana / maxMana;
    }

    /**
     * @return how much health the Player has left in percent
     */
    public float getHealthPercent() {
        return (float) health / maxHealth;
    }

    /**
     * Attacks an {@link LivingEntity} with the selected {@link Weapon}
     * @param livingEntity the {@link LivingEntity} which to attack
     * @param weapon the {@link Weapon} with which to attack
     */
    public void attackSelective(LivingEntity livingEntity, Weapon weapon) {
        weapon.attack(this, livingEntity);
        timeSinceLastAttack = 0;
    }

    /**
     * Checks if the player can attack and what the weapon type is
     * <p>
     *     If the weapon is of type AOE, and is of type melee the {@code attackSelective()} method is called for all LivingEntities in weapon-range
     * <p>
     *     If the weapon is of type AOE, and not of type melee the {@code attackSelective()} method is called only for each livingEntity with {@code livingEntity = null}
     * <p>
     *     If the weapon is not of type AOE and is of type melee the {@code attackSelective()} method is called for the livingEntity under the cursor if it exists and is in weapon-range
     *<p>
     *     If the weapon is not of type AOE and not of type melee the {@code attackSelective()} method is called only once with {@code livingEntity = null}
     */
    public void attack() {
        int killsThisAttack = 0;
        ItemStack weaponSlotStack = hotbar.getInventory().getItemStack(0, 0);

        if (weaponSlotStack == null || weaponSlotStack.getItem() == null) {
            return;
        }

        if (!(weaponSlotStack.getItem() instanceof Weapon)) {
            return;
        }

        if (timeSinceLastAttack <= baseAttackCooldown) {
            return;
        }

        Weapon weapon = (Weapon) weaponSlotStack.getItem();

        if(selectedItem.getWeapon().isAOEWeapon()) {
            if (selectedItem.getWeapon().isMeleeWeapon()) {
                //aoe und melee
                for (LivingEntity livingEntity : EntityRegistry.getAllRoomLivingEntities()) {
                    if (!(livingEntity instanceof Player) && Math.pow(getX() - livingEntity.getX(), 2) + Math.pow(getY() - livingEntity.getY(), 2) <= selectedItem.getWeapon().getRange()) {
                        attackSelective(livingEntity, weapon);
                    }
                }

            } else {
                //aoe und ranged
                for (LivingEntity livingEntity : EntityRegistry.getAllRoomLivingEntities()) {
                    if (!(livingEntity instanceof Player)) {
                        attackSelective(null, weapon);
                    }
                }

            }
        } else {
            if (selectedItem.getWeapon().isMeleeWeapon()) {
                //direct und melee
                Vector3 vector3 = new Vector3();
                camera.unproject(vector3.set(Gdx.input.getX(), Gdx.input.getY(), 0));
                for(LivingEntity livingEntity : EntityRegistry.getAllRoomLivingEntities()) {
                    if(livingEntity instanceof Player) {continue;}

                    if(vector3.x -0.5 <= livingEntity.getX()+livingEntity.getWidth()/2 && livingEntity.getX()+livingEntity.getWidth()/2 <= vector3.x + 0.5 && vector3.y -0.5 <= livingEntity.getY()+livingEntity.getHeight()/2 && livingEntity.getY()+livingEntity.getHeight()/2 <= vector3.y + 0.5&& Math.pow(getX() - livingEntity.getX(), 2) + Math.pow(getY() - livingEntity.getY(), 2) <= selectedItem.getWeapon().getRange()) {

                        attackSelective(livingEntity, weapon);
                        break;
                    }
                }
            } else {
                //direct und ranged
                attackSelective(null, weapon);
            }
        }


    }

    /**
     * @param mana sets the amount of {@code mana} the Player has
     */
    public void setMana(int mana) {
        this.mana = mana;
    }

    /**
     * @return the amount of {@code mana} the Player has
     */
    public int getMana() {
        return mana;
    }

    /**
     * Runs every frame and increases {@code timeSince} attributes among other things, also checks for some animation things
     * @param deltaTime Frame time to satisfy smooth updating even when lagging
     */
    @Override
    public void update(float deltaTime) {
        timeSinceLastDamage += deltaTime;
        timeSinceLastAttack += deltaTime;
        timeSinceLastManaRegen += deltaTime;
        move();

        selectedItem = hotbar.getInventory().getItemStack(0,0);
        hotbar.getInventoryUI().rebuildAllOpenInventories();

        if(timeSinceLastManaRegen >= 5) {
            addMana(10);
            timeSinceLastManaRegen = 0f;
        }



        // Determine which animation should be active
        Animation<TextureRegion> newAnim = walkAnimations.get(facing);

        // Switch animation only if the animation object actually changed
        if (currentAnimation != newAnim) {
            setCurrentAnimation(newAnim); // resets stateTime only when switching direction
        }

        // Advance the animation timer
        updateAnimation(deltaTime);

        // Optional: handle stopping movement by showing the first frame
        if (!isMoving && currentAnimation != null) {
            stateTime = currentAnimation.getAnimationDuration(); // stays on first frame
        }
    }

    /**
     * Never use, because it makes no sense
     * @param delta Frame time stuff
     * @param player could honestly just be {@code this}
     */
    @Override
    public void update(float delta, Player player) {
    }

    /**
     * @return the Player's Inventory
     */
    public Inventory getPlayerInventory() {
        return inventory;
    }

    /**
     * @return the Player's Hotbar Inventory
     */
    public Hotbar getPlayerHotbar() {return hotbar;}

    /**
     * @return the currently selected Item in form of an ItemStack
     */
    public ItemStack getSelectedItem() {
        return selectedItem;
    }

}

