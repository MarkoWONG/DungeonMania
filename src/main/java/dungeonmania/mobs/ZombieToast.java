package dungeonmania.mobs;

import dungeonmania.entity.collectables.Armour;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.movement.Movement;
import java.util.Random;

public class ZombieToast extends Mob{

    public ZombieToast(Position position, int health, int ad) {
        super(new Position(position.getX(), position.getY(),50));
        setAttackDamage(ad);
        setHealth(health);
        Random rand = new Random(System.currentTimeMillis());
        if (rand.nextInt(5) == 4) {
            setArmour(new Armour());
        } else {
            setArmour(null);
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
        if (getArmour() != null) {
            reducedDamage = getArmour().usedInDefense(reducedDamage);
            getArmour().usedInBattle(this);
        }
        super.takeDamage(reducedDamage);
    }
}
