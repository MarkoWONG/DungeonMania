package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class FightManager {

    private PlayerCharacter character;

    public void charFights(HashMap<Position, ArrayList<Entity>> entitiesMap) {
        doCharFights(entitiesMap.get(character.getPosition()));
    }

    public void doCharFights(ArrayList<Entity> entities) {
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

    public void setCharacter(PlayerCharacter character) {
        this.character = character;
    }
}