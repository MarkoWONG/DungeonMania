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

    public void resetHasFought() {
        for (Entity eachEntity : entities) {
                eachEntity.resetHasFought();
        }
    }

    public void setCharacter(PlayerCharacter character) {
        this.character = character;
    }
}