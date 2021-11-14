package dungeonmania.entity.collectables.potion;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;


public class InvisibilityPotion extends CollectableEntity implements Usable{
    private static final int effectDuration = 5;
    
    public InvisibilityPotion(Position position){
        super(new Position(position.getX(), position.getY(), 40));
    }

    public InvisibilityPotion() {
        super(null);
    }

    @Override
    public String getType() {
        return "invisibility_potion";
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
        player.setInvisibleTicks(effectDuration);
        player.removeItemFromInventory(this);
    }

}
