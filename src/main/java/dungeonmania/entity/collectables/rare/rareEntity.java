package dungeonmania.entity.collectables.rare;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;
import dungeonmania.entity.collectables.CollectableEntity;

public abstract class rareEntity extends CollectableEntity {
    public rareEntity(Position position, String type){
        super(position, type);
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }
}
