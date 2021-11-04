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
        super(new Position(position.getX(), position.getY(), 80));
        this.isOpen = false;  
        this.key = !key.isBlank() ? Integer.parseInt(key) : -1;
    }

    public void unlock(){
        this.isOpen = true;
    }

    @Override
    public String getType() {
        return "door";
    }


    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    /**
     * Attempts to unlock door
     * @param player
     */
    public boolean unlockDoor(PlayerCharacter player){
        if (isOpen) {
            return true;
        }
        for (CollectableEntity item : player.getInventory()){
            if (item.getType().equals("key")){
                Key playerKey = (Key) item;
                if (playerKey.getKeyIdentifer() == key){
                    player.removeItemFromInventory(item);
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