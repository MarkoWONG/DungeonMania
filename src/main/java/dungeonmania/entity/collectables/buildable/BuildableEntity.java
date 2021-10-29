package dungeonmania.entity.collectables.buildable;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;
import dungeonmania.entity.collectables.CollectableEntity;

public abstract class BuildableEntity extends CollectableEntity{
    public BuildableEntity(Position position, String type){
        super(position, type);
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }
}
