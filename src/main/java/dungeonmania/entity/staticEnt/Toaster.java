package dungeonmania.entity.staticEnt;

import dungeonmania.util.Position;

public class Toaster extends StaticEntity{
    private int tickTilSpawn;

    public Toaster(Position position, int tickTilSpawn){
        super(new Position(position.getX(), position.getY(), Integer.MAX_VALUE)); 
        this.tickTilSpawn = tickTilSpawn;    
    }
}
