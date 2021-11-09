package dungeonmania.entity.collectables.buildable;

import java.util.Arrays;
import java.util.List;
import dungeonmania.EntityList;
import java.util.ArrayList;

public class Spectre extends BuildableEntity{

    private List<String> recipeUsed;

    public Spectre() {
        this.recipeUsed = new ArrayList<String>();
    }

    @Override
    public String getType() {
        return "spectre";
    }

    public boolean buildable(ArrayList<String> inventory, EntityList entities) {
        if (materialCounter(inventory, "sun_stone") >= 1){
            if (materialCounter(inventory, "wood") >= 1){
                if (materialCounter(inventory, "key") >= 1){
                    recipeUsed = Arrays.asList("sun_stone","wood", "key");
                    return true;
                }
                else if (materialCounter(inventory, "treasure") >= 1){
                    recipeUsed = Arrays.asList("sun_stone","wood", "treasure");
                    return true;
                }
                else{
                    return false;
                }
            }
            if (materialCounter(inventory, "arrow") >= 2){
                if (materialCounter(inventory, "key") >= 1){
                    recipeUsed = Arrays.asList("sun_stone","arrow", "arrow", "key");
                    return true;
                }
                else if (materialCounter(inventory, "treasure") >= 1){
                    recipeUsed = Arrays.asList("sun_stone","arrow", "arrow", "treasure");
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
        }
        return false;
    }

    public List<String> getRecipeUsed() {
        return this.recipeUsed;
    }
}
