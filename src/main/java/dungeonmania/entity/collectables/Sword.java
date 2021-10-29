package dungeonmania.entity.collectables;

import dungeonmania.entity.Entity;
import dungeonmania.PlayerCharacter;
import dungeonmania.util.Position;

public class Sword extends CollectableEntity{

    private int durability;

    public Sword(Position position, int durability){
        super(new Position(position.getX(), position.getY(), 40), "sword");
        this.durability = durability;
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        player.addItemToInventory(this);
        player.setAttackDamage(player.getAttackDamage() + 2);
    }

    public void usedInBattle(PlayerCharacter player){
        durability--;
        if (durability <= 0){
            for (CollectableEntity ent : player.getInventory()){
                if (ent.equals(this)){
                    player.getInventory().remove(ent);
                }
            }
        }
    }
}
