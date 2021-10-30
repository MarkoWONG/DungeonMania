package dungeonmania.entity.collectables.potion;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Usable;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import java.util.HashMap;
import java.util.ArrayList;


public class HealthPotion extends PotionEntity implements Usable{

    private boolean healthPotionUsed;
    private HashMap<Position, ArrayList<Entity>> entityMap;

    public HealthPotion(Position position, HashMap<Position, ArrayList<Entity>> entityMap){
        super(new Position(position.getX(), position.getY(), 40), "health_potion");  
        this.healthPotionUsed = false;   
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
        if (!healthPotionUsed){
            disableHealthPotion(player);
            player.setHealth(10);
            player.removeItemFromInventory(this);
        }
    }

    /**
     * disables all health potion in game
     * @param player
     */
    private void disableHealthPotion(PlayerCharacter player){
        // set all healthPotion on map (not picked up) to do nothing
        for (Position pos : entityMap.keySet()){
            for (Entity ent : entityMap.get(pos)){
                if (ent.getType().equals("health_potion")){
                    HealthPotion otherHealthPotion = (HealthPotion) ent;
                    otherHealthPotion.setHealthPotionUsed(true);
                }
            }
        }
        // set all healthPotion on map (not picked up) to do nothing
        for (CollectableEntity ent : player.getInventory()){
            if (ent.getType().equals("health_potion")){
                HealthPotion otherHealthPotion = (HealthPotion) ent;
                otherHealthPotion.setHealthPotionUsed(true);
            }
        }
    }

    public boolean getHealthPotionUsed() {
        return this.healthPotionUsed;
    }

    public void setHealthPotionUsed(boolean healthPotionUsed) {
        this.healthPotionUsed = healthPotionUsed;
    }
}
