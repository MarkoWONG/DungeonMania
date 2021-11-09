package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;

public class SunStone extends CollectableEntity{

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

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        player.addItemToInventory(this);
    }

}
