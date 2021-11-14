package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
import dungeonmania.FightManager;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.HardEntityFactory;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;

import java.util.Random;

public class Hard extends Difficulty{
    public Hard(Dungeon dungRef, MovementManager movementManager, FightManager fightManager) {
        super(dungRef,movementManager,fightManager);
    }

    @Override
    public String getMode() {
        return "Hard";
    }

    // same simulation as standard difficulty, only entity factory has changes
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
        return new HardEntityFactory(entityMap, currRandom);
    }
}
