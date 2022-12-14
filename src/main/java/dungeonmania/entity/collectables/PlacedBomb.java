package dungeonmania.entity.collectables;
import com.fasterxml.jackson.annotation.JsonBackReference;
import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.staticEnt.StaticEntity;
import dungeonmania.entity.staticEnt.Switch;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;

public class PlacedBomb extends StaticEntity {
    @JsonBackReference
    private EntityList entities;

    public PlacedBomb(Position position, EntityList entityMap){
        super(new Position(position.getX(), position.getY(), 100));
        this.entities = entityMap;
    }

    @Override
    public String getType() {
        return "bomb";
    }

    @Override
    public void incrementTick(){
        for (Position eachPos : this.getPosition().getCardinallyAdjacentPositions()) {
            for (Entity eachEntity : entities.search(eachPos)) {
                if (eachEntity.getType().equals("switch") && ((Switch) eachEntity).getSwitchOn()) {
                    denotate();
                    return;
                }
            }
        }
    }

    /**
     * When called, destroy all entities surrounding this entity, except the player
     */
    private void denotate(){
        for (Position eachPos : this.getPosition().getAdjacentPositions()) {
            for (Entity eachEntity : entities.search(eachPos)) {
                if (!(eachEntity instanceof PlayerCharacter)) {
                    entities.remove(eachEntity);
                }
            }
        }
        entities.remove(this);
    }   
}
