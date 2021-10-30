package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.buildables.Build;
import dungeonmania.util.Position;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


public class PlayerCharacter extends Entity implements Movement{

    private ArrayList<CollectableEntity> inventory;
    private ArrayList<Mob> allies;
    private Double health;
    private Double attackDamage;
    private Double defense;
    private Boolean hasArmour;

    public PlayerCharacter(Double health, Double attack, Double defense) {
        this.health = health;
        this.attackDamage = attack;
        this.defense = defense;
        this.hasArmour = false;
    }

    public void addItemToInventory(CollectableEntity item) {
        inventory.add(item);
    }

    public void removeItem(CollectableEntity item) {
        inventory.remove(item);
    }

    public void takeDamage(Double damage) {
        Double damageTaken = damage * defense;
        health -= damageTaken;

        if (health <= 0) {
            // notify something coz of death and all that
        }
    }

    @Override
    public void move(Direction direction) {

    public PlayerCharacter(Position position) {
        super(position);
    }

    public void addItemToInventory(Entity item) {
        ;
    }

    public void removeItem(CollectableEntity item) {
        ;
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
            removeItem(eachItemTBD);
        }
    }

    public List<String> getBuildables() {
        return Build.getBuildables(inventory);
    }



    @Override
    public void fight(Mob mob) {

    }

    @Override
    public void startInteraction(Entity entity) {

    }
}
