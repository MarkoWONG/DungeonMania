package dungeonmania.entity.collectables.potion;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

import dungeonmania.entity.collectables.CollectableEntity;

public abstract class PotionEntity extends CollectableEntity{
    public PotionEntity(Position position){
        super(position);
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }
}
