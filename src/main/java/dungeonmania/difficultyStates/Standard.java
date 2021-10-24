package dungeonmania.difficultyStates;

import dungeonmania.Dungeon;

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
}
