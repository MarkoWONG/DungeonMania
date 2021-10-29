package dungeonmania.mobs;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.movement.Movement;

public class ZombieToast extends Mob{
    
    public ZombieToast() {
        super();
    }
    
    @Override
    public void move(Direction d) {
        // get list of possible moves
        // generate a random number within bounds of list length
        // move in corresponding direction
    }

    @Override
    public void teleport(Position p) {
        // does nothing
    }
}
