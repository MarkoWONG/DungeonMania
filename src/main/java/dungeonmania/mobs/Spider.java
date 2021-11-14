package dungeonmania.mobs;

import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Spider extends Mob {
    private int positionCounter = -1;
    private int moveDirection = 1; // 1 for movement clockwise, -1 for anticlockwise
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
        Boolean boulder = MovementManager.checkBoulder(super.getPosition(), direction);
        switch(positionCounter) {
            case -1:
            if (!boulder) {
                super.move(Direction.UP);
            }
            break;
            case 0 :
            if (boulder) {
                positionCounter *= -1;
                super.move(Direction.LEFT);
            } else {
                super.move(Direction.RIGHT);
            }
            break;
            case 1 :
            if (boulder) {
                positionCounter *= -1;
                super.move(Direction.UP);
            } else {
                super.move(Direction.DOWN);
            }
            break;
            case 2 :
            if (boulder) {
                positionCounter *= -1;
                super.move(Direction.UP);
            } else {
                super.move(Direction.DOWN);
            }
            break;
            case 3 :
            if (boulder) {
                positionCounter *= -1;
                super.move(Direction.RIGHT);
            } else {
                super.move(Direction.LEFT);
            }
            break;
            case 4 :
            if (boulder) {
                positionCounter *= -1;
                super.move(Direction.RIGHT);
            } else {
                super.move(Direction.LEFT);
            }
            break;
            case 5 :
            if (boulder) {
                positionCounter *= -1;
                super.move(Direction.RIGHT);
            } else {
                super.move(Direction.UP);
            }
            break;
            case 6 :
            if (boulder) {
                positionCounter *= -1;
                super.move(Direction.RIGHT);
            } else {
                super.move(Direction.UP);
            }
            break;
            case 7 :
            if (boulder) {
                positionCounter *= -1;
                super.move(Direction.LEFT);
            } else {
                super.move(Direction.RIGHT);
            }
            break;
        }
        positionCounter = positionCounter + moveDirection;
        if (positionCounter < 0 || positionCounter > 7) {
            positionCounter = (((positionCounter % 8) + 8) % 8);
        }
    }

    @Override
    public void teleport(Position position) {
        // does nothing.
    }
}
