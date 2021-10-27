package dungeonmania;

import dungeonmania.entity.Entity_Ethan;

import java.util.ArrayList;

public class PlayerCharacter extends Entity_Ethan {

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

    @Override
    public void fight(Mob mob) {

    }

    @Override
    public void startInteraction(Entity entity) {

    }
}
