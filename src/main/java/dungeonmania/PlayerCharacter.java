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

    public void fight(Entity e) {
        // does nothing
    }

    public void fight(Mob mob) {

        // get attack damage before fight so that order of attacks doesnt matter
        int charAttack = getHealth() * attack() / 5;
        int mobAttack = mob.getHealth() * mob.attack() / 10;

        takeDamage(mobAttack);
        mob.takeDamage(charAttack);
    }   
    
    

    public double getHealth() {
        return Health;
    }
}
