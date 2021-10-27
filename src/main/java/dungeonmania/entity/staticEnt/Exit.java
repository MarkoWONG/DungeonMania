package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;

public class Exit extends StaticEntity{
    public Exit(Position position){
        super(new Position(position.getX(), position.getY(), 0));     
    }
    
}