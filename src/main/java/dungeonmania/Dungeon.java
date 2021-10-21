package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Dungeon {

    private Difficulty gameMode;
    private playerCharacter theCharacter;
    private HashMap<Position, ArrayList<Entity>> entitiesMap;
    private MovementManager theFightManager;
    private CollisionManager theCollisionManager;


    public Dungeon(String dungeonName, Difficulty gameMode) {
        ;
    }

    public HashMap<Position, ArrayList<Entity>> createEntitiesMap(String dungeonName) throws IOException {
        ;
    }


    public void tick(String itemUsed, Direction movementDirection) {
        // if item is used
            // for each in theCharacter inventory
                // if the item is iteMused
                    // item.use()
        // movementManager.moveChar(MovementDirection)
        // movementManager.doInteractions()
        // movementManager.moveMobs()
        // movementManager.doInteractions()
        // movementManager.doMercenarySpMove()
        // OR
        // movementManager.simulate()
        // OR
        // movementManager.moveChar(MovementDirection)
        // interactionManager.doInteractions()
        // movementManager.moveMobs()
        // interactionManager.doInteractions()
        // movementManager.doMercenarySpMove()
    }

    public void deleteEntity(Entity entityTbd) {
        return;
    }


}
