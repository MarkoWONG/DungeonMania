package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;

public class Wood extends CollectableEntity{
    public Wood(Position position){
        super(new Position(position.getX(), position.getY(), Integer.MAX_VALUE), "wood");     
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }
}
