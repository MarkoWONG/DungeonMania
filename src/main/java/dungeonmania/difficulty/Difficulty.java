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
     *
     * @param entitiesMap The EntityList of all entities with a place in the dungeon
     * @param movementDirection A direction for the player
     */
    abstract public void simulate(EntityList entitiesMap, Direction movementDirection);

    abstract public EntityFactory createEntityFactory(EntityList entityMap, Random currRandom);
}
