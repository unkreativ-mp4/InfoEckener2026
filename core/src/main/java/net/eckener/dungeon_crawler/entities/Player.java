package net.eckener.dungeon_crawler.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import net.eckener.dungeon_crawler.items.Bow;
import net.eckener.dungeon_crawler.logic.*;
import net.eckener.dungeon_crawler.items.Maul;
import net.eckener.dungeon_crawler.items.Weapon;
import net.eckener.dungeon_crawler.ui.Hotbar;

public class Player extends LivingEntity{
    private int maxMana;
    private int mana;
    private Hotbar hotbar;
    private Inventory inventory;
    private float timeSinceLastDamage;
    private float timeSinceLastAttack;
    private final float baseDamageCooldown = 0.5F;
    private final float baseAttackCooldown = 1.0f;  //hotbar.getInventory().getItemStack(1,1).getItem().isWeapon().getCooldownModifier();
    private ItemStack selectedItem;
    private int killcount;

    //temporär für Projektabgabe
    private Stage stage;
    private LootTable chestLootTable;
    private Chest spawnedChest;


    public Player(int maxHealth, int maxMana, Stage stage) {
        super(1,1, Assets.get(Assets.PLAYER_DOWN), maxHealth,2);
        this.maxMana = maxMana;

        inventory = new Inventory(4, 7, "Inventory", stage);
        hotbar = new Hotbar(stage);
        selectedItem = hotbar.getInventory().getItemStack(0, 0);
        this.stage = stage;

    }


    /**
     * Player movement depending on user input
     */
    public void move() {
        boolean matched = false;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            setTexture(Assets.get(Assets.PLAYER_UP));
            direction.add(0,1);
            matched = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            setTexture(Assets.get(Assets.PLAYER_LEFT));
            direction.add(-1,0);
            matched = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
            setTexture(Assets.get(Assets.PLAYER_DOWN));
            direction.add(0,-1);
            matched = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            setTexture(Assets.get(Assets.PLAYER_RIGHT));
            direction.add(1,0);
            matched = true;
        }
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

    public boolean attackSelective(LivingEntity livingEntity, Weapon weapon) {
        if (livingEntity == null) {
            weapon.attack(this, null);
            timeSinceLastAttack = 0;
            return false;
        }

        int healthBefore = livingEntity.getHealth();

        weapon.attack(this, livingEntity);
        timeSinceLastAttack = 0;

        return healthBefore > 0 && livingEntity.getHealth() <= 0;
    }

    /**
     * Checks if the player can attack and what the weapon type is
     * <p>
     * If the weapon is of type melee, all LivingEntities in weapon-range are attacked individually
     * <p>
     * If the weapon is not of type melee, the {@code attackSelective()} method is called only once with {@code livingEntity = null}
     */
    public int attack() {
        int killsThisAttack = 0;

        ItemStack weaponSlotStack = hotbar.getInventory().getItemStack(0, 0);

        if (weaponSlotStack == null || weaponSlotStack.getItem() == null) {
            System.out.println("Hotbar slot 1 is empty");
            return 0;
        }

        if (!(weaponSlotStack.getItem() instanceof Weapon weapon)) {
            System.out.println("Hotbar slot 1 item is not a weapon: " + weaponSlotStack.getItem().getItemName());
            return 0;
        }

        if (timeSinceLastAttack <= baseAttackCooldown) {
            return 0;
        }

        if (selectedItem.getWeapon().isMeleeWeapon()) {
            for (LivingEntity livingEntity : EntityRegistry.getAllRoomLivingEntities()) {
                if (!(livingEntity instanceof Player)
                    && Math.pow(getX() - livingEntity.getX(), 2) + Math.pow(getY() - livingEntity.getY(), 2)
                    <= selectedItem.getWeapon().getRange()) {

                    if (attackSelective(livingEntity, weapon)) {
                        handleKillReward();
                        killsThisAttack++;
                    }
                }
            }
        } else {
            attackSelective(null, weapon);
        }

        return killsThisAttack;
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
     * Runs every frame and increases {@code timeSince} attributes among other things
     * @param deltaTime Frame time to satisfy smooth updating even when lagging
     */
    @Override
    public void update(float deltaTime) {
        timeSinceLastDamage += deltaTime;
        timeSinceLastAttack += deltaTime;
        move();
        selectedItem = hotbar.getInventory().getItemStack(0,0);
    }

    /**
     * Never use, because it makes no sense
     * @param delta Frame time stuff
     * @param player could honestly just be {@code this}
     */
    @Override
    public void update(float delta, Player player) {
    }

    public Inventory getPlayerInventory() {
        return inventory;
    }

    public Hotbar getPlayerHotbar() {return hotbar;}

    public ItemStack getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(ItemStack pSelectedItem) {
        selectedItem = pSelectedItem;
    }

    public void setChestLootTable(LootTable lootTable) {
        this.chestLootTable = lootTable;
    }

    public Chest getSpawnedChest() {
        return spawnedChest;
    }

    public void handleKillReward() {
        killcount++;
        if (chestLootTable != null) {
            int i = MathUtils.random(0, 99);
            if(i < 10) {
                if (spawnedChest != null) {
                    spawnedChest.remove();
                }
                spawnedChest = new Chest(getX(), getY(), stage, chestLootTable);
            }

        }
    }

}

