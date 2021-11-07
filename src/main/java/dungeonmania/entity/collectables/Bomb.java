package dungeonmania.entity.collectables;
import dungeonmania.EntityList;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import java.util.HashMap;
import java.util.ArrayList;

public class Bomb extends CollectableEntity implements Usable{
    private EntityList entityMap;

    public Bomb(Position position, EntityList entityMap){
        super(new Position(position.getX(), position.getY(), 40));
        this.entityMap = entityMap;   
    }

    public Bomb(EntityList entityMap) {
        super(null);
        this.entityMap = entityMap;
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
        Position currPosition = player.getPosition();
        entityMap.add(new PlacedBomb(currPosition,entityMap));
        player.removeItemFromInventory(this);
    }

    @Override
    public String getType() {
        return "bomb";
    }

}
