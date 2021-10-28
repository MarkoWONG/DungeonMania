package dungeonmania.entity.staticEnt;

import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.entity.Mob.Zombie;
import dungeonmania.util.Position;

import java.util.HashMap;
import java.util.ArrayList;


public class Toaster extends StaticEntity{
    private int tickTilSpawn;
    private int currentTickCount;
    private HashMap<Position, ArrayList<Entity>> entitiesMap;
    public Toaster(Position position, int tickTilSpawn, HashMap<Position, ArrayList<Entity>> entitiesMap){
        super(new Position(position.getX(), position.getY(), 80), "toaster"); 
        this.tickTilSpawn = tickTilSpawn;    
        this.currentTickCount = 0;
        this.entitiesMap = entitiesMap;
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }


    @Override
    public void incrementTick(){
        currentTickCount++;
        if (currentTickCount >= tickTilSpawn){
            new Zombie(this.getPosition());
            currentTickCount = 0;
        }
    }

    // Destory toaster if player with sword is adject to toaster 
    public void destoryToaster(){
        for (Entity ent : entitiesMap.get(this.getPosition())){
            if (ent.getType().equals("zombie_toast_spawner")){
                entitiesMap.get(this.getPosition()).remove(ent);
            }
        }
        // for (Position checkPos : this.getPosition().getAdjacentPositions()){
        //     for (Entity ent : entitiesMap.get(checkPos)){
        //         for ()
        //         if (ent.getType().equals("player") && playerHasSword(ent)){
        //             return true;
        //         }
        //     }
        // }
        // return false;
    }
    // // Need access to player Inventory OR have this function in PlayerCharater class 
    // private boolean playerHasSword(Entity playerEnt){
    //     PlayerCharacter player = (PlayerCharacter) playerEnt;
    //     for (CollectableEntity item : player.getInventory()){
    //         if (item.getType().equals("sword")){
    //             return true;
    //         }
    //     }
    //     return false;
    // }
}
