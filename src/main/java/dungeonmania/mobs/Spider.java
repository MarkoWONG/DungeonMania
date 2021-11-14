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
        Direction pos = getMovePos();
        Direction neg = getMoveNeg();
        Position newPos = super.getPosition().translateBy((moveDirection == 1) ? pos : neg);
        
        if (MovementManager.checkBoulder(newPos, entities)) {
            moveDirection *= -1;
        }

        super.move((moveDirection == 1) ? pos : neg);

        positionCounter = modulus(positionCounter + moveDirection, 8);
    }

    /**
     * @return the direction to move when moving clockwise
     */
    private Direction getMovePos(){
        
        switch(positionCounter) {
            case -1:
            return Direction.UP;
            case 0 :
            return Direction.RIGHT;
            case 1 :
            return Direction.DOWN;
            case 2 :
            return Direction.DOWN;
            case 3 :
            return Direction.LEFT;
            case 4 :
            return Direction.LEFT;
            case 5 :
            return Direction.UP;
            case 6 :
            return Direction.UP;
            case 7 :
            return Direction.RIGHT;
        }

        return Direction.NONE;
    }

    /**
     * @return the direction to move when moving anti-clockwise
     */
    private Direction getMoveNeg(){
        
        switch(positionCounter) {
            case 0 :
             return Direction.LEFT;
            case 1 :
            return Direction.LEFT;
            case 2 :
            return Direction.UP;
            case 3 :
            return Direction.UP;
            case 4 :
            return Direction.RIGHT;
            case 5 :
            return Direction.RIGHT;
            case 6 :
            return Direction.DOWN;
            case 7 :
            return Direction.DOWN;
        }

        return Direction.NONE;
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
