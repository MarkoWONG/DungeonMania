package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;
// import dungeonmania.difficulty.Difficulty;

public class Wall extends StaticEntity{
    public Position getPostion(){
        System.out.println("created Wall");
        return new Position(2,4);
    }
    public void interact(){
        
    }
}
