package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.FightManager;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.StandardEntityFactory;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class Standard extends Difficulty {
    public Standard(Dungeon dungRef, MovementManager movementManager, FightManager fightManager) {
        super(dungRef,movementManager,fightManager);
    }

    @Override
    public HashMap<Position, ArrayList<Entity>> simulate(HashMap<Position, ArrayList<Entity>> entitiesMap, Direction moveDir) {
        entitiesMap = movementManager.moveChar(entitiesMap,moveDir);
        movementManager.doInteractions(entitiesMap);
        fightManager.charFights(entitiesMap);
        entitiesMap = movementManager.moveMobs(entitiesMap);
        movementManager.doInteractions(entitiesMap);
        fightManager.charFights(entitiesMap);
//        movementManager.doMercenarySpMove();
        fightManager.charFights(entitiesMap);
        fightManager.resetHasFought(entitiesMap);
        return entitiesMap;
    }

    @Override
    public EntityFactory createEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap) {
        return new StandardEntityFactory(entityMap);
    }
}
