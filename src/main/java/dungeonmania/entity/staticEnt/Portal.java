package dungeonmania.entity.staticEnt;

import dungeonmania.entity.Entity;
import dungeonmania.entity.Mob.Zombie;
import dungeonmania.util.Position;

import java.util.HashMap;
import java.util.ArrayList;

public class Portal extends StaticEntity{

    private HashMap<Position, ArrayList<Entity>> entityMap;
    private Position otherPortalPosition;

    public Portal(Position position, String colour, HashMap<Position, ArrayList<Entity>> entityMap){
        super(new Position(position.getX(), position.getY(), 80), "portal", colour);     
        this.entityMap = entityMap;
        this.otherPortalPosition = findOtherPortal(colour);
    }

    // portal should search for another portal on creation, if found it will set it's other portal reference, and also notify the other portal of its existence
    private Position findOtherPortal(String colour){
        for (Position pos : entityMap.keySet()){
            for (Entity ent : entityMap.get(pos)){
                if (ent.getType().equals("portal") && ent.getOtherInfo().equals(colour) && !ent.equals(this)){
                    Portal otherPortal = (Portal) ent;
                    otherPortal.setOtherPortalPosition(this.getPosition());
                    return pos;
                }
            }
        }
        // for when the other portal does not exist yet
        return null;
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }


    @Override
    public void interact(Zombie zombie){
        // DO Nothing
    }

    @Override
    public void interact(Entity Entity){
        Entity.setPosition(otherPortalPosition);
    }

    //Getters and Setters 
    public Position getOtherPortalPosition() {
        return this.otherPortalPosition;
    }

    public void setOtherPortalPosition(Position otherPortalPosition) {
        this.otherPortalPosition = otherPortalPosition;
    }

    
}