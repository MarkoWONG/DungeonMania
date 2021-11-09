package dungeonmania.entity.collectables.buildable;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MidnightArmour extends BuildableEntity{

    private List<String> recipeUsed;

    public MidnightArmour() {
        // times 2 as items are used twice per battle one for attacking and one for defending
        this.recipeUsed = new ArrayList<String>();

    }

    public static List<String> getRecipe() {
        return Arrays.asList("sun_stone","armour");
    }

    @Override
    public String getType() {
        return "midnight_armour";
    }

    public boolean buildable(ArrayList<String> inventory) {
        if (
            materialCounter(inventory, "sun_stone") >= 1 && 
            materialCounter(inventory, "armour") >= 1 &&
            checkNozombies()
        ){
            recipeUsed = Arrays.asList("sun_stone","armour");
            return true;
        }
        return false;
    }

    private static boolean checkNozombies(){
        //TODO
        return true;
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
