package dungeonmania.entity.collectables.buildable;

import dungeonmania.PlayerCharacter;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.staticEnt.Exit;
import dungeonmania.entity.staticEnt.Wall;
import dungeonmania.util.Position;

import java.util.Arrays;
import java.util.List;

public class Shield extends CollectableEntity {

    private Integer durability;

    public Shield(Integer durability) {
        super(null);
        this.durability = durability;
    }

    public static List<String> getRecipe() {
        return Arrays.asList("wood","wood","treasure");
    }

    public void usedInBattle(PlayerCharacter player){
        durability--;
        if (durability <= 0){
            player.getInventory().removeIf(ent -> ent.equals(this));
        }
    }

    @Override
    public String getType() {
        return "shield";
    }

}