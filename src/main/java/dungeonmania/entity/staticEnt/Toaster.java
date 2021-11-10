package dungeonmania.entity.staticEnt;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.RandomManager;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.util.Position;
import dungeonmania.util.Direction;
import dungeonmania.mobs.Mob;
import java.util.ArrayList;

public class Toaster extends StaticEntity{
    private int tickTilSpawn;
    private int currentTickCount;
    private EntityList entityList;
    public Toaster(Position position, int tickTilSpawn, EntityList entityList){
        super(new Position(position.getX(), position.getY(), 80));
        this.tickTilSpawn = tickTilSpawn;    
        this.currentTickCount = 0;
        this.entityList = entityList;
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
            ZombieToast newZombie = new ZombieToast(this.getPosition(),10,2);
            // check if a valid position is possible
            ArrayList<Integer> validDirIndexes = new ArrayList<Integer>();
            // -1 because we don't want NONE Direction
            for (Integer dir_index = 0; dir_index < Direction.values().length - 1; dir_index++){
                if (checkMove(newZombie, Direction.values()[dir_index])){
                    validDirIndexes.add(dir_index);
                }
            }
            
            // spawn zombie if valid
            if (validDirIndexes.size() > 0){
                Direction validDir = randomDirection(validDirIndexes.size());
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

    private Direction randomDirection(int size) {
        int pick = RandomManager.nextInt(size);
        return Direction.values()[pick];
    }

    private Boolean checkMove(Entity entity, Direction direction) {
        Position newEntityPosition = entity.getPosition().translateBy(direction);
        ArrayList<Entity> tile = entityList.search(newEntityPosition);
        for (Entity eachEntity : tile) {
            if (eachEntity.getPosition().getLayer() >= entity.getPosition().getLayer()) {
                if (eachEntity instanceof Mob || eachEntity instanceof PlayerCharacter) {
                    return true;
                }
                return false;
            }
        }
        return true;
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
