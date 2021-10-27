package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;

public class Exit extends StaticEntity{
    public Position getPostion(){
        System.out.println("created Exit");
        return new Position(2,4);
    }
    public void interact(){
        
    }
}
