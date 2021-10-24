package dungeonmania;

import dungeonmania.difficultyStates.Difficulty;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Dungeon {

    private Difficulty gameMode;
    private playerCharacter character;
    private HashMap<Position, ArrayList<Entity>> entitiesMap;
    private MovementManager movementManager;
    private InteractionManager interactionManager;
    private FightManager fightManager;


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
        // gameMode.simulate(Direction movementDirection);
            // THIS IS ALL WITHIN gameMode.simulate()
            // movementManager.moveChar(MovementDirection)
            // interactionManager.doCharInteractions()
            // FightManager.doCharFights()
            // movementManager.moveMobs()
            // interactionManager.doInteractions()
            // FightManager.doCharFights()
            // movementManager.doMercenarySpMove()
            // FightManager.resetHasFought()
    }

    public void deleteEntity(Entity entityTbd) {
    }


}
