package dungeonmania.entity;

public class HardEntityFactory extends EntityFactory{

    @Override
    protected Entity makeToaster() {
        return new Toaster(15);
    }
}
