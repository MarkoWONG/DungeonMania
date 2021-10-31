package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.Mob.Mob;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.entity.collectables.buildable.Build;
import dungeonmania.util.Position;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;

public class FightManager {

    public FightManager(){}

    public void doCharFights(PlayerCharacter character, ArrayList<Entity> entities) {
        // does all fights, if somethings health <= 0 then it is removed in cleanup
        for (Entity e : entities) {
            if (!e.hasFought()) {
                e.startFight(character);
            }
        }
        
        cleanUp(entities);
        
    }

    private void cleanUp (ArrayList<Entity> entities) {
        for (Entity e : entities) {
            if (e.getHealth() <= 0) {
                if (!e.canRevive()) {
                     entities.remove(e);
                } else {
                     e.revive();
                }
            }
        }
    }

    public void resetHasFought(HashMap<Position, ArrayList<Entity>> entitiesMap) {
        for (ArrayList<Entity> entities: entitiesMap.values()) {
            for (Entity e : entities) {
                e.resetHasFought();
            }
        }
    }
}