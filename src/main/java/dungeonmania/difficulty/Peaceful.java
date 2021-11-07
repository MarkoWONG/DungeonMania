package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
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

    @Override
    public String getMode() {
        return "Peaceful";
    }

    public void simulate(EntityList entitiesMap, Direction moveDir) {
        movementManager.moveChar(moveDir);
//        movementManager.doInteractions();
        movementManager.moveMobs();
        movementManager.doInteractions();
//        movementManager.doMercenarySpMove();
    }

    @Override
    public EntityFactory createEntityFactory(EntityList entityMap) {
        return new PeacefulEntityFactory(entityMap);
    }
}
