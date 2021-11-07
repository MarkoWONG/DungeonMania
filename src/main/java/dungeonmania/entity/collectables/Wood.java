package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;

public class Wood extends CollectableEntity{
    public Wood(Position position){
        super(new Position(position.getX(), position.getY(), 40));
    }

    public Wood() {
        super(null);
    }

    @Override
    public String getType() {
        return "wood";
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
