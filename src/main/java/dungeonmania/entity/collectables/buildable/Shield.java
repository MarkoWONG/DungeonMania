package dungeonmania.entity.collectables.buildable;

import dungeonmania.PlayerCharacter;
import dungeonmania.entity.collectables.CollectableEntity;

import java.util.Arrays;
import java.util.List;

public class Shield extends CollectableEntity {

    private Integer durability;

    public Shield() {
        super(null);
        this.durability = 3 * 2;
    }

    public static List<String> getRecipe() {
        return Arrays.asList("wood","wood","treasure");
    }

    public static List<String> getRecipe2() {
        return Arrays.asList("wood","wood","key");
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

}
