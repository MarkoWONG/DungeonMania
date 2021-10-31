package dungeonmania.entity.Mob;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;

public abstract class Mob extends Entity{
    public Mob(Position position, String type){
        super(position, type);
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

}
