package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;

public class Boulder extends StaticEntity{
    
    public Boulder(Position position){
        super(new Position(position.getX(), position.getY(), Integer.MAX_VALUE));     
    }

    //move method
    

}