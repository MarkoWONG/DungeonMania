package dungeonmania.entity.collectables;

import dungeonmania.entity.Entity;
import dungeonmania.PlayerCharacter;
import dungeonmania.util.Position;

public class Sword extends CollectableEntity implements Weapon{

    private int durability;
    private static final int startingDurability = 3;

    public Sword(Position position){
        super(new Position(position.getX(), position.getY(), 40));
        
        // times 2 as items are used twice per battle one for attacking and one for defending
        this.durability = startingDurability * 2; 
    }

    public Sword() {

        super(null);
    }

    @Override
    public String getType() {
        return "sword";
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        player.addItemToInventory(this);
    }

    public boolean usedInBattle(PlayerCharacter player){
        durability--;
        if (durability <= 0){
            return true;
            // player.getInventory().removeIf(ent -> ent.equals(this));
        }
        return false;
    }

    @Override
    public int usedInAttack(int attackDamage) {
        return attackDamage + 2;
    }

    public int getDurability() {
        return durability;
    }
}
