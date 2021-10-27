package dungeonmania.entity;

import dungeonmania.util.Position;

public class HardEntityFactory extends EntityFactory{

    @Override
    public Entity makeToaster(Position startPos) {
        return new Toaster(startPos,15);
    }
}
