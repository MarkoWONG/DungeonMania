package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;
import dungeonmania.entity.Entity;

public class Wall extends StaticEntity{
    public Wall(Position position){
        super(new Position(position.getX(), position.getY(), 80), "wall");     
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }
}
