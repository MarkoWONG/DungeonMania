package dungeonmania.entity.collectables;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity{
    public CollectableEntity(Position position){
        super(position);
    }
    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    public void usedInBattle(PlayerCharacter player) {
        ; // default case for each collectable
    }
}
