package dungeonmania.goal;

import dungeonmania.EntityList;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Treasure;

public class TreasureGoal implements Goal{

    private transient EntityList entities;

    public TreasureGoal(EntityList entities) {
        this.entities = entities;
    }

    @Override
    public boolean compute() {
        for (Entity eachEntity : entities) {
            if (eachEntity instanceof Treasure && eachEntity.getPosition() != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return compute() ? "" : ":treasure";
    }
}
