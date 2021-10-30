package dungeonmania.mobs;
import dungeonmania.util.Position;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;

public class Mercenary extends Mob{
    private int price;
    private Position charPosition;
    private int battleRadius;
    private Boolean battleInRadius;

    public Mercenary() {
        super();
    }
    
    /**
     * bribe the mercenary to change its faction. 
     * the amount given is cumilative
     * @param amount an amount of money given
     * @return false if its not enough, true if the merc has become an ally
     */
    public Boolean bribe(int amount) {
        price -= amount;
        if (price > 0) {
            return false;
        }
        super.changeFaction("ally");
        return true;
    }

    @Override
    public void move(Direction direction) {
        super.move(MovementManager.shortestPath(super.getPosition(), charPosition));
    }
}
