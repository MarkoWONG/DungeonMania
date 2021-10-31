package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import java.util.HashMap;
import java.util.ArrayList;

public class Bomb extends CollectableEntity implements Usable{
    private HashMap<Position, ArrayList<Entity>> entityMap;

    public Bomb(Position position, HashMap<Position, ArrayList<Entity>> entityMap){
        super(new Position(position.getX(), position.getY(), 40));
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
        if (!entityMap.containsKey(currPosition)) {
            entityMap.put(currPosition, new ArrayList<Entity>());
        }
        entityMap.get(currPosition).add( new PlacedBomb(currPosition, entityMap));
        player.removeItemFromInventory(this);
    }

    @Override
    public String getType() {
        return "bomb";
    }

}
