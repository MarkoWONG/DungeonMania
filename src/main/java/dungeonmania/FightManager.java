package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class FightManager {

    private PlayerCharacter character;
    private EntityList entities;

    public FightManager(EntityList entities) {
        this.entities = entities;
        this.character = null;
    }

    public void doCharFights() {
        ArrayList<Entity> currTile= entities.searchTile(character);
        for (Entity e : currTile) {
            if (!e.hasFought()) {
                e.startFight(character);
            }
        }
        cleanUp();
    }

    private void cleanUp () {
        for(int i = entities.size() - 1; i >= 0; --i) {
            Entity curr = entities.get(i);
            if(curr.getHealth() <= 0) {
                if (!curr.canRevive()) {
                    entities.remove(i);
                } else {
                    curr.revive();
                }
            }
        }
    }

    public void resetHasFought() {
        for (Entity eachEntity : entities) {
                eachEntity.resetHasFought();
        }
    }

    public void setCharacter(PlayerCharacter character) {
        this.character = character;
    }
}