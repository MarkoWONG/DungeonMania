package dungeonmania.entity.staticEnt;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;


public abstract class StaticEntity extends Entity{

    public StaticEntity(Position position){
        super(position);
    }
    public void interact(Boulder boulder){
        // do nothing
    };
}
