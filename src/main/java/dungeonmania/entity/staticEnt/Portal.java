package dungeonmania.entity.staticEnt;

import dungeonmania.EntityList;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.util.Position;

import java.util.HashMap;
import java.util.ArrayList;

public class Portal extends StaticEntity{

    private EntityList entities;
    private String colour;
    private Position otherPortalPosition;

    public Portal(Position position, String colour, EntityList entityMap){
        super(new Position(position.getX(), position.getY(), 80));
        this.colour = colour;
        this.entities = entityMap;
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
        ArrayList<Entity> allPortals = entities.search("portal");
        for (Entity eachPortal : allPortals) {
            if (eachPortal.getOtherInfo().equals(colour)) {
                ((Portal) eachPortal).setOtherPortalPosition(this.getPosition());
                return eachPortal.getPosition();
            }
        }
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