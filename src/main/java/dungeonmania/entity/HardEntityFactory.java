package dungeonmania.entity;

import dungeonmania.entity.staticEnt.Toaster;
import dungeonmania.util.Position;

public class HardEntityFactory extends EntityFactory{

    @Override
    protected Entity makeToaster(Position StartPos) {
        return new Toaster(StartPos, 15);
    }
}
