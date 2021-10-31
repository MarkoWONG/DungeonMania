package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.Mob.Mob;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.entity.collectables.buildable.Build;
import dungeonmania.mobs.Mob;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;


public class PlayerCharacter extends Entity implements Movement{

    private Position position;
    private ArrayList<CollectableEntity> inventory;
    private ArrayList<Mob> allies;
    private double Health;
    private double attackDamage;
    private Integer invincibleTicks;
    private Integer invisibleTicks;

    public PlayerCharacter(Position position, Double health, Double attack, Double defense) {
        super(position);
        this.position = position;
        this.inventory = new ArrayList<>();
        this.allies = new ArrayList<>();
        this.Health = 10;
        this.attackDamage = 1;
        this.invisibleTicks = 0;
        this.invincibleTicks = 0;
    }

    public void addItemToInventory(CollectableEntity item) {
        inventory.add(item);
    }

    public void removeItemFromInventory(CollectableEntity item) {
        inventory.remove(item);
    }

    @Override
    public void move(Direction direction) {}



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
    public void startInteraction(Entity entity) {

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

    public ArrayList<Mob> getAllies() {
        return this.allies;
    }

    public void setAllies(ArrayList<Mob> allies) {
        this.allies = allies;
    }

    public double getHealth() {
        return this.Health;
    }

    public void setHealth(double Health) {
        this.Health = Health;
    }

    public double getAttackDamage() {
        return this.attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public Position getPosition() {
        return this.position;
    }

    @Override
    public String getType() {
        return "player";
    }

    @Override
    public boolean isInteractable() {
        return false;
    }

    public void setPosition(Position position) {
        this.position = position;
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
