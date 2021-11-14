package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
import dungeonmania.FightManager;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.StandardEntityFactory;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;

import java.util.Random;

public class Standard extends Difficulty {
    public Standard(Dungeon dungRef, MovementManager movementManager, FightManager fightManager) {
        super(dungRef,movementManager,fightManager);
    }

    @Override
    public String getMode() {
        return "Standard";
    }

    @Override
    public void simulate(EntityList entitiesMap, Direction moveDir) {
        movementManager.moveChar(moveDir);
        fightManager.doCharFights();
        movementManager.moveMobs();
        movementManager.doInteractions();
        fightManager.doCharFights();
        fightManager.doCharFights();
        fightManager.resetHasFought();
    }

    @Override
    public EntityFactory createEntityFactory(EntityList entityMap, Random currRandom) {
        return new StandardEntityFactory(entityMap, currRandom);
    }
}
