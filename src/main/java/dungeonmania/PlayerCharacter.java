package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.mobs.Mob;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.managers.Movement;

import java.util.ArrayList;


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

    }

    @Override
    public void move(Direction direction) {

    }

    @Override
    public void teleport(Position position) {

    }
}
