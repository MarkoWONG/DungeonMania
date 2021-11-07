package dungeonmania;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
import java.util.Iterator;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class PlayerCharacter extends Entity implements Movement{

    private ArrayList<CollectableEntity> inventory;
    private ArrayList<Mob> allies;
    private Integer Health;
    private Integer attackDamage;
    private Integer invincibleTicks;
    private Integer invisibleTicks;

    public PlayerCharacter(Position position, int health, int ad) {
        super(new Position(position.getX(), position.getY(),50));
        this.inventory = new ArrayList<>();
        this.allies = new ArrayList<>();
        this.Health = health;
        this.attackDamage = ad;
        this.invisibleTicks = 0;
        this.invincibleTicks = 0;
    }

    public void replaceInventory(ArrayList<CollectableEntity> newInv) {
        this.inventory = newInv;
    }

    public void addItemToInventory(CollectableEntity item) {
        inventory.add(item);
        item.setPosition(null);
    }

    public void removeItemFromInventory(CollectableEntity item) {
        inventory.remove(item);
    }

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
    }



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
        if (mob.isEnemy()) {
            int mobAttack = mob.getAttackDamage() * mob.getHealth() / 10;
            mob.takeDamage(attack());
            takeDamage(mobAttack);
        }
    }

    public int attack() {
        int AD = (int)getAttackDamage();
        ArrayList<String> typesUsed = new ArrayList<String>();

        for (Iterator<CollectableEntity> iterator = inventory.iterator(); iterator.hasNext();){
            CollectableEntity currentEnt = iterator.next();
            if (!typesUsed.contains(currentEnt.getType())) {
                AD = currentEnt.usedInAttack(AD);
                if (currentEnt.usedInBattle(this)){
                    iterator.remove();
                    typesUsed.add(currentEnt.getType());
                }
            }
        }
        

        for (Mob mob : allies) {
            AD += mob.getAttackDamage();
        }
        return AD * getHealth() / 5;
    }   

    public void takeDamage(int damage) {
        ArrayList<String> typesUsed = new ArrayList<String>();
        int reducedDamage = damage;
        for (Iterator<CollectableEntity> iterator = inventory.iterator(); iterator.hasNext();){
            CollectableEntity currentEnt = iterator.next();
            if (!typesUsed.contains(currentEnt.getType())) {
                reducedDamage = currentEnt.usedInDefense(reducedDamage);
                if (currentEnt.usedInBattle(this)){
                    iterator.remove();
                    typesUsed.add(currentEnt.getType());
                }
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
