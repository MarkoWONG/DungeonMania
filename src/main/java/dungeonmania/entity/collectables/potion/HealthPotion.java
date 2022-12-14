package dungeonmania.entity.collectables.potion;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;


public class HealthPotion extends CollectableEntity implements Usable{


    public HealthPotion(Position position){
        super(new Position(position.getX(), position.getY(), 40));
    }

    public HealthPotion() {
        super(null);
    }

    @Override
    public String getType() {
        return "health_potion";
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
        player.setHealth(10);
        player.removeItemFromInventory(this);
    }
}
