package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.entity.EntityFactory_Ethan;
import dungeonmania.entity.StandardEntityFactory;
import dungeonmania.util.Direction;

public class Standard extends Difficulty {
    public Standard(Dungeon dungRef) {
        super(dungRef,movementManager,interactionManager,fightManager);
    }

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
    public EntityFactory_Ethan createEntityFactory() {
        return new StandardEntityFactory();
    }
}
