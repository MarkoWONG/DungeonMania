package dungeonmania;

import java.util.ArrayList;
import java.util.Collection;

public abstract class PlayerCharacter extends Entity {

    private ArrayList<CollectableEntity> inventory;
    private ArrayList<Mob> allies;
    private double Health;
    private double attackDamage;
    private double defense;

    public addItemToInventory(CollectableEntity item) {
        ;
    }

    public removeItem(CollectableEntity item) {
        ;
    }
}
