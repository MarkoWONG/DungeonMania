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
    private EntityFactory entityFactory;

    public Dungeon(String dungeonName, Difficulty gameMode) {
        this.gameMode = gameMode;
        this.entityFactory = gameMode.createEntityFactory();
        this.character =  entityFactory.create("character");
        this.movementManager = new MovementManager();
        this.interactionManager = new InteractionManager();
        this.fightManager = new FightManager();
        this.entitiesMap = createEntitiesMap(dungeonName);

    }

    public HashMap<Position, ArrayList<Entity>> createEntitiesMap(String dungeonName) {
        ;
    }


    public void tick(String itemUsed, Direction movementDirection) {
        // if item is used
            // for each in theCharacter inventory
                // if the item is itemUsed
                    // item.use()
        // gameMode.simulate(movementDirection);
    }

    public void deleteEntity(Entity entityTbd) {}


}
