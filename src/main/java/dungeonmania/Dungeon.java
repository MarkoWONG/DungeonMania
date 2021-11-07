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
import dungeonmania.entity.collectables.buildable.Build;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goal.GoalManager;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Dungeon {

    private String name;
    private String id;
    private Position entry;

    private Difficulty gameMode;
    private PlayerCharacter character;
    private EntityList entities;
    private MovementManager movementManager;
    private FightManager fightManager;
    private GoalManager goalManager;
    private EntityFactory entityFactory;

    public Dungeon(String dungeonName, String gameMode) {
        this.name = dungeonName;
        this.id = UUID.randomUUID().toString();
        this.entities = new EntityList();
        this.goalManager = new GoalManager(dungeonName,this);
        this.movementManager = new MovementManager(entities);
        this.fightManager = new FightManager(entities);
        this.gameMode = difficultySelector(gameMode);
        this.entityFactory = this.gameMode.createEntityFactory(entities);
        createNewEntitiesMap(entities, dungeonName);
        this.entry = character.getPosition();
        fightManager.setCharacter(character);
        movementManager.setCharacter(character);
    }

    public Dungeon (JSONObject saveGame) {
        this(saveGame.getString("name"),saveGame.getString("gamemode"));
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
        // for(int i = entities.size() - 1; i >= 0; --i) {
        //     entities.get(i).incrementTick();
        // }
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


    public void build(String item) {
        if (!item.equals("bow") && !item.equals("shield")) {
            throw new IllegalArgumentException();
        }
        if (Build.getBuildables(getInventory()).contains(item)) {
            // can be safely typecast because we check if it's a valid item in the controller?
            character.addItemToInventory((CollectableEntity) entityFactory.create(item, null,null,null));
            character.consume(Build.getRecipe(item));
        } else {
            throw new InvalidActionException("Missing required items");
        }

    }

    // will always be given a valid string, we do the checking in the controller
    private Difficulty difficultySelector(String gameMode) throws IllegalArgumentException {
        switch (gameMode) {
            case ("Peaceful"):
                return new Peaceful(this,movementManager,fightManager);
            case ("Standard"):
                return new Standard(this,movementManager,fightManager);
            case ("Hard"):
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
            Entity currEnt = entityFactory.create(currEntType, currPosition,currEntColour,currDoorKey);
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
            Entity currEnt = entityFactory.create(currEntType, null,null,currDoorKey);
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
        return character.getBuildables();
    }



}
