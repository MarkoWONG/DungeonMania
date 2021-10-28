package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Difficulty {

    protected Dungeon dungref;
    protected MovementManager movementManager;
    protected InteractionManager interactionManager;
    protected FightManager fightManager;

    public Difficulty(Dungeon dungRef, MovementManager movementManager, InteractionManager interactionManager, FightManager fightManager) {
        this.dungref = dungRef;
        this.movementManager = movementManager;
        this.interactionManager = interactionManager;
        this.fightManager = fightManager;
    }

    abstract public void simulate(Direction movementDirection);

    abstract public EntityFactory createEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap);
}
