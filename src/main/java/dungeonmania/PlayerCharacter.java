package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.entity.collectables.Weapon;
import dungeonmania.entity.collectables.buildable.Build;
import dungeonmania.mobs.Mob;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;


public class PlayerCharacter extends Entity implements Movement{

    private EntityList entities;
    private ArrayList<CollectableEntity> inventory;
    private ArrayList<Mob> allies;
    private Integer Health;
    private Integer attackDamage;
    private Integer invincibleTicks;
    private Integer invisibleTicks;

    public PlayerCharacter(Position position, Integer health, EntityList entities) {
        super(new Position(position.getX(), position.getY(),50));
        this.entities = entities;
        this.inventory = new ArrayList<>();
        this.allies = new ArrayList<>();
        this.Health = health;
        this.attackDamage = 1;
        this.invisibleTicks = 0;
        this.invincibleTicks = 0;
    }

    public void addItemToInventory(CollectableEntity item) {
        inventory.add(item);
        item.setPosition(null);
    }

    public void removeItemFromInventory(CollectableEntity item) {
        inventory.remove(item);
    }

    @Override
    public void move(Direction direction) {
        setPosition(getPosition().translateBy(direction));
    }



    public void consume(List<String> items) {
        ArrayList<CollectableEntity> itemsTBD = new ArrayList<CollectableEntity>();
        for (String eachString : items) {
            for (CollectableEntity eachItem : inventory) {
                if (eachItem.getType().equals(eachString)) {
                    itemsTBD.add(eachItem);
                    break;
                }
            }
        }
        for (CollectableEntity eachItemTBD : itemsTBD) {
            removeItemFromInventory(eachItemTBD);
        }
    }

    public List<String> getBuildables() {
        return Build.getBuildables(inventory);
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
        for (CollectableEntity e : inventory) {
            if (e.getType().equals("one_ring")) {
                setHealth(20);
                inventory.remove(e);
            }
        }
    }

    @Override
    public void fight(Entity e) {
        ;
    }

    @Override
    public void fight(Mob mob) {
        int mobAttack = mob.attack();
        mob.takeDamage(attack());
        takeDamage(mobAttack);
    }

    public int attack() {
        int AD = (int)getAttackDamage();
        ArrayList<String> typesUsed = new ArrayList<String>();


        for (CollectableEntity e : inventory) {
            if (!typesUsed.contains(e.getType())) {
                AD = e.usedInAttack(AD);
                e.usedInBattle(this);
                typesUsed.add(e.getType());
            }
        }

        for (Mob mob : allies) {
            AD += mob.attack();
        }
        return AD;
    }   

    public void takeDamage(int damage) {
        ArrayList<String> typesUsed = new ArrayList<String>();
        int reducedDamage = damage;

        for (CollectableEntity e : inventory) {
            if (!typesUsed.contains(e.getType())) {
                reducedDamage = e.usedInDefense(reducedDamage);
                e.usedInBattle(this);
                typesUsed.add(e.getType());
            }
        }
        setHealth(getHealth() - reducedDamage);
    }  
    
    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    // Getter and Setters
    public ArrayList<CollectableEntity> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<CollectableEntity> inventory) {
        this.inventory = inventory;
    }

    public void useItem(String itemType) {
        for (CollectableEntity eachItem : inventory) {
            if (eachItem.getType().equals(itemType)) {
                if (eachItem instanceof Usable) {
                    ((Usable) eachItem).useItem(this);
                    return;
                }
            }
        }
    }

    public CollectableEntity getItemById(String id) {
        for (CollectableEntity eachEntity : inventory) {
            if (eachEntity.getId().equals(id)) {
                return eachEntity;
            }
        }
        return null;
    }

    public CollectableEntity getItemByType(String type) {
        for (CollectableEntity eachEntity : inventory) {
            if (eachEntity.getType().equals(type)) {
                return eachEntity;
            }
        }
        return null;
    }

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

    public void setAllies(ArrayList<Mob> allies) {
        this.allies = allies;
    }

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


    public Integer getInvincibleTicks() {
        return invincibleTicks;
    }

    public void setInvincibleTicks(Integer invincibleTicks) {
        this.invincibleTicks = invincibleTicks;
    }

    public Integer getInvisibleTicks() {
        return this.invisibleTicks;
    }

    public void setInvisibleTicks(Integer invisibleTicks) {
        this.invisibleTicks = invisibleTicks;
    }

    @Override
    public void incrementTick() {
        if (invincibleTicks > 0) {
            invincibleTicks--;
        }
        if (invisibleTicks > 0) {
            invisibleTicks--;
        }
    }

}
