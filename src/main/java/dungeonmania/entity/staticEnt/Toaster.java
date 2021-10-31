package dungeonmania.entity.staticEnt;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.util.Position;

import java.util.HashMap;
import java.util.ArrayList;

import static java.lang.Math.abs;


public class Toaster extends StaticEntity{
    private int tickTilSpawn;
    private int currentTickCount;
    private EntityList entityMap;
    public Toaster(Position position, int tickTilSpawn, EntityList entityMap){
        super(new Position(position.getX(), position.getY(), 80));
        this.tickTilSpawn = tickTilSpawn;    
        this.currentTickCount = 0;
        this.entityMap = entityMap;
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
            new ZombieToast(this.getPosition(),10,2);
            currentTickCount = 0;
        }
    }

    @Override
    public void click(PlayerCharacter character) {
        for (Position adjPositions : this.getPosition().getAdjacentPositions() ) {
            if (character.getPosition().equals(adjPositions.asLayer(50)) && character.hasWeapon()) {
                entityMap.remove(this);
            }
        }
    }
}
