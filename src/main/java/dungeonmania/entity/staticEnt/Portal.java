package dungeonmania.entity.staticEnt;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.Mercenary;
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

    public String getOtherInfoType() {
        return "colour";
    }

    /**
     * Find the other portal location if any
     * @param colour
     * @return other portal location
     */
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
    public void interact(PlayerCharacter player){
        player.setPosition(new Position(otherPortalPosition.getX(), otherPortalPosition.getY(), player.getPosition().getLayer()));
    }

    @Override
    public void interact(Mercenary mercenary){
        mercenary.setPosition(new Position(otherPortalPosition.getX(), otherPortalPosition.getY(), mercenary.getPosition().getLayer()));
    }

    @Override
    public void interact(Boulder boulder){
        boulder.setPosition(new Position(otherPortalPosition.getX(), otherPortalPosition.getY(), boulder.getPosition().getLayer()));
    }

    @Override
    public void interact(Spider spider){
        spider.setPosition(new Position(otherPortalPosition.getX(), otherPortalPosition.getY(), spider.getPosition().getLayer()));
    }

    //Getters and Setters 
    public Position getOtherPortalPosition() {
        return this.otherPortalPosition;
    }

    public void setOtherPortalPosition(Position otherPortalPosition) {
        this.otherPortalPosition = otherPortalPosition;
    }

    
}