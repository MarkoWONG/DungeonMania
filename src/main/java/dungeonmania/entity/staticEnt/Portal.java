package dungeonmania.entity.staticEnt;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.Mercenary;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Locale;

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
        if (colour.equals("")) {
            return "portal";
        } else {
            return "portal_" + getOtherInfo().toLowerCase(Locale.ROOT);
        }

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
        ArrayList<Entity> matchingPortals = entities.search("portal_"+ getOtherInfo().toLowerCase(Locale.ROOT));
        for (Entity eachPortal : matchingPortals) {
            ((Portal) eachPortal).setOtherPortalPosition(this.getPosition());
            return eachPortal.getPosition();
        }
        return this.getPosition(); // dont teleport if no matching portal
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

    //Setters 
    public void setOtherPortalPosition(Position otherPortalPosition) {
        this.otherPortalPosition = otherPortalPosition;
    }

    
}