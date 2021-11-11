package dungeonmania.entity.collectables.potion;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;

public class InvincibilityPotion extends PotionEntity implements Usable{
    private static final int effectDuration = 5;
    private boolean enabled;

    public InvincibilityPotion(Position position, boolean enabled){
        super(new Position(position.getX(), position.getY(), 40));
        this.enabled = enabled;
    }

    public InvincibilityPotion(boolean enabled) {
        super(null);
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
            player.setInvincibleTicks(effectDuration);
        }
        player.removeItemFromInventory(this);
    }

    @Override
    public String getType() {
        return "invincibility_potion";
    }

}
