package dungeonmania.entity.collectables.buildable;

import java.util.Arrays;
import java.util.List;
import dungeonmania.EntityList;
import java.util.ArrayList;

public class MidnightArmour extends BuildableEntity{

    private List<String> recipeUsed;

    public MidnightArmour() {
        this.recipeUsed = new ArrayList<String>();
    }

    public static List<String> getRecipe() {
        return Arrays.asList("sun_stone","armour");
    }

    @Override
    public String getType() {
        return "midnight_armour";
    }

    public boolean buildable(ArrayList<String> inventory, EntityList entities) {
        if (
            materialCounter(inventory, "sun_stone") >= 1 && 
            materialCounter(inventory, "armour") >= 1 &&
            checkNozombies(entities)
        ){
            recipeUsed = Arrays.asList("sun_stone","armour");
            return true;
        }
        return false;
    }

    private boolean checkNozombies(EntityList entities){
        if (entities.search("zombie_toast").size() == 0){
            return true;
        }
        return false;
    }

    @Override
    public int usedInAttack(int attackDamage) {
        return attackDamage + 5;
    }

    @Override
    public int usedInDefense(int damage) {
        return damage - 5;
    }

    public List<String> getRecipeUsed() {
        return this.recipeUsed;
    }
}
