package dungeonmania.entity.collectables.buildable;

import dungeonmania.PlayerCharacter;

import java.util.Arrays;
import java.util.ArrayList;
import dungeonmania.EntityList;
import java.util.List;

public class Shield extends BuildableEntity {

    private Integer durability;
    private static final int startingDurability = 3;
    private List<String> recipeUsed;

    public Shield() {
        // times 2 as items are used twice per battle one for attacking and one for defending
        this.durability = startingDurability * 2;
        this.recipeUsed = new ArrayList<String>();
    }

    public boolean buildable(ArrayList<String> inventory, EntityList entities) {
        if (materialCounter(inventory, "wood") >= 2){
            if (materialCounter(inventory, "treasure") >= 1){
                recipeUsed = Arrays.asList("wood","wood","treasure");
                return true;
            }
            else if (materialCounter(inventory, "key") >= 1){
                recipeUsed = Arrays.asList("wood","wood","key");
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    public boolean usedInBattle(PlayerCharacter player){
        durability--;
        if (durability <= 0){
            return true;
        }
        return false;
    }

    @Override
    public String getType() {
        return "shield";
    }

    @Override
    public int usedInDefense(int damage) {
        return damage / 2;
    }

    public List<String> getRecipeUsed() {
        return this.recipeUsed;
    }

}
