package dungeonmania.entity;

import dungeonmania.entity.staticEnt.Toaster;
import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.HashMap;

public class HardEntityFactory extends EntityFactory{
    private HashMap<Position, ArrayList<Entity>> entityMap;

    public HardEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap) {
        super(entityMap);
        this.entityMap = entityMap;
    }

    @Override
    protected Entity makeToaster(Position StartPos) {
        return new Toaster(StartPos, 15, entityMap);
    }
}
