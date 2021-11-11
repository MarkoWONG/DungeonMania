package dungeonmania.entity.staticEnt;

import dungeonmania.entity.Entity;
import dungeonmania.movement.Movement;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity implements Movement{
    
    public Boulder(Position position){
        super(new Position(position.getX(), position.getY(),100) );
    }

    @Override
    public String getType() {
        return "boulder";
    }


    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

 
    @Override
    public void move(Direction playerDirection){
        Position newBoulderPostion = this.getPosition().translateBy(playerDirection);
        this.setPosition(newBoulderPostion);
    }

    @Override
    public void teleport(Position position) {
        this.setPosition(position);
    }

}