package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.util.Position;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DungeonJSONAdapter {


    public static JSONObject toJSON(Dungeon currDungeon) {
        JSONObject main = new JSONObject();
        main.put("name",currDungeon.getName());
        main.put("id",currDungeon.getId());
        main.put("gamemode",currDungeon.getGamemode());
        main.put("entities",entitiesToJSON(currDungeon.getEntities()));
        main.put("inventory", InventoryToJSON(currDungeon.getInventory()));
        return main;
    }



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
