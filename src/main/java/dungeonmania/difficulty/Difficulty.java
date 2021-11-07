package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
import dungeonmania.FightManager;
import dungeonmania.entity.EntityFactory;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;

public abstract class Difficulty {

    protected Dungeon dungref;
    protected MovementManager movementManager;
    protected FightManager fightManager;

    public Difficulty(Dungeon dungRef, MovementManager movementManager, FightManager fightManager) {
        this.dungref = dungRef;
        this.movementManager = movementManager;
        this.fightManager = fightManager;
    }

    abstract public String getMode();

    abstract public void simulate(EntityList entitiesMap, Direction movementDirection);

    abstract public EntityFactory createEntityFactory(EntityList entityMap);
}
