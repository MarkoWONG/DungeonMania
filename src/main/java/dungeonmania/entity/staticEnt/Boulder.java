package dungeonmania.entity.staticEnt;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity{
    public Position getPostion(){
        System.out.println("created Boulder");
        return new Position(2,4);
    }
    public void interact(){
        
    }
}
