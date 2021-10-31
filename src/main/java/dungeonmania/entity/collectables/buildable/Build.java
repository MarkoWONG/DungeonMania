package dungeonmania.entity.collectables.buildable;

import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Build {
    // returns the currently craftable items
    // how do you do this without these if statements?? aahhhhh
    public static List<String> getBuildables(ArrayList<CollectableEntity> inventory) {
        ArrayList<String> output = new ArrayList<>();
        ArrayList<String> currInv = (ArrayList<String>) inventory.stream().map(Entity::getType).collect(Collectors.toList());
        if (currInv.containsAll(Bow.getRecipe())) {
            output.add("bow");
        }
        if (currInv.containsAll(Shield.getRecipe())) {
            output.add("shield");
        }
        return output;
    }

    public static List<String> getRecipe(String item) {
        switch (item) {
            case "bow":
                return Bow.getRecipe();
            case "shield":
                return Shield.getRecipe();
            default:
                return new ArrayList<String>();
        }
    }
}