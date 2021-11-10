package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;

public class SunStone extends CollectableEntity implements BribeMaterial{

    public SunStone(Position position){
        super(new Position(position.getX(), position.getY(), 40));
    }
    public SunStone(){
        super(null);
    }

    @Override
    public String getType() {
        return "sun_stone";
    }

    public int getBribeAmount(int price){
        return 1;
    }
    public void usedInBribe(PlayerCharacter player){
        player.removeItemFromInventory(this);
    }
    public int getBribePriority(){
        return 2;
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
