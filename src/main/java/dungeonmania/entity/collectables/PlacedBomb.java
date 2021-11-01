package dungeonmania.entity.collectables;
import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.staticEnt.Switch;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;

public class PlacedBomb extends Bomb {
    private EntityList entities;
    public PlacedBomb(Position position, EntityList entityMap){
        super(new Position(position.getX(), position.getY(), 100), entityMap);
        this.entities = entityMap;
    }

    @Override
    public void incrementTick(){
        for (Position eachPos : this.getPosition().getAdjacentPositions()) {
            for (Entity eachEntity : entities.search(eachPos)) {
                if (eachEntity.getType().equals("switch") && ((Switch) eachEntity).getSwitchOn()) {
                    denotate();
                }
            }
        }
    }

    // removes all entities directly surrounding bomb (except player)
    private void denotate(){
        for (Position eachPos : this.getPosition().getAdjacentPositions()) {
            for (Entity eachEntity : entities.search(eachPos)) {
                if (!(eachEntity instanceof PlayerCharacter)) {
                    entities.remove(eachEntity);
                }
            }
        }
    }   
}
