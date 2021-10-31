package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.FightManager;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Difficulty {

    protected Dungeon dungref;
    protected MovementManager movementManager;
    protected FightManager fightManager;

    public Difficulty(Dungeon dungRef, MovementManager movementManager, FightManager fightManager) {
        this.dungref = dungRef;
        this.movementManager = movementManager;
        this.fightManager = fightManager;
    }

    abstract public HashMap<Position, ArrayList<Entity>> simulate(HashMap<Position, ArrayList<Entity>> entitiesMap, Direction movementDirection);

    abstract public EntityFactory createEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap);
}
