package dungeonmania;

import dungeonmania.difficulty.Difficulty;
import dungeonmania.difficulty.Hard;
import dungeonmania.difficulty.Peaceful;
import dungeonmania.difficulty.Standard;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.collectables.buildable.Build;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.goal.GoalManager;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Dungeon {

    private String name;
    private String id;
    private Position entry;

    private Difficulty gameMode;
    private PlayerCharacter character;
    private HashMap<Position, ArrayList<Entity>> entitiesMap = new HashMap<>();
    private MovementManager movementManager;
    private InteractionManager interactionManager;
    private FightManager fightManager;
    private GoalManager goalManager;
    private EntityFactory entityFactory;

    public Dungeon(String dungeonName, String gameMode) {
        this.gameMode = difficultySelector(gameMode);
        this.entityFactory = this.gameMode.createEntityFactory(entitiesMap);
        this.movementManager = new MovementManager();
        this.interactionManager = new InteractionManager();
        this.fightManager = new FightManager();
        this.goalManager = new GoalManager(dungeonName,this);
        createEntitiesMap_FromJson(entitiesMap, dungeonName);
        this.name = dungeonName;
        this.id = UUID.randomUUID().toString();
    }

    public void tick(String itemUsed, Direction movementDirection) {
        // if item is used
            // for each in theCharacter inventory
                // if the item is iteMused
                    // item.use()
        // gameMode.simulate(movementDirection);
        // notifyOfTick();
    }

    private void notifyOfTick() {
        for (ArrayList<Entity> eachTile : entitiesMap.values()) {
            for (Entity eachEntity : eachTile) {
                eachEntity.incrementTick();
            }
        }
    }

    public void deleteEntity(Entity entityTbd) {}


    public void build(String item) {
        if (Build.getBuildables(getInventory()).contains(item)) {
            character.addItemToInventory(entityFactory.create(item, null,null,null));
            character.consume(Build.getRecipe(item));
        }

    }

    // will always be given a valid string, we do the checking in the controller
    private Difficulty difficultySelector(String gameMode) {
        switch (gameMode) {
            case ("Peaceful"):
                return new Peaceful(this,movementManager,interactionManager,fightManager);
            case ("Standard"):
                return new Standard(this,movementManager,interactionManager,fightManager);
            case ("Hard"):
                return new Hard(this,movementManager,interactionManager,fightManager);
            default:
                return new Standard(this,movementManager,interactionManager,fightManager);
        }
    }

    private void createEntitiesMap_FromJson(HashMap<Position, ArrayList<Entity>> output, String dungeonName) throws IllegalArgumentException {
        String currFileStr;
        try {
            currFileStr = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (IOException e) {
            throw new IllegalArgumentException("Dungeon does not exist");
        }
        JSONArray currEntities = new JSONObject(currFileStr).getJSONArray("entities");
        for (int i = 0; i < currEntities.length() ; i++) {
            JSONObject currObj = currEntities.getJSONObject(i);
            Position currPosition = new Position(currObj.getInt("x"),currObj.getInt("y"));
            String currEntType = currObj.getString("type");
            String currEntColour = currObj.getString("colour");
            String currDoorKey= currObj.getString("key");
            String currKeyDoor = currObj.getString("door");
            Entity currEnt = entityFactory.create(currEntType, currPosition,currEntColour,currDoorKey);
            if ( currEntType.equals("player") ) {
                this.character = (PlayerCharacter) currEnt;
                this.entry = currPosition;
            }
            if (!output.containsKey(currPosition)) { // we can do this because position overrides hashCode and equals
                output.put(currPosition, new ArrayList<Entity>());
            }
            output.get(currPosition).add(currEnt);
        }
    }

    // Getters for creating a DungeonResponse

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }


    public String getGoals() {
        return goalManager.getGoals();
    }

    public ArrayList<CollectableEntity> getInventory() {
        return character.getInventory();
    }

    public HashMap<Position, ArrayList<Entity>> getEntitiesMap() {
        return entitiesMap;
    }

    public List<String> getBuildables() {
        return character.getBuildables();
    }



}
