package dungeonmania.entity.staticEnt;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;
import dungeonmania.mobs.Mob;
import java.util.Random;
import java.util.ArrayList;

public class Toaster extends StaticEntity{
    private int tickTilSpawn;
    private int currentTickCount;
    private EntityList entityList;
    private Random currRandom;
    public Toaster(Position position, int tickTilSpawn, EntityList entityList, Random currRandom){
        super(new Position(position.getX(), position.getY(), 80));
        this.tickTilSpawn = tickTilSpawn;    
        this.currentTickCount = 0;
        this.entityList = entityList;
        this.currRandom = currRandom;
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public boolean isInteractable() {
        return true;
    }

    @Override
    public String getType() {
        return "zombie_toast_spawner";
    }

    @Override
    public void incrementTick(){
        currentTickCount++;
        if (currentTickCount >= tickTilSpawn){
            ZombieToast newZombie = new ZombieToast(new Position(this.getXPos(), this.getYPos(), 50), 10,2,currRandom);
            // check if a valid position is possible
            ArrayList<Integer> validDirIndexes = new ArrayList<Integer>();
            // -1 because we don't want NONE Direction
            for (Integer dir_index = 0; dir_index < Direction.values().length - 1; dir_index++){
                if (MovementManager.staticCheckMove(newZombie, Direction.values()[dir_index],entityList)){
                    validDirIndexes.add(dir_index);
                }
            }
            
            // spawn zombie if valid
            if (validDirIndexes.size() > 0){
                Direction validDir = randomDirection(validDirIndexes);
                newZombie.move(validDir);
                entityList.add(newZombie);
            }
            else{
                // remove newZombie as none was created
                newZombie = null;
            }
            currentTickCount = 0;
        }
    }

    private Direction randomDirection(ArrayList<Integer> validDirIndexes) {
        int pick = currRandom.nextInt(validDirIndexes.size());
        Direction ranDir = Direction.values()[validDirIndexes.get(pick)];
        return ranDir;
    }

    @Override
    public void click(PlayerCharacter character) {
        for (Position adjPositions : this.getPosition().getAdjacentPositions() ) {
            if (character.getPosition().equals(adjPositions.asLayer(50)) && character.hasWeapon()) {
                entityList.remove(this);
            }
        }
    }
}
