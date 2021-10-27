package dungeonmania;

import dungeonmania.difficulty.Difficulty;
import dungeonmania.difficulty.Hard;
import dungeonmania.difficulty.Peaceful;
import dungeonmania.difficulty.Standard;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Dungeon {

    private String name;
    private String id;

    private Difficulty gameMode;
    private PlayerCharacter character;
    private HashMap<Position, ArrayList<Entity>> entitiesMap;
    private MovementManager movementManager;
    private InteractionManager interactionManager;
    private FightManager fightManager;
    private GoalManager goalManager;
    private EntityFactory entityFactory;

    public Dungeon(String dungeonName, String gameMode) {
        this.gameMode = difficultySelector(gameMode);
        this.entityFactory = gameMode.createEntityFactory();
        this.movementManager = new MovementManager();
        this.interactionManager = new InteractionManager();
        this.fightManager = new FightManager();
        this.goalManager = new GoalManager(dungeonName);
        this.entitiesMap = createEntitiesMap_FromJson(dungeonName);
    }

    private HashMap<Position, ArrayList<Entity>> createEntitiesMap_FromJson(String dungeonName) throws IllegalArgumentException {
        String currFileStr;
        try {
            currFileStr = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (IOException e) {
            throw new IllegalArgumentException("Dungeon does not exist");
        }
        JSONArray currEntities = new JSONObject(currFileStr).getJSONArray("entities");
        HashMap<Position, ArrayList<Entity>> output = new HashMap<>();
        for (int i = 0; i < currEntities.length() ; i++) {
            JSONObject currObj = currEntities.getJSONObject(i);
            Position currPosition = new Position(currObj.getInt("x"),currObj.getInt("y"));
            String currEntType = currObj.getString("type");
            Entity currEnt = entityFactory.create(currEntType, currPosition);
            if ( currEntType.equals("player") ) {
                this.character = (PlayerCharacter) currEnt;
            }
            if (!output.containsKey(currPosition)) { // we can do this because position overrides hashCode and equals
                output.put(currPosition, new ArrayList<Entity>());
            }
            output.get(currPosition).add(currEnt);
        }
        return output;
    }


    public void tick(String itemUsed, Direction movementDirection) {
        // if item is used
            // for each in theCharacter inventory
                // if the item is iteMused
                    // item.use()
        // gameMode.simulate(movementDirection);
    }

    public void deleteEntity(Entity entityTbd) {}

    // will always be given a valid string, we do the checking in the controller
    private Difficulty difficultySelector(String gameMode) {
        switch (gameMode) {
            case ("Peaceful"):
                return new Peaceful(this,movementManager,interactionManager,fightManager);
            case ("Standard"):
                return new Standard(this,movementManager,interactionManager,fightManager);
            case ("Hard"):
                return new Hard(this,movementManager,interactionManager,fightManager);
        }
    }

}
