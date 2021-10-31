package dungeonmania.mobs;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.movement.Movement;
import java.util.Random;

public class ZombieToast extends Mob{
    
    public ZombieToast() {
        super();
        Random rand = new Random(System.currentTimeMillis());
        if (rand.nextInt(5) == 4) {
            armour = new Armour();
        } else {
            armour = null;
        }
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

    @Override
    public void takeDamage(int damage) {
        int reducedDamage = damage;
        if (armour != null) {
            reducedDamage = armour.usedInDefense(reducedDamage);
            armour.usedInBattle(this);
        }
        super.takeDamage(reducedDamage);
    }
}
