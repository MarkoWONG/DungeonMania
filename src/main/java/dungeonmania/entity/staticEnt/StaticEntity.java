package dungeonmania.entity.staticEnt;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;


public abstract class StaticEntity extends Entity{

    public StaticEntity(Position position, String type){
        super(position, type);
    }

    public StaticEntity(Position position, String type, String colour){
        super(position, type, colour);
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }
}
