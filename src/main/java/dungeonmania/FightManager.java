package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Anduril;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.rare.OneRing;

import java.util.ArrayList;
import java.util.Random;

public class FightManager {

    private PlayerCharacter character;
    private EntityList entities;
    private Random rand;

    public FightManager(EntityList entities) {
        this.entities = entities;
        this.character = null;
        this.rand = new Random();
    }

    public FightManager(EntityList entities, Random rand) {
        this.entities = entities;
        this.character = null;
        this.rand = rand;
    }

    public void doCharFights() {
        if (character.getInvisibleTicks() <= 0) {
            ArrayList<Entity> currTile= entities.searchTile(character);
            for (Entity e : currTile) {
                if (!e.hasFought()) {
                    e.startFight(character);
                    e.setHasFought(true);
                }
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
                    for(int j = curr.getInventory().size() - 1; j >= 0; --j) {
                        character.addItemToInventory(curr.getInventory().get(j));
                    }
                    int newRand = rand.nextInt(10);
                    if (newRand == 0) {
                        character.addItemToInventory(new OneRing());
                    } else if (newRand == 1) {
                        character.addItemToInventory(new Anduril());
                    }
                } else {
                    curr.revive(curr);
                }
            }
        }
    }

    public void resetHasFought() {
        for (Entity eachEntity : entities) {
                eachEntity.setHasFought(false);
        }
    }

    public void setCharacter(PlayerCharacter character) {
        this.character = character;
    }
}