package dungeonmania.mobs;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.util.Position;

import java.util.Random;

import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;

public class Mercenary extends Mob{
    private int price;
    private Position charPosition;
    private int battleRadius;
    private Boolean battleInRadius;
    private Armour armour;

    public Mercenary(Position position) {
        super(position);
        Random rand = new Random();
        if (rand.nextInt(5) == 4) {
            armour = new Armour();
        } else {
            armour = null;
        }
    }

    @Override
    public boolean isInteractable() {
        return true;
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
    public void takeDamage(int damage) {
        int reducedDamage = damage;
        if (armour != null) {
            reducedDamage = armour.usedInDefense(reducedDamage);
            armour.usedInBattle(this);
        }
        super.takeDamage(reducedDamage);
    }

    @Override
    public String getType() {
        return "mercenary";
    }

    @Override
    public void move(Direction direction) {
        super.move(MovementManager.shortestPath(super.getPosition(), charPosition));
    }
}
