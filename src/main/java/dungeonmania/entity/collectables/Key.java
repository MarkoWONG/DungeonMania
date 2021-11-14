package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;

public class Key extends CollectableEntity {
    private final int keyIdentifer;
    public Key(Position position, String keyIdentifer){
        super(new Position(position.getX(), position.getY(), 40));
        this.keyIdentifer = !keyIdentifer.isBlank() ? Integer.parseInt(keyIdentifer) : -1;
    }

    public Key(String keyIdentifer) {
        super(null);
        this.keyIdentifer = !keyIdentifer.isBlank() ? Integer.parseInt(keyIdentifer) : -1;
    }

    @Override
    public String getType() {
        return "key";
    }

    public String getOtherInfo() {
        return String.valueOf(keyIdentifer);
    }

    public String getOtherInfoType() {
        return "key";
    }


    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        if (!playerHasKey(player)){
            player.addItemToInventory(this);
        }
    }

    /**
     * Search the player's inventory for any key, returning true if they have one
     * @param player The player to be searched
     * @return Whether the given player has a key in their inventory
     */
    private boolean playerHasKey(PlayerCharacter player){
        for (CollectableEntity ent : player.getInventory()){
            if (ent.getType().equals("key")){
                return true;
            }
        }
        return false;
    }

    /**
     * Return the number ID for which door/key combo this corresponds to
     * @return the key identifier this key belongs to
     */
    public int getKeyIdentifer() {
        return this.keyIdentifer;
    }

}