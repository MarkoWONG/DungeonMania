package dungeonmania.entity.collectables.rare;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;
import dungeonmania.PlayerCharacter;

public class OneRing extends rareEntity{
    public OneRing(Position position){
        super(new Position(position.getX(), position.getY(), 40), "one_ring");  
    }

    @Override
    public String getType() {
        return "one_ring";
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        player.addItemToInventory(this);
    }

    public void useItem(PlayerCharacter player){
        player.removeItemFromInventory(this);
    }
}
