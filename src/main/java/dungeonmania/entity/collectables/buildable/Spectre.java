package dungeonmania.entity.collectables.buildable;
import dungeonmania.EntityList;
import dungeonmania.entity.collectables.BribeMaterial;
import dungeonmania.PlayerCharacter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spectre extends BuildableEntity implements BribeMaterial{

    private List<String> recipeUsed;
    private int bribeDuration;

    public Spectre() {
        this.recipeUsed = new ArrayList<String>();
        this.bribeDuration = 10;
    }

    @Override
    public String getType() {
        return "spectre";
    }

    public void usedInBribe(PlayerCharacter player){
       
    }
    public int getBribeAmount(int price){
        return price;
    }
    public int getBribePriority(){
        return 3;
    }

    public int getBribeDuration(){
        return bribeDuration;
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
