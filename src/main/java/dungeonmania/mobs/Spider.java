package dungeonmania.mobs;

import dungeonmania.EntityList;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Spider extends Mob {
    private int positionCounter = -1;
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
        if (MovementManager.checkBoulder(super.getPosition(), entities)) {
            moveDirection *= -1;
        }

        if (moveDirection == 1) {doMovePos();}
        else if (moveDirection == -1) {doMoveNeg();}

        positionCounter = modulus(positionCounter + moveDirection, 8);
    }

    private void doMovePos(){
        
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
    }

    
    private void doMoveNeg(){
        
        switch(positionCounter) {
            case -1:
            break;
            case 0 :
            super.move(Direction.LEFT);
            break;
            case 1 :
            super.move(Direction.LEFT);
            break;
            case 2 :
            super.move(Direction.UP);
            break;
            case 3 :
            super.move(Direction.UP);
            break;
            case 4 :
            super.move(Direction.RIGHT);
            break;
            case 5 :
            super.move(Direction.RIGHT);
            break;
            case 6 :
            super.move(Direction.DOWN);
            break;
            case 7 :
            super.move(Direction.DOWN);
            break;
        }
    }

    @Override
    public void teleport(Position position) {
        // does nothing.
    }

    private int modulus(int x, int y) {
        int remainder = x % y;
        int mod = (remainder < 0) ? (y + remainder) : remainder;
        return mod;
    }

}
