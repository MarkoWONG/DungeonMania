package dungeonmania.entity.collectables.buildable;

import dungeonmania.entity.collectables.Weapon;
import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Bow extends BuildableEntity implements Weapon {

    private Integer durability;
    private static final int startingDurability = 3;
    private List<String> recipeUsed;

    public Bow() {
        // times 2 as items are used twice per battle one for attacking and one for defending
        this.durability = startingDurability * 2;
        this.recipeUsed = new ArrayList<String>();
    }

    public boolean usedInBattle(PlayerCharacter player){
        durability--;
        if (durability <= 0){
            return true;
        }
        return false;
    }

    public boolean buildable(ArrayList<String> inventory, EntityList entities) {
        if (materialCounter(inventory, "wood") >= 1 && materialCounter(inventory, "arrow") >= 3){
            recipeUsed = Arrays.asList("wood", "arrow", "arrow", "arrow");
            return true;
        }
        return false;
    }

    @Override
    public String getType() {
        return "bow";
    }

    @Override
    public int usedInAttack(int attackDamage) {
        return attackDamage * 2;
    }

    public List<String> getRecipeUsed() {
        return this.recipeUsed;
    }
    
    public Integer getDurability() {
        return durability;
    }
}
