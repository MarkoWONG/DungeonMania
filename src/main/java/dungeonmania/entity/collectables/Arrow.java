package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;

public class Arrow extends CollectableEntity {
    public Arrow(Position position){
        super(new Position(position.getX(), position.getY(), 40));
    }

    @Override
    public String getType() {
        return null;
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
