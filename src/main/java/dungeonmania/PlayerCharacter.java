package dungeonmania;

import dungeonmania.entity.Entity;

import java.util.ArrayList;

public class PlayerCharacter extends Entity {

    private ArrayList<CollectableEntity> inventory;
    private ArrayList<Mob> allies;
    private double Health;
    private double attackDamage;
    private double defense;

    public void addItemToInventory(CollectableEntity item) {
        ;
    }

    public void removeItem(CollectableEntity item) {
        ;
    }
}
