package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.FightManager;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.PeacefulEntityFactory;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class Peaceful extends  Difficulty{
    public Peaceful(Dungeon dungRef, MovementManager movementManager, FightManager fightManager) {
        super(dungRef,movementManager,fightManager);
    }

    public HashMap<Position, ArrayList<Entity>> simulate(HashMap<Position, ArrayList<Entity>> entitiesMap, Direction moveDir) {
        entitiesMap = movementManager.moveChar(entitiesMap,moveDir);
//        interactionManager.doCharInteractions();
        entitiesMap = movementManager.moveMobs(entitiesMap);
//        interactionManager.doInteractions();
//        movementManager.doMercenarySpMove();
        return entitiesMap;
    }

    @Override
    public EntityFactory createEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap) {
        return new PeacefulEntityFactory(entityMap);
    }
}
