package dungeonmania.entity.collectables;

import dungeonmania.PlayerCharacter;
import dungeonmania.util.Position;

public class Sword extends CollectableEntity{

    private int durability;

    public Sword(Position position){
        super(new Position(position.getX(), position.getY(), 0), "sword");
        this.durability = 3;
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        player.addItemToInventory(this);
    }
}
