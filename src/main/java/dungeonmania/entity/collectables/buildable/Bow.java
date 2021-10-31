package dungeonmania.entity.collectables.buildable;

import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bow extends CollectableEntity {

    public Bow() {
        super(null);
    }




    public static List<String> getRecipe() {
        return Arrays.asList("wood","arrow","arrow");
    }

    @Override
    public String getType() {
        return "bow";
    }

    @Override
    public int usedInAttack(int attackDamage) {
        return attackDamage * 2;
    }
}
