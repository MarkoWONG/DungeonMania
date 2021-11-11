package dungeonmania.goal;

import dungeonmania.EntityList;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.Mob;

public class EnemiesGoal implements Goal {

    private EntityList entities;

    public EnemiesGoal(EntityList entities) {
        this.entities = entities;
    }
    @Override
    public boolean compute() {
        for (Entity eachEntity : entities) {
            if (eachEntity instanceof Mob && eachEntity.getPosition() != null && ((Mob) eachEntity).isEnemy()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        // mercenary represents all entities
        return compute() ? "" : ":mercenary";
    }
}
