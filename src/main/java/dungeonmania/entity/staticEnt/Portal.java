package dungeonmania.entity.staticEnt;

import dungeonmania.entity.Entity;
import dungeonmania.util.Position;
import dungeonmania.PlayerCharacter;

public class Portal extends StaticEntity{

    public Portal(Position position){
        super(new Position(position.getX(), position.getY(), 0), "portal");     
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        // TODO: Get location of other portal
        Position portalPosition = null;
        // TODO: update player position to other portal location + 1 step out (according to the MoveDirection)
        Position newPlayerPostion = portalPostition.translateBy(playerDirection);
        player.setPosition(newPlayerPostion);
    }

}