package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;


public class Treasure extends CollectableEntity implements BribeMaterial{
    public Treasure(Position position){
        super(new Position(position.getX(), position.getY(), 40));
    }

    public Treasure() {
        super(null);
    }

    @Override
    public String getType() {
        return "treasure";
    }

    public int getBribeAmount(int price){
        return 1;
    }
    public void usedInBribe(PlayerCharacter player){
        player.removeItemFromInventory(this);
    }
    public int getBribePriority(){
        return 1;
    }
    public int getBribeDuration(){
        return -1;
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        player.addItemToInventory(this);
    }

}