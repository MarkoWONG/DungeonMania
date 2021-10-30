package dungeonmania.entity.staticEnt;

import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

public class Exit extends StaticEntity{
    public Exit(Position position){
        super(new Position(position.getX(), position.getY(), 0), "exit");     
    }
    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }
}