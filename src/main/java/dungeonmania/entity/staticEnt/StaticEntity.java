package dungeonmania.entity.staticEnt;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;


public abstract class StaticEntity extends Entity{

    public StaticEntity(Position position, String type){
        super(position, type);
    }

    public StaticEntity(Position position, String type, String otherInfo){
        super(position, type, otherInfo);
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }
}
