package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;

import java.util.ArrayList;
import java.util.List;

// Very loosely follows the Adapter Pattern
// really doesnt follow it all, but it still does the adapting
public class DungeonResponseAdapter {

    private transient Dungeon dungeon;

    public DungeonResponseAdapter(Dungeon dungRef) {
        this.dungeon = dungRef;
    }

    public DungeonResponse createDungResponse() {
        String id = dungeon.getId();
        String name = dungeon.getName();;
        List<EntityResponse> entities = doEntities();
        List<ItemResponse> inventory = doItems();
        List<String> buildables = dungeon.getBuildables();
        String goals = dungeon.getGoals();
        return new DungeonResponse(id,name,entities,inventory,buildables,goals);
    }

    private List<EntityResponse> doEntities() {
        ArrayList<EntityResponse> output = new ArrayList<>();
        for (Entity eachEntity : dungeon.getEntities() ) {
            output.add(new EntityResponse(eachEntity.getId(),eachEntity.getType(),eachEntity.getPosition(),eachEntity.isInteractable()));
        }
        return output;
    }

    private List<ItemResponse> doItems() {
        ArrayList<ItemResponse> output = new ArrayList<>();
        for (Entity eachItem : dungeon.getInventory()) {
            output.add(new ItemResponse(eachItem.getId(), eachItem.getType()));
        }
        return output;
    }



}
