package dungeonmania.entity.collectables.potion;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import java.util.HashMap;
import java.util.ArrayList;

public class InvincibilityPotion extends PotionEntity implements Usable{
   
    private HashMap<Position, ArrayList<Entity>> entityMap;
    private int effectTime;

    public InvincibilityPotion(Position position, HashMap<Position, ArrayList<Entity>> entityMap){
        super(new Position(position.getX(), position.getY(), 40), "invincibility_potion");  
        this.effectTime = -1;
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
        player.setInvincible(true);
        effectTime = 5;
        player.removeItemFromInventory(this);
    }

    @Override
    public void incrementTick(){
        if (effectTime > 0){
            effectTime--;
        }
        else if (effectTime == 0) {
            PlayerCharacter player = findPlayer();
            player.setInvincible(false);
        }
    }

    private PlayerCharacter findPlayer(){
        for (Position pos : entityMap.keySet()){
            for (Entity ent : entityMap.get(pos)){
                if (ent.getType().equals("player")){
                    PlayerCharacter player = (PlayerCharacter) ent;
                    return player;
                }
            }
        }
        return null;
    }
}
