package dungeonmania.difficulty;

import dungeonmania.Dungeon;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.PeacefulEntityFactory;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class Peaceful extends  Difficulty{
    public Peaceful(Dungeon dungRef, MovementManager movementManager, InteractionManager interactionManager, FightManager fightManager) {
        super(dungRef,movementManager,interactionManager,fightManager);
    }

    public HashMap<Position, ArrayList<Entity>> simulate(Direction movementDirection) {
//        movementManager.moveChar(movementDirection);
//        interactionManager.doCharInteractions();
//        movementManager.moveMobs();
//        interactionManager.doInteractions();
//        movementManager.doMercenarySpMove();
//        fightManager.resetHasFought();
    }

    @Override
    public EntityFactory createEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap) {
        return new PeacefulEntityFactory(entityMap);
    }
}
