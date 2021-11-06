package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
import dungeonmania.FightManager;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Difficulty {

    protected transient Dungeon dungref;
    protected transient MovementManager movementManager;
    protected transient FightManager fightManager;

    public Difficulty(Dungeon dungRef, MovementManager movementManager, FightManager fightManager) {
        this.dungref = dungRef;
        this.movementManager = movementManager;
        this.fightManager = fightManager;
    }

    abstract public void simulate(EntityList entitiesMap, Direction movementDirection);

    abstract public EntityFactory createEntityFactory(EntityList entityMap);
}
