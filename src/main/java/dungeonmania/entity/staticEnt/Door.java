package dungeonmania.entity.staticEnt;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;
import java.util.HashMap;
import java.util.ArrayList;
import dungeonmania.entity.collectables.Key;

public class Door extends StaticEntity{
    private Boolean isOpen;
    private int key;

    public Door(Position position, String key){
        super(new Position(position.getX(), position.getY(), 80), "door");   
        this.isOpen = false;  
        this.key = Integer.parseInt(key);
    }

    public void unlockDoor(){
        this.isOpen = true;
        this.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY(), 0));
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(Key playerKey){
        if (playerKey.getKeyIdentifer() == key){
            unlockDoor();
        }
    }

    public Boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}