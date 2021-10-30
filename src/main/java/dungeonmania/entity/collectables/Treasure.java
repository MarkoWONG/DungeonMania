package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;


public class Treasure extends CollectableEntity implements Craftable{
    public Treasure(Position position){
        super(new Position(position.getX(), position.getY(), 40), "treasure");     
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