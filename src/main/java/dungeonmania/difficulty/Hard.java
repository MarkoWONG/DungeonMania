package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.HardEntityFactory;
import dungeonmania.util.Direction;

public class Hard extends Difficulty{
    public Hard(Dungeon dungRef, MovementManager movementManager, InteractionManager interactionManager, FightManager fightManager) {
        super(dungRef,movementManager,interactionManager,fightManager);
    }

    // same simulation as standard difficulty, only entity factory has changes
    @Override
    public void simulate(Direction movementDirection) {
//        movementManager.moveChar(movementDirection);
//        interactionManager.doCharInteractions();
//        fightManager.doCharFights();
//        movementManager.moveMobs();
//        interactionManager.doInteractions();
//        fightManager.doCharFights();
//        movementManager.doMercenarySpMove();
//        fightManager.resetHasFought();
    }

    @Override
    public EntityFactory createEntityFactory() {
        return new HardEntityFactory();
    }
}
