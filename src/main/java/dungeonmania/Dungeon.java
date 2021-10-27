package dungeonmania;

import dungeonmania.difficulty.Difficulty;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Dungeon {

    private Difficulty gameMode;
    private PlayerCharacter character;
    private HashMap<Position, ArrayList<Entity>> entitiesMap;
    private MovementManager movementManager;
    private InteractionManager interactionManager;
    private FightManager fightManager;
    private GoalManager goalManager;
    private EntityFactory entityFactory;

    public Dungeon(String dungeonName, Difficulty gameMode) {
        this.gameMode = gameMode;
        this.entityFactory = gameMode.createEntityFactory();
        this.movementManager = new MovementManager();
        this.interactionManager = new InteractionManager();
        this.fightManager = new FightManager();
        this.goalManager = new GoalManager(dungeonName);
        this.entitiesMap = createEntitiesMap(dungeonName);
    }

    private HashMap<Position, ArrayList<Entity>> createEntitiesMap(String dungeonName) {
        // set this.character to the character when you add it
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
