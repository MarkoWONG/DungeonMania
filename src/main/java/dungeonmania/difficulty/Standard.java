package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.StandardEntityFactory;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class Standard extends Difficulty {
    public Standard(Dungeon dungRef, MovementManager movementManager, InteractionManager interactionManager, FightManager fightManager) {
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
//        fightManager.resetHasFought(dungRef.getEntitiesMap());
    }

    @Override
    public EntityFactory createEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap) {
        return new StandardEntityFactory(entityMap);
    }
}
