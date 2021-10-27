package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;

public class Portal extends StaticEntity{
    
    public Portal(Position position){
        super(new Position(position.getX(), position.getY(), 0));     
    }
}