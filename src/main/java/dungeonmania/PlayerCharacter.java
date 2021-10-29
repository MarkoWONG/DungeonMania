package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class PlayerCharacter extends Entity {

    private ArrayList<CollectableEntity> inventory;
    private ArrayList<Mob> allies;
    private double Health;
    private double attackDamage;
    private double defense;

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



    @Override
    public void fight(Mob mob) {

    }

    @Override
    public void startInteraction(Entity entity) {

    }
}
