package dungeonmania.entity.staticEnt;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;
import java.util.HashMap;
import java.util.ArrayList;

public class Door extends StaticEntity{

    private HashMap<Position, ArrayList<Entity>> entityMap;
    private Boolean isOpen;

    public Door(Position position, HashMap<Position, ArrayList<Entity>> entityMap){
        super(new Position(position.getX(), position.getY(), 80), "door");   
        this.isOpen = false;  
        this.entityMap = entityMap;
    }

    public void unlockDoor(){
        this.isOpen = true;
        this.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY(), 0));
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    // TODO: same logic as portal only one key link to door

    public Boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}