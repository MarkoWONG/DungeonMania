package dungeonmania.entity.staticEnt;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.Mercenary;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.mobs.Spider;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class Portal extends StaticEntity{

    private EntityList entities;
    private String colour;
    private Position otherPortalPosition;

    public Portal(Position position, String colour, EntityList entityMap){
        super(new Position(position.getX(), position.getY(), 45));
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
                System.out.println("linked up portal");
                return eachPortal.getPosition();
            }
        }
        System.out.println("no other portal found yet");
        return null;
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        System.out.println("teleporting to " + otherPortalPosition.toString());
        player.setPosition(otherPortalPosition);
    }

    @Override
    public void interact(Mercenary mercenary){
        System.out.println("teleporting to " + otherPortalPosition.toString());
        mercenary.setPosition(otherPortalPosition);
    }

    @Override
    public void interact(Boulder boulder){
        System.out.println("teleporting to " + otherPortalPosition.toString());
        boulder.setPosition(otherPortalPosition);
    }

    @Override
    public void interact(Spider spider){
        System.out.println("teleporting to " + otherPortalPosition.toString());
        spider.setPosition(otherPortalPosition);
    }

    //Getters and Setters 
    public Position getOtherPortalPosition() {
        return this.otherPortalPosition;
    }

    public void setOtherPortalPosition(Position otherPortalPosition) {
        this.otherPortalPosition = otherPortalPosition;
    }

    
}