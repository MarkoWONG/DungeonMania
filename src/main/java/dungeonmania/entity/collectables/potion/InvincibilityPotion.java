package dungeonmania.entity.collectables.potion;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import java.util.HashMap;
import java.util.ArrayList;

public class InvincibilityPotion extends PotionEntity implements Usable{

    private boolean enabled;

    public InvincibilityPotion(Position position, boolean enabled){
        super(new Position(position.getX(), position.getY(), 40));
        this.enabled = enabled;
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
        if (enabled) {
            player.setInvincibleTicks(5);
            player.removeItemFromInventory(this);
        }
    }

    @Override
    public String getType() {
        return "invincibility_potion";
    }

}
