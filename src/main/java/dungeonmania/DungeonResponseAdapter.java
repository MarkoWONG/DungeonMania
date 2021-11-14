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

    private Dungeon dungeon;

    public DungeonResponseAdapter(Dungeon dungRef) {
        this.dungeon = dungRef;
    }

    /**
     * Create a dungeonResponse from the current instance Dungeon object
     * @return DungeonResponse of the dungeon's state
     */
    public DungeonResponse createDungResponse() {
        String id = dungeon.getId();
        String name = dungeon.getName();;
        List<EntityResponse> entities = doEntities();
        List<ItemResponse> inventory = doItems();
        List<String> buildables = dungeon.getBuildables();
        String goals = dungeon.getGoals();
        return new DungeonResponse(id,name,entities,inventory,buildables,goals);
    }

    /**
     * Convert Dungeon's Entities to a list of EntityResponses
     * @return List of all EntityResponses in the dungeon
     */
    private List<EntityResponse> doEntities() {
        ArrayList<EntityResponse> output = new ArrayList<>();
        for (Entity eachEntity : dungeon.getEntities() ) {
            output.add(new EntityResponse(eachEntity.getId(),eachEntity.getType(),eachEntity.getPosition(),eachEntity.isInteractable()));
        }
        return output;
    }

    /**
     * Convert each item in the player's inventory to a list of ItemResponses
     * @return A list of ItemResponses in the players inventory
     */
    private List<ItemResponse> doItems() {
        ArrayList<ItemResponse> output = new ArrayList<>();
        for (Entity eachItem : dungeon.getInventory()) {
            output.add(new ItemResponse(eachItem.getId(), eachItem.getType()));
        }
        return output;
    }



}
