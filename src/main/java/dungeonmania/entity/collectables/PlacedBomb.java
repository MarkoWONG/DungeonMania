package dungeonmania.entity.collectables;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;
import dungeonmania.entity.staticEnt.Switch;
import java.util.ArrayList;
import java.util.HashMap;

public class PlacedBomb extends Bomb {
    private HashMap<Position, ArrayList<Entity>> entityMap;

    public PlacedBomb(Position position, HashMap<Position, ArrayList<Entity>> entityMap){
        super(new Position(position.getX(), position.getY(), 100), entityMap);
        this.entityMap = entityMap;
    }

    @Override
    public void incrementTick(){
        for (Position checkPos : this.getPosition().getAdjacentPositions()){
            for (Entity ent : entityMap.get(checkPos)){
                if (ent.getType().equals("switch")){
                    Switch sw = (Switch) ent;
                    if (sw.getSwitchOn()){
                        denotate();
                    }
                }
            }
        }
    }

    // removes all entities directly surrounding bomb (except player)
    private void denotate(){
        for (Position checkPos : this.getPosition().getAdjacentPositions()){
            boolean playerOnTile = false;
            for (Entity ent : entityMap.get(checkPos)){
                if (!ent.getType().equals("player")){
                    entityMap.get(checkPos).remove(ent);
                }
                else{
                    playerOnTile = true;
                }
            }
            if (!playerOnTile){
                entityMap.remove(checkPos);
            }
        }
    }   
}