package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
import dungeonmania.FightManager;
import dungeonmania.entity.EntityFactory;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;

import java.util.Random;

public abstract class Difficulty {

    protected Dungeon dungref;
    protected MovementManager movementManager;
    protected FightManager fightManager;

    public Difficulty(Dungeon dungRef, MovementManager movementManager, FightManager fightManager) {
        this.dungref = dungRef;
        this.movementManager = movementManager;
        this.fightManager = fightManager;
    }

    /**
     * @return A string containing the name of the current gamemode
     */
    abstract public String getMode();

    /**
     * Simulate the game board for a single turn
     * @param entitiesMap The EntityList of all entities with a place in the dungeon
     * @param movementDirection A direction for the player
     */
    abstract public void simulate(EntityList entitiesMap, Direction movementDirection);

    /**
     * Create an entity factory with the correct entity values
     * @param entityMap The current dungeons' EntityList
     * @param currRandom The dungeon wide random object
     * @return An instance of an entity factory for this difficulty
     */
    abstract public EntityFactory createEntityFactory(EntityList entityMap, Random currRandom);
}
