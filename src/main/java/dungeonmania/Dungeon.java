package dungeonmania;

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
        this.fightManager = new FightManager(entities, currRandom);
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

    /**
     * Given a JSON Object as specified by DungeonJSONAdapter.toJSON, create a dungeon
     * @param saveGame The JSONObject in the save game format
     */
    public Dungeon (JSONObject saveGame) {
        this(saveGame.getString("name"),saveGame.getString("gamemode"),saveGame.getLong("seed"));
        entitiesFromJSON(saveGame.getJSONArray("entities"),entities);
        character.replaceInventory(inventoryFromJSON(saveGame.getJSONArray("inventory")));
        this.id = saveGame.getString("id");
        PlayerCharacter thePlayer = entities.findPlayer();
        fightManager.setCharacter(thePlayer);
        movementManager.setCharacter(thePlayer);
    }

    /**
     * Simulate a single turn of the board
     * @param itemUsed The ID of the item to be used
     * @param movementDirection The direction that the player is to move
     */
    public void tick(String itemUsed, Direction movementDirection) {
        if (itemUsed != null) {
            useItemId(itemUsed);
        }
        gameMode.simulate(entities,movementDirection);
        notifyOfTick();
        doSpawns();
        tick++;
    }

    /**
     * Given an item ID in the player's inventory, find it, and use it
     * @param itemUsed The ID of the item to be used
     */
    private void useItemId(String itemUsed) {
        Entity givenItem = null;
        givenItem = character.getItemById(itemUsed);
        if (givenItem == null) {
            givenItem = entities.searchId(itemUsed);
        }
        if (!(givenItem instanceof Usable)) {
            throw new IllegalArgumentException();
        }
        if (!getInventory().contains(givenItem)) {
            throw new InvalidActionException(givenItem.getType() + "not in inventory");
        }
        ((Usable) givenItem).useItem(character);
    }

    /**
     * Notify all entities in the EntityList of the new tick
     */
    private void notifyOfTick() {
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).incrementTick();
        }
    }

    /**
     * Given the ID for an entity, attempt to click on them.
     * Throw IllegalArgumentException if entity not found
     * @param entityId The ID for an entity
     */
    public void click(String entityId) {
        Entity givenEntity = entities.searchId(entityId);
        if (givenEntity == null) {
            throw new IllegalArgumentException();
        }
        givenEntity.click(character);
    }

    /**
     * Do the periodic spawns of mercenaries and hydras
     */
    public void doSpawns() {
        if (tick != 0 && tick % 30 == 0 && SpawnManager.checkValidSpawn(entities, entry)) {
            entities.add(entityFactory.create("mercenary", entry, "", "", 1));
        }
        if (tick != 0 && tick % 50 == 0 && gameMode.getMode().equals("Hard")) {
            Position p = SpawnManager.getRandPosition(entities, currRandom);
            if (p != null) {entities.add(entityFactory.create("hydra", p, "", "", 0));}
        }
    }

    /**
     * Spawn spiders onto the map randomly,
     */
    private void spawnSpiders() {
        for (int n = 0; n < 5 && entities.search("spider").size() < 4; n++) {
            Position p = SpawnManager.getRandPosition(entities, currRandom);
            if (p != null) {entities.add(entityFactory.create("spider", p, "", "", 1));}
        }
    }

    /**
     * Given the type string of an item, attempt to build it
     * @param item The type string of the item to be built
     */
    public void build(String item) {
        BuildableEntity target = BuildableEntity.findBuildable(item);
        // check if target is a buildable type
        if (target == null) {
            throw new IllegalArgumentException();
        }
        // check if buildable type has the materials
        else if (BuildableEntity.getBuildables(getInventory(), entities).contains(item)) {
            target.resetDurability();
            character.addItemToInventory(target);
            character.consume(target.getRecipeUsed());
        }
        else {
            throw new InvalidActionException("Missing required items");
        }
    }

    /**
     * Given a gamemode string, create the gamemode object
     * @param gameMode The string name
     * @return The gamemode obejct
     * @throws IllegalArgumentException
     */
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

    /**
     * Create the entitiesMap from a dungeon.json file
     * @param output The ArrayList of entities to be output to
     * @param dungeonName The filename of the dungeon (without the .json)
     * @throws IllegalArgumentException when the Dungeon filename could not be found
     */
    private void createNewEntitiesMap(ArrayList<Entity> output, String dungeonName) throws IllegalArgumentException {
        String currFileStr;
        try {
            currFileStr = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (IOException | NullPointerException e) {
            throw new IllegalArgumentException("Dungeon does not exist");
        }
        JSONArray currEntities = new JSONObject(currFileStr).getJSONArray("entities");
        entitiesFromJSON(currEntities,output);
    }

    /**
     * Given a JSONArray of Entity JSONObjects, create them and add them to the input arrayList of Entities
     * @param currEntities The JSONArray of Entity JSONObjects
     * @param input The object to be replaced with all the new entities
     */
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

    /**
     * Given a JSONArray of inventory items, create a new ArrayList of CollectableEntities
     * @param currInventory The JSONArray of inventory items
     * @return a new ArrayList of CollectableEntities with only items from the JSON
     */
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
