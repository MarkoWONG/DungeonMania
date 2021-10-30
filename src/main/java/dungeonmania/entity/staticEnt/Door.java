package dungeonmania.entity.staticEnt;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Key;

public class Door extends StaticEntity{
    private Boolean isOpen;
    private int key;

    public Door(Position position, String key){
        super(new Position(position.getX(), position.getY(), 80), "door");   
        this.isOpen = false;  
        this.key = Integer.parseInt(key);
    }

    public void unlock(){
        this.isOpen = true;
        this.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY(), 0));
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    /**
     * Attempts to unlock door
     * @param player
     * @return true for succussful unlock, false otherwise
     */
    public boolean unlockDoor(PlayerCharacter player){
        for (CollectableEntity item : player.getInventory()){
            if (item.getType().equals("key")){
                Key playerKey = (Key) item;
                if (playerKey.getKeyIdentifer() == key){
                    unlock();
                    return true;
                }
            }
        }
        return false;
    }

    //Getter and Setter
    public Boolean getIsOpen() {
        return this.isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
}