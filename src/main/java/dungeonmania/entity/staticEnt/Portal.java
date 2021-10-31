package dungeonmania.entity.staticEnt;

import dungeonmania.entity.Entity;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.util.Position;

import java.util.HashMap;
import java.util.ArrayList;

public class Portal extends StaticEntity{

    private HashMap<Position, ArrayList<Entity>> entityMap;
    private String colour;
    private Position otherPortalPosition;

    public Portal(Position position, String colour, HashMap<Position, ArrayList<Entity>> entityMap){
        super(new Position(position.getX(), position.getY(), 80));
        this.colour = colour;
        this.entityMap = entityMap;
        this.otherPortalPosition = findOtherPortal(colour);
    }

    @Override
    public String getType() {
        return "portal";
    }

    public String getOtherInfo() {
        return colour;
    }

    // portal should search for another portal on creation, if found it will set it's other portal reference, and also notify the other portal of its existence
    private Position findOtherPortal(String colour){
        for (Position pos : entityMap.keySet()){
            for (Entity ent : entityMap.get(pos)){
                if (ent.getType().equals("portal") && ent.getOtherInfo().equals(colour) && !ent.getId().equals(getId())){
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
    public void interact(ZombieToast zombie){
        ;
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