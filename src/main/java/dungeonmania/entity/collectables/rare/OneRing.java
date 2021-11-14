package dungeonmania.entity.collectables.rare;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;
import dungeonmania.PlayerCharacter;

public class OneRing extends CollectableEntity {
    public OneRing(Position position){
        super(new Position(position.getX(), position.getY(), 40));
    }

    public OneRing() {
        super(null);
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

    /**
     * Use the item as a bribe, removing itself from the given player's inventory
     * @param playerCharacter The character currently being controlled
     */
    public void usedInBribe(PlayerCharacter playerCharacter) {
        playerCharacter.removeItemFromInventory(this);
    }
}
