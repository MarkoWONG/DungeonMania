package dungeonmania.goal;

import dungeonmania.EntityList;
import dungeonmania.entity.Entity;
import dungeonmania.entity.staticEnt.Switch;

public class BouldersGoal implements Goal{

    private transient EntityList entities;

    public BouldersGoal(EntityList entities) {
        this.entities = entities;
    }
    @Override
    public boolean compute() {
        for (Entity eachEntity : entities.search("switch")) {
            Switch eachSwitch = (Switch) eachEntity;
            if (!eachSwitch.getSwitchOn()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return compute() ? "" : ":boulder";
    }
}
