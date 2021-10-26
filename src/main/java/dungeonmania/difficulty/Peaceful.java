package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.PeacefulEntityFactory;
import dungeonmania.util.Direction;

public class Peaceful extends  Difficulty{
    public Peaceful(Dungeon dungRef, MovementManager movementManager, InteractionManager interactionManager, FightManager fightManager) {
        super(dungRef,movementManager,interactionManager,fightManager);
    }

    public void simulate(Direction movementDirection) {
//        movementManager.moveChar(movementDirection);
//        interactionManager.doCharInteractions();
//        movementManager.moveMobs();
//        interactionManager.doInteractions();
//        movementManager.doMercenarySpMove();
//        fightManager.resetHasFought();
    }

    @Override
    public EntityFactory createEntityFactory() {
        return new PeacefulEntityFactory();
    }
}
