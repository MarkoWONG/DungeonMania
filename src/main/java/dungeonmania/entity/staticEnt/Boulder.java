package dungeonmania.entity.staticEnt;
import dungeonmania.Movement;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity implements Movement{
    
    public Boulder(Position position){
        super(new Position(position.getX(), position.getY(), Integer.MAX_VALUE), "boulder");     
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        //TODO: move(player.getLastMoveDirection);
    }
    //move method
    public void move(Direction playerDirection){
        Position newBoulderPostion = this.getPosition().translateBy(playerDirection);
        if (validPos(newBoulderPostion)){
            this.setPosition(newBoulderPostion);
        }
    }

    // TODO: Checking if the new boulder position is valid Need to have a list of surrounding entities
    private boolean validPos(Position pos){
        boolean valid = true;
        for (Entity ent : entitiesMap.getPosition(pos)){
            if (ent.getType().equals("switch")){
                valid = true;
            }
            else {
                valid = false;
            }
        }
        return valid;
    }

}