package dungeonmania.entity.collectables.potion;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import java.util.HashMap;
import java.util.ArrayList;


public class HealthPotion extends PotionEntity implements Usable{

    private boolean healthPotionUsed;

    public HealthPotion(Position position){
        super(new Position(position.getX(), position.getY(), 40));
        this.healthPotionUsed = false;
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
        if (!healthPotionUsed){
            player.setHealth(10);
            player.removeItemFromInventory(this);
        }
    }
}
