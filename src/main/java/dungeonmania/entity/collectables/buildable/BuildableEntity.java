package dungeonmania.entity.collectables.buildable;
import dungeonmania.EntityList;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class BuildableEntity extends CollectableEntity{
    private static final List<BuildableEntity> buildables = Arrays.asList(new Bow(), new Shield(), new MidnightArmour(), new Sceptre());
    
    public BuildableEntity(){
        super(null);
    }

    public abstract boolean buildable(ArrayList<String> inventory, EntityList entities);

    public int materialCounter(ArrayList<String> inventory, String material){
        int counter = 0;
        for (String mat : inventory){
            if (mat.equals(material)){
                counter++;
            }
        }
        return counter;
    }
    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    public abstract List<String> getRecipeUsed();

    public static BuildableEntity findBuildable(String item){
        for (BuildableEntity buildableEnt : buildables){
            if (buildableEnt.getType().equals(item)){
                return buildableEnt;
            }
        }
        return null;
    }

    /**
     * returns the currently craftable items
     * @param inventory
     * @return list of builable types
     */
    public static List<String> getBuildables(ArrayList<CollectableEntity> inventory, EntityList entities) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<String> currInv = (ArrayList<String>) inventory.stream().map(Entity::getType).collect(Collectors.toList());
        for (BuildableEntity buildableEnt : buildables){
            if (buildableEnt.buildable(currInv, entities)){
                output.add(buildableEnt.getType());
            }
        }
        return output;
    }
}
