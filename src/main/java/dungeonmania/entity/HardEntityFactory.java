package dungeonmania.entity;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class HardEntityFactory extends EntityFactory{

    public HardEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap) {
        super(entityMap);
    }

    @Override
    public Entity makeToaster(Position startPos) {
        return new Toaster(startPos,15);
    }
}
