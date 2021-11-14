package dungeonmania.mobs;

import dungeonmania.EntityList;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Spider extends Mob {
    private int positionCounter = 998;
    private int moveDirection = 1; // 1 for movement clockwise, -1 for anticlockwise
    private EntityList entities;
    /**
     * Spider position guide
     * 7 > 0 > 1
     * ^   ^   v
     * 6  -1   2
     * ^       v
     * 5 < 4 < 3
     * Starts at -1, then moves to 0 and starts going in a circle
     */

     public Spider(Position position, int health, int ad, EntityList entities) {
        super(new Position(position.getX(), position.getY(),80));
        setAttackDamage(ad);
        setHealth(health);
        this.entities = entities;
     }

    @Override
    public String getType() {
        return "spider";
    }

    @Override
    public void move(Direction direction) {
        Boolean boulder;
        if (moveDirection == -1) {
            positionCounter--;
        }else {
            positionCounter++;
        }
        if (positionCounter != 999) {
            positionCounter = (((positionCounter % 8) + 8) % 8);
        }
        switch(positionCounter) {
            case 999:
                boulder = MovementManager.checkBoulder(super.getPosition().translateBy(Direction.UP), entities);
                if (!boulder) {
                    super.move(Direction.UP);
                    positionCounter = -1;
                } else {
                    positionCounter = 998;
                }
                break;
            case 0 :
                boulder = MovementManager.checkBoulder(super.getPosition().translateBy(Direction.RIGHT), entities);
                if (boulder) {
                    moveDirection *= -1;
                    super.move(Direction.LEFT);
                    positionCounter = positionCounter - 5;
                } else {
                    super.move(Direction.RIGHT);
                }
                break;
            case 1 :
                boulder = MovementManager.checkBoulder(super.getPosition().translateBy(Direction.DOWN), entities);
                if (boulder) {
                    moveDirection *= -1;
                    super.move(Direction.UP);
                    positionCounter = positionCounter - 5;
                } else {
                    super.move(Direction.DOWN);
                }
                break;
            case 2 :
                boulder = MovementManager.checkBoulder(super.getPosition().translateBy(Direction.DOWN), entities);
                if (boulder) {
                    moveDirection *= -1;
                    super.move(Direction.UP);
                    positionCounter = positionCounter - 5;
                } else {
                    super.move(Direction.DOWN);
                }
                break;
            case 3 :
                boulder = MovementManager.checkBoulder(super.getPosition().translateBy(Direction.LEFT), entities);
                if (boulder) {
                    moveDirection *= -1;
                    super.move(Direction.RIGHT);
                    positionCounter = positionCounter - 5;
                } else {
                    super.move(Direction.LEFT);
                }
                break;
            case 4 :
                boulder = MovementManager.checkBoulder(super.getPosition().translateBy(Direction.LEFT), entities);
                if (boulder) {
                    moveDirection *= -1;
                    super.move(Direction.RIGHT);
                    positionCounter = positionCounter - 5;
                } else {
                    super.move(Direction.LEFT);
                }
                break;
            case 5 :
                boulder = MovementManager.checkBoulder(super.getPosition().translateBy(Direction.UP), entities);
                if (boulder) {
                    moveDirection *= -1;
                    super.move(Direction.RIGHT);
                    positionCounter = positionCounter - 5;
                } else {
                    super.move(Direction.UP);
                }
                break;
            case 6 :
                boulder = MovementManager.checkBoulder(super.getPosition().translateBy(Direction.UP), entities);
                if (boulder) {
                    moveDirection *= -1;
                    super.move(Direction.RIGHT);
                    positionCounter = positionCounter - 5;
                } else {
                    super.move(Direction.UP);
                }
                break;
            case 7 :
                boulder = MovementManager.checkBoulder(super.getPosition().translateBy(Direction.RIGHT), entities);
                if (boulder) {
                    moveDirection *= -1;
                    super.move(Direction.LEFT);
                    positionCounter = positionCounter - 5;
                } else {
                    super.move(Direction.RIGHT);
                }
                break;
        }
    }

    @Override
    public void teleport(Position position) {
        // does nothing.
    }
}
