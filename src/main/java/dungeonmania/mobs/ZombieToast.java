package dungeonmania.mobs;

import dungeonmania.entity.collectables.Armour;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.movement.Movement;
import java.util.Random;

public class ZombieToast extends Mob{

    private Armour armour;

    public ZombieToast(Position position) {
        super(position);
        Random rand = new Random(System.currentTimeMillis());
        if (rand.nextInt(5) == 4) {
            armour = new Armour();
        } else {
            armour = null;
        }
    }

    @Override
    public String getType() {
        return "zombie_toast";
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
