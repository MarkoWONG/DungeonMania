package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DungeonJSONAdapter {

    /**
     * Given a Dungeon object, create a JSONObject containing it's state
     * @param currDungeon Any Dungeon Object
     * @return The JSONObject of the Dungeon's state
     */
    public static JSONObject toJSON(Dungeon currDungeon) {
        JSONObject main = new JSONObject();
        main.put("name",currDungeon.getName());
        main.put("id",currDungeon.getId());
        main.put("gamemode",currDungeon.getGamemode());
        main.put("seed",currDungeon.getCurrSeed());
        main.put("entities",entitiesToJSON(currDungeon.getEntities()));
        main.put("inventory", InventoryToJSON(currDungeon.getInventory()));
        return main;
    }


    /**
     * Given an ArrayList of Entities, convert it to JSONArray form
     * @param entities Any ArrayList of Entities
     * @return JSONArray of Entity JSONObjects
     */
    private static JSONArray entitiesToJSON(ArrayList<Entity> entities) {
        JSONArray output = new JSONArray();
        for (Entity eachEntity : entities) {
            JSONObject temp = new JSONObject();
            temp.put("x",eachEntity.getXPos());
            temp.put("y",eachEntity.getYPos());
            temp.put("type",eachEntity.getType());
            temp.put(eachEntity.getOtherInfoType(),eachEntity.getOtherInfo());
            output.put(temp);
        }
        return output;
    }

    /**
     * Given an ArrayList of CollectableEntities, convert it to JSONArray Form
     * @param inventory An ArrayList of CollectableEntities
     * @return JSONArray of CollectableEntities
     */
    private static JSONArray InventoryToJSON(ArrayList<CollectableEntity> inventory) {
        JSONArray output = new JSONArray();
        for (CollectableEntity eachItem : inventory) {
            JSONObject temp = new JSONObject();
            temp.put("type",eachItem.getType());
            temp.put(eachItem.getOtherInfoType(),eachItem.getOtherInfo());
            output.put(temp);
        }
        return output;
    }


}
