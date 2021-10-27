package dungeonmania.entity.staticEnt;

import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

public class Toaster extends StaticEntity{
    private int tickTilSpawn;
    private int currentTickCount;
    public Toaster(Position position, int tickTilSpawn){
        super(new Position(position.getX(), position.getY(), Integer.MAX_VALUE), "toaster"); 
        this.tickTilSpawn = tickTilSpawn;    
        this.currentTickCount = 0;
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    // TODO: Destory toaster if player with sword is adject to toaster
    public boolean destoryToaster(){
        for (Position checkPos : this.getPosition().getAdjacentPositions()){
            for (Entity ent : entitiesMap.getPosition(checkPos)){
                if (ent.getType().equals("player") && playerHasSword(ent)){
                    return true;
                }
            }
        }
        return false;
    }
    // TODO: Need access to player Inventory OR have this function in PlayerCharater class
    private boolean playerHasSword(Entity playerEnt){
        PlayerCharacter player = (PlayerCharacter) playerEnt;
        for (CollectableEntity item : player.getInventory()){
            if (item.getType().equals("sword")){
                return true;
            }
        }
        return false;
    }

    // TODO: spawn zombie every tickTilSpawn Need a way to increment currentTickCount
    // Observer pattern?
    public void spawnZombie(){
        if (currentTickCount >= tickTilSpawn){
            // new zombie
            new Zombie(this.getPosition());
            currentTickCount = 0;
        }
    }
}
