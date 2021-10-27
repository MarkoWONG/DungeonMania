package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;

public class Wall extends StaticEntity{
    public Wall(Position position){
        super(new Position(position.getX(), position.getY(), Integer.MAX_VALUE));     
    }
}
