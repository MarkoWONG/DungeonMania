package dungeonmania.entity.Mob;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;

public abstract class MobEntity extends Entity{
    public MobEntity(Position position, String type){
        super(position, type);
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

}
