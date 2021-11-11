package dungeonmania.entity.collectables.buildable;

import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Weapon;
import dungeonmania.PlayerCharacter;

import java.util.Arrays;
import java.util.List;

public class Bow extends CollectableEntity implements Weapon {

    private Integer durability;
    private static final int startingDurability = 3;

    public Bow() {
        super(null);
        // times 2 as items are used twice per battle one for attacking and one for defending
        this.durability = startingDurability * 2;
    }

    public boolean usedInBattle(PlayerCharacter player){
        durability--;
        if (durability <= 0){
            return true;
        }
        return false;
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
