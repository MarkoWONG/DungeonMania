package dungeonmania.entity.staticEnt;

import dungeonmania.entity.Entity;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Mobs.Zombie;
import java.util.HashMap;
import java.util.ArrayList;

public class Portal extends StaticEntity{

    private HashMap<Position, ArrayList<Entity>> entityMap;
    private Position otherPortalPosition;

    public Portal(Position position, String colour, HashMap<Position, ArrayList<Entity>> entityMap){
        super(new Position(position.getX(), position.getY(), 80), "portal", colour);     
        this.entityMap = entityMap;
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

    // portal should search for another portal on creation, if found it will set it's other portal reference, and also notify the other portal of its existence
    
}