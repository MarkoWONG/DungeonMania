package dungeonmania.mobs;
import dungeonmania.entity.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.movement.Movement;
import dungeonmania.mobs.faction.*;

public class Mob extends Entity implements Movement{
    private double health;
    private double attackDamage;
    private Faction faction;
    private Position position;

    public Mob() {
        this.faction = new Faction();
        faction.setFaction(new Enemy());

    }

    /**
     * Movement manager passes a random direction that the mob is able to move in
     * If the mob has special movement, this is overridden and the correct direction
     * is passed here
     */
    @Override
    public void move(Direction d) {
        // just a standard move
        position = position.translateBy(d);
    }

    /**
     * teleports the mob to the given location
     * If mob cannot teleport, this is overridden to do nothing.
     */
    @Override
    public void teleport(Position p) {
        // just a standard teleport
        position = p;
    }

    public boolean isEnemy() {
        return faction.isEnemy();
    }

    public boolean isAlly() {
        return faction.isAlly();
    }

    public void changeFaction(String newFaction) {
        if (newFaction == "ally") {
            faction.setFaction(new Ally());
        } else if (newFaction == "enemy") {
            faction.setFaction(new Enemy());
        }
    }

    public double getHealth() {
        return health;
    }
    public int attack() {
        return (int)attackDamage;
    }

    public void setHealth(double health) {
        this.health = health;
    }
    
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }
}
