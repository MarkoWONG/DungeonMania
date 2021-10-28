package dungeonmania.entity.collectables;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity{
    public CollectableEntity(Position position, String type){
        super(position, type);
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }
}
