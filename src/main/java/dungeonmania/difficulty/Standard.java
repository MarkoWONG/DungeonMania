package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
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
    public void simulate(EntityList entitiesMap, Direction moveDir) {
        movementManager.moveChar(moveDir);
        movementManager.doInteractions();
        fightManager.doCharFights();
        movementManager.moveMobs();
        movementManager.doInteractions();
        fightManager.doCharFights();
//        movementManager.doMercenarySpMove();
        fightManager.doCharFights();
        fightManager.resetHasFought();
    }

    @Override
    public EntityFactory createEntityFactory(EntityList entityMap) {
        return new StandardEntityFactory(entityMap);
    }
}
