package dungeonmania.difficultyStates;

import dungeonmania.Dungeon;

public class Hard extends Difficulty{
    public Hard(Dungeon dungRef) {
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
        return null;
    }
}
