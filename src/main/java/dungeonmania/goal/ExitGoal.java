package dungeonmania.goal;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.entity.staticEnt.Exit;

public class ExitGoal implements Goal {

    private EntityList entities;

    public ExitGoal(EntityList entities) {
        this.entities = entities;
    }

    @Override
    public boolean compute() {
        PlayerCharacter thePlayer = entities.findPlayer();
        if (thePlayer == null) {
            return false;
        }
        for (Entity eachEntity : entities.searchTile(thePlayer)) {
            if (eachEntity instanceof Exit) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return compute() ? "" : ":exit";
    }
}
