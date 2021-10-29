package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import java.util.HashMap;
import java.util.ArrayList;

public class Bomb extends CollectableEntity{
    private HashMap<Position, ArrayList<Entity>> entityMap;

    public Bomb(Position position, HashMap<Position, ArrayList<Entity>> entityMap){
        super(new Position(position.getX(), position.getY(), 40), "bomb");  
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

    public void plantBomb(PlayerCharacter player){
        new PlacedBomb(player.getPosition(), entityMap);
        player.removeItemFromInventory(this);
    }

    //Not sure If this is needed (maybe needed for PlaceBomb)
    @Override
    public void incrementTick(){}
}
