package dungeonmania.entity.collectables;

import dungeonmania.entity.Entity;
import dungeonmania.PlayerCharacter;
import dungeonmania.util.Position;

public class Sword extends CollectableEntity implements Weapon{

    private int durability;

    public Sword(Position position, int durability){
        super(new Position(position.getX(), position.getY(), 40));
        this.durability = durability;
    }

    public Sword(int durability) {
        super(null);
        this.durability = durability;
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
}
