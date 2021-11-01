package dungeonmania.mobs;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Spider extends Mob {
    private int positionCounter = -1;
    /**
     * Spider position guide
     * 7 > 0 > 1
     * ^   ^   v
     * 6  -1   2
     * ^       v
     * 5 < 4 < 3
     * Starts at -1, then moves to 0 and starts going in a circle
     */

     public Spider(Position position, int health, int ad) {
        super(new Position(position.getX(), position.getY(),80));
        setAttackDamage(ad);
        setHealth(health);
     }

    @Override
    public String getType() {
        return "spider";
    }

    @Override
    public void move(Direction direction) {
        switch(positionCounter) {
            case -1:
                super.move(Direction.UP);
                break;
            case 0 :
                super.move(Direction.RIGHT);
                break;
            case 1 :
                super.move(Direction.DOWN);
                break;
            case 2 :
                super.move(Direction.DOWN);
                break;
            case 3 :
                super.move(Direction.LEFT);
                break;
            case 4 :
                super.move(Direction.LEFT);
                break;
            case 5 :
                super.move(Direction.UP);
                break;
            case 6 :
                super.move(Direction.UP);
                break;
            case 7 :
                super.move(Direction.RIGHT);
                break;
        }
        positionCounter = (positionCounter + 1) % 8;
    }

    @Override
    public void teleport(Position position) {
        // does nothing.
    }
}
