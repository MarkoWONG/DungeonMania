package dungeonmania;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Weapon;
import dungeonmania.entity.collectables.buildable.BuildableEntity;
import dungeonmania.mobs.Hydra;
import dungeonmania.mobs.Mob;
import dungeonmania.mobs.Subscriber;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class PlayerCharacter extends Entity {

    private ArrayList<CollectableEntity> inventory;
    private ArrayList<Mob> allies;
    private ArrayList<Subscriber> subscribers;
    private Integer Health;
    private Integer startingHealth;
    private Integer attackDamage;
    private Integer invincibleTicks;
    private Integer invisibleTicks;

    public PlayerCharacter(Position position, int health, int ad) {
        super(new Position(position.getX(), position.getY(),50));
        this.inventory = new ArrayList<>();
        this.allies = new ArrayList<>();
        this.subscribers = new ArrayList<>();
        this.Health = health;
        this.startingHealth = health;
        this.attackDamage = ad;
        this.invisibleTicks = 0;
        this.invincibleTicks = 0;
    }

    /**
     * Make an entity an observer of the character
     * @param s The entity to observe the PlayerCharacter object
     */
    public void addSubscriber(Subscriber s) {
        subscribers.add(s);
    }

    /**
     * Replace the player character's inventory with a new one
     * Used primarily by persistence
     * @param newInv An arraylist of CollectableEntities
     */
    public void replaceInventory(ArrayList<CollectableEntity> newInv) {
        this.inventory = newInv;
    }

    /**
     * Add a collectable entity to the inventory
     * @param item The collectable entity to be added
     */
    public void addItemToInventory(CollectableEntity item) {
        inventory.add(item);
        item.setPosition(null);
    }

    /**
     * Remove a collectable entity from the inventory by the actual instance
     * @param item The collectable entity instance to be removed
     */
    public void removeItemFromInventory(CollectableEntity item) {
        inventory.remove(item);
    }

    /**
     * Remove an item from the inventory by it's ID
     * @param id The unique ID of the item to be removed
     */
    public void removeItemFromInventory(String id) {
        for(int i = inventory.size() - 1; i >= 0; --i) {
            CollectableEntity current = inventory.get(i);
            if (inventory.get(i).getId().equals(id)) {
                inventory.remove(current);
            }
        }
    }

    @Override
    public void move(Direction direction) {
        setPosition(getPosition().translateBy(direction));
        for(Subscriber s: subscribers) {
            s.notifyMove(super.getPosition());
        }
    }

    /**
     * Notify all subscribers of this object's position, as per the start of the game
     */
    public void startGame() {
        for(Subscriber s: subscribers) {
            s.notifyMove(super.getPosition());
        }
    }


    /**
     * Given a list of strings representing items, where all are known to be items in the inventory, remove them from the inventory
     * @param items A list of item type strings representing items to be removed
     */
    public void consume(List<String> items) {
        ArrayList<String> itemsTBD = new ArrayList<>();
        for (String eachString : items) {
            for (CollectableEntity eachItem : inventory) {
                if (eachItem.getType().equals(eachString) && !itemsTBD.contains(eachItem.getId())) {
                    itemsTBD.add(eachItem.getId());
                    break;
                }
            }
        }
        for (String eachItemTBD : itemsTBD) {
            removeItemFromInventory(eachItemTBD);
        }
    }

    /**
     * Get the buildable items that can be crafted as of right now
     * @param entities The entityList for the current dungeon
     * @return A list of strings of the type of each item that can be built
     */
    public List<String> getBuildables(EntityList entities) {
        return BuildableEntity.getBuildables(inventory, entities);
    }

    @Override
    public boolean canRevive() {
        for (CollectableEntity e : inventory) {
            if (e.getType().equals("one_ring")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void revive() {
        for (Iterator<CollectableEntity> iterator = inventory.iterator(); iterator.hasNext();){
            CollectableEntity currentEnt = iterator.next();
            if (currentEnt.getType().equals("one_ring")) {
                setHealth(startingHealth);
                iterator.remove();
            }
        }
    }

    @Override
    public void fight(Entity e) {
        ;
    }

    @Override
    public void fight(Mob mob) {
        if (mob.isEnemy()) {
            int mobAttack = mob.getAttackDamage() * mob.getHealth() / 10;
            mob.takeDamage(attack());
            takeDamage(mobAttack);
            for(Subscriber s: subscribers) {
                s.notifyFight(this.getPosition());
            }
        }
    }

    /**
     * Fight the hydra (boss)
     * @param hydra The hydra to be fought
     */
    public void fight(Hydra hydra) {
        if (hydra.isEnemy()) {
            int mobAttack = hydra.getAttackDamage() * hydra.getHealth() / 10;
            Boolean usingAnduril = hasAnduril();
            hydra.takeDamage(attackHydra(), usingAnduril);
            takeDamage(mobAttack);
            for(Subscriber s: subscribers) {
                s.notifyFight(getPosition());
            }
        }
    }

    /**
     * Calculate attack damage
     * @return the attack damage for the current fight
     */
    public int attack() {
        int AD = (int)getAttackDamage();
        ArrayList<CollectableEntity> weaponsUsed = new ArrayList<CollectableEntity>();

        for (CollectableEntity e : inventory) {
            if (e.getType().equals("anduril")) {
                AD = e.usedInAttack(AD);
                weaponsUsed.add(e);
                break;
            }
        }

        if (weaponsUsed.isEmpty()) {
            for (CollectableEntity e : inventory) {
                if (e.getType().equals("sword")) {
                    AD = e.usedInAttack(AD);
                    weaponsUsed.add(e);
                    break;
                }
            }
        }
        
        for (CollectableEntity e : inventory) {
            if (e.getType().equals("bow")) {
                AD = e.usedInAttack(AD);
                weaponsUsed.add(e);
                break;
            }
        }   
        for (CollectableEntity e : weaponsUsed) {
            if (e.usedInBattle(this)) {
                inventory.remove(e);
            }
        }

        for (Mob mob : allies) {
            AD += mob.getAttackDamage()/2;
        }
        return AD * getHealth() / 5;
    }

    /**
     * Calculate the attack damage for the current fight against a hydra
     * @return The attack damage against a hdyra
     */
    public int attackHydra() {
        int AD = (int)getAttackDamage();
        ArrayList<CollectableEntity> weaponsUsed = new ArrayList<CollectableEntity>();

        for (CollectableEntity e : inventory) {
            if (e.getType().equals("anduril")) {
                AD = e.usedInAttack(AD);
                weaponsUsed.add(e);
                break;
            }
        }

        if (weaponsUsed.isEmpty()) {
            for (CollectableEntity e : inventory) {
                if (e.getType().equals("sword")) {
                    AD = e.usedInAttack(AD);
                    weaponsUsed.add(e);
                    break;
                }
            }
        }

        for (CollectableEntity e : inventory) {
            if (e.getType().equals("midnight_armour")) {
                AD = e.usedInAttack(AD);
                weaponsUsed.add(e);
                break;
            }
        }

        for (CollectableEntity e : inventory) {
            if (e.getType().equals("bow")) {
                AD = e.usedInAttack(AD);
                weaponsUsed.add(e);
                break;
            }
        }   
        for (CollectableEntity e : weaponsUsed) {
            if (e.usedInBattle(this)) {
                inventory.remove(e);
            }
        }

        for (Mob mob : allies) {
            AD += mob.getAttackDamage();
        }
        return AD * getHealth() / 5;
    }

    /**
     * Take damage, after calculating reductions, and reducing the durability of applicable items
     * @param damage The original damage to be taken, before reductions
     */
    public void takeDamage(int damage) {
        ArrayList<CollectableEntity> typesUsed = new ArrayList<CollectableEntity>();
        int reducedDamage = damage;

        for (CollectableEntity e : inventory) {
            if (e.getType().equals("midnight_armour")) {
                reducedDamage = e.usedInDefense(reducedDamage);
                typesUsed.add(e);
                break;
            }
        }

        for (CollectableEntity e : inventory) {
            if (e.getType().equals("shield")) {
                reducedDamage = e.usedInDefense(reducedDamage);
                typesUsed.add(e);
                break;
            }
        }   
        for (CollectableEntity e : inventory) {
            if (e.getType().equals("armour")) {
                reducedDamage = e.usedInDefense(reducedDamage);
                typesUsed.add(e);
                break;
            }
        }

        for (CollectableEntity e : typesUsed) {
            if (e.usedInBattle(this)) {
                inventory.remove(e);
            }
        }
        if (invincibleTicks <= 0) {
            setHealth(getHealth() - reducedDamage);
        }
    }

    /**
     * @return Whether the PlayerCharacter has an Anduril sword in their inventory
     */
    public boolean hasAnduril() {
        for (CollectableEntity e : inventory) {
            if (e.getType().equals("anduril")) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    // Getter and Setters
    public ArrayList<CollectableEntity> getInventory() {
        return inventory;
    }


    /**
     * Find an item in the player's inventory by ID
     * @param id The ID of an item in the inventory
     * @return The CollectableEntity relating to the given ID, null if not found
     */
    public CollectableEntity getItemById(String id) {
        for (CollectableEntity eachEntity : inventory) {
            if (eachEntity.getId().equals(id)) {
                return eachEntity;
            }
        }
        return null;
    }

    /**
     * @return Whether the character possesses a weapon
     */
    public boolean hasWeapon() {
        for (CollectableEntity eachEntity : inventory) {
            if (eachEntity instanceof Weapon) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Mob> getAllies() {
        return this.allies;
    }

    @Override
    public void addAlly(Mob newAlly) {
        allies.add(newAlly);
    }

    public void removeAlly(Mob oldAlly) {
        allies.remove(oldAlly);
    }

    @Override
    public Integer getHealth() {
        return this.Health;
    }

    public void setHealth(Integer Health) {
        this.Health = Health;
    }

    public double getAttackDamage() {
        return this.attackDamage;
    }

    public void setAttackDamage(Integer attackDamage) {
        this.attackDamage = attackDamage;
    }
    

    @Override
    public String getType() {
        return "player";
    }

    /**
     * Mark the player as invincible for however many ticks
     * @param invincibleTicks How many ticks the player should be invincible for
     */
    public void setInvincibleTicks(Integer invincibleTicks) {
        this.invincibleTicks = invincibleTicks;
    }

    public Integer getInvisibleTicks() {
        return this.invisibleTicks;
    }

    /**
     * Mark the player as invisible for however many ticks
     * @param invisibleTicks How many ticks the player should be invisible for
     */
    public void setInvisibleTicks(Integer invisibleTicks) {
        this.invisibleTicks = invisibleTicks;
    }

    @Override
    public void incrementTick() {
        if (invincibleTicks > 0) {
            invincibleTicks--;
            for(Subscriber s: subscribers) {
                s.notifyInvincible(true);
            }
        } else {
            for(Subscriber s: subscribers) {
                s.notifyInvincible(false);
            }
        }
        if (invisibleTicks > 0) {
            invisibleTicks--;
            for(Subscriber s: subscribers) {
                s.notifyInvisible(true);
            }
        } else {
            for(Subscriber s: subscribers) {
                s.notifyInvisible(false);
            }
        }
    }

}
