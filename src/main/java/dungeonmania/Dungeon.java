package dungeonmania;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dungeonmania.difficulty.Difficulty;
import dungeonmania.difficulty.Hard;
import dungeonmania.difficulty.Peaceful;
import dungeonmania.difficulty.Standard;
import dungeonmania.entity.Entity;
import dungeonmania.entity.EntityFactory;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.entity.collectables.buildable.BuildableEntity;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goal.GoalManager;
import dungeonmania.mobs.SpawnManager;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Dungeon {

    private String name;
    private String id;
    private int tick;
    private Position entry;

    private Difficulty gameMode;
    private PlayerCharacter character;
    private EntityList entities;
    private MovementManager movementManager;
    private FightManager fightManager;
    private GoalManager goalManager;
    private EntityFactory entityFactory;
    private Random currRandom;
    private Long currSeed;

    public Dungeon(String dungeonName, String gameMode, Long seed) {
        this.currSeed = seed;
        this.currRandom = new Random(seed);
        this.name = dungeonName;
        this.id = UUID.randomUUID().toString();
        this.entities = new EntityList();
        this.goalManager = new GoalManager(dungeonName,this);
        this.movementManager = new MovementManager(entities,currRandom);
        this.fightManager = new FightManager(entities);
        this.gameMode = difficultySelector(gameMode);
        this.entityFactory = this.gameMode.createEntityFactory(entities,currRandom);
        createNewEntitiesMap(entities, dungeonName);
        this.entry = character.getPosition();
        fightManager.setCharacter(character);
        movementManager.setCharacter(character);
        spawnSpiders();
        character.startGame();
        movementManager.initTicksTilMove(entities);
    }

    public Dungeon (JSONObject saveGame) {
        this(saveGame.getString("name"),saveGame.getString("gamemode"),saveGame.getLong("seed"));
        entitiesFromJSON(saveGame.getJSONArray("entities"),entities);
        character.replaceInventory(inventoryFromJSON(saveGame.getJSONArray("inventory")));
        this.id = saveGame.getString("id");
        PlayerCharacter thePlayer = entities.findPlayer();
        fightManager.setCharacter(thePlayer);
        movementManager.setCharacter(thePlayer);
    }

    public void tick(String itemUsed, Direction movementDirection) {
        if (itemUsed != null) {
            useItemId(itemUsed);
        }
        gameMode.simulate(entities,movementDirection);
        notifyOfTick();
        doSpawns();
        tick++;
    }

    private void useItemId(String itemUsed) {
        Entity givenItem = character.getItemById(itemUsed);
        if (!(givenItem instanceof Usable)) {
            throw new IllegalArgumentException();
        }
        if (!getInventory().contains(givenItem)) {
            throw new InvalidActionException(givenItem.getType() + "not in inventory");
        }
        ((Usable) givenItem).useItem(character);
    }

    private void notifyOfTick() {
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).incrementTick();
        }
    }

    public void click(String entityId) {
        Entity givenEntity = entities.searchId(entityId);
        if (givenEntity == null) {
            throw new IllegalArgumentException();
        }
        givenEntity.click(character);
    }

    public void doSpawns() {
        if (tick != 0 && tick % 30 == 0 && SpawnManager.checkValidSpawn(entities, entry)) {
            entities.add(entityFactory.create("mercenary", entry, "", "", 1));
        }
    }

    private void spawnSpiders() {
        for (int n = 0; n < 5 && entities.search("spider").size() < 4; n++) {
            Position p = SpawnManager.getRandPosition(entities, currRandom);
            if (p != null) {entities.add(entityFactory.create("spider", p, "", "", 1));}
        }
    }

    public void build(String item) {
        BuildableEntity target = BuildableEntity.findBuildable(item);
        // check if target is a buildable type
        if (target == null) {
            throw new IllegalArgumentException();
        }
<<<<<<< src/main/java/dungeonmania/Dungeon.java
        // check if buildable type has the materials
        else if (BuildableEntity.getBuildables(getInventory(), entities).contains(item)) {
            character.addItemToInventory(target);
            character.consume(target.getRecipeUsed());
        }
        else {
=======
        if (Build.getBuildables(getInventory()).contains(item)) {
            // can be safely typecast because we check if it's a valid item in the controller?
            character.addItemToInventory((CollectableEntity) entityFactory.create(item, null,null,null, 1));
            character.consume(Build.getRecipe(item));
        } else {
>>>>>>> src/main/java/dungeonmania/Dungeon.java
            throw new InvalidActionException("Missing required items");
        }
    }

    // will always be given a valid string, we do the checking in the controller
    private Difficulty difficultySelector(String gameMode) throws IllegalArgumentException {
        switch (gameMode.toLowerCase(Locale.ROOT)) {
            case ("peaceful"):
                return new Peaceful(this,movementManager,fightManager);
            case ("standard"):
                return new Standard(this,movementManager,fightManager);
            case ("hard"):
                return new Hard(this,movementManager,fightManager);
            default:
                throw new IllegalArgumentException("Game mode does not exist");
        }
    }

    private void createNewEntitiesMap(ArrayList<Entity> output, String dungeonName) throws IllegalArgumentException {
        String currFileStr;
        try {
            currFileStr = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (IOException e) {
            throw new IllegalArgumentException("Dungeon does not exist");
        }
        JSONArray currEntities = new JSONObject(currFileStr).getJSONArray("entities");
        entitiesFromJSON(currEntities,output);
    }

    private void entitiesFromJSON(JSONArray currEntities, ArrayList<Entity> input) {
        input.clear();
        for (int i = 0; i < currEntities.length() ; i++) {
            JSONObject currObj = currEntities.getJSONObject(i);
            Position currPosition = new Position(currObj.getInt("x"),currObj.getInt("y"));
            String currEntType = ((currObj.has("type") && !currObj.isNull("type"))) ? currObj.getString("type") : "";
            String currEntColour = ((currObj.has("colour") && !currObj.isNull("colour"))) ? currObj.getString("colour") : "";
            String currDoorKey =  ((currObj.has("key") && !currObj.isNull("key"))) ? String.valueOf(currObj.getInt("key")) : "";
            int currMovFactor =  ((currObj.has("movement_factor") && !currObj.isNull("movement_factor"))) ? currObj.getInt("movement_factor") : 1;
            Entity currEnt = entityFactory.create(currEntType, currPosition,currEntColour,currDoorKey, currMovFactor);
            if ( currEntType.equals("player") ) {
                this.character = (PlayerCharacter) currEnt;
                this.entry = currPosition;
            }
            input.add(currEnt);
        }
    }

    private ArrayList<CollectableEntity> inventoryFromJSON(JSONArray currInventory) {
        ArrayList<CollectableEntity> newInv = new ArrayList<>();
        for (int i = 0; i < currInventory.length() ; i++) {
            JSONObject currObj = currInventory.getJSONObject(i);
            String currEntType = ((currObj.has("type") && !currObj.isNull("type"))) ? currObj.getString("type") : "";
            String currDoorKey =  ((currObj.has("key") && !currObj.isNull("key"))) ? String.valueOf(currObj.getInt("key")) : "";
            Entity currEnt = entityFactory.create(currEntType, null,null,currDoorKey, 1);
            newInv.add((CollectableEntity) currEnt);
        }
        return newInv;
    }

    // Getters for creating a DungeonResponse

    public String getGamemode() {
        return gameMode.getMode();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Long getCurrSeed() {
        return currSeed;
    }


    public String getGoals() {
        return goalManager.getGoals();
    }

    public ArrayList<CollectableEntity> getInventory() {
        return character.getInventory();
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public List<String> getBuildables() {
        return character.getBuildables(entities);
    }



}
