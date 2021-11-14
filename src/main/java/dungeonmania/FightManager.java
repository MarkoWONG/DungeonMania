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

    /**
     * Do all fights between the character and entities on the same tile as the character
     */
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

    /**
     * For all entities with negative health, remove them from the game
     * If they had an item, add it to the players inventory
     * Randomly give the player a rare collectable on kill
     */
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
                    curr.revive();
                }
            }
        }
    }

    /**
     * For each entity, reset it's hasFought status to false
     */
    public void resetHasFought() {
        for (Entity eachEntity : entities) {
                eachEntity.setHasFought(false);
        }
    }

    /**
     * Set the current instance of FightManager to the given character
     * @param character The controlled character for this dungeon
     */
    public void setCharacter(PlayerCharacter character) {
        this.character = character;
    }
}