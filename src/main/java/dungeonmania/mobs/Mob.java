package dungeonmania.mobs;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.faction.IFaction;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.movement.Movement;
import dungeonmania.mobs.faction.*;

public abstract class Mob extends Entity implements Movement{
    private Integer health;
    private Integer attackDamage;
    private Faction faction;
    private Position position;

    public Mob (Position position) {
        super(position);
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
        setPosition(getPosition().translateBy(d));
    }

    /**
     * teleports the mob to the given location
     * If mob cannot teleport, this is overridden to do nothing.
     */
    @Override
    public void teleport(Position p) {
        // just a standard teleport
        setPosition(p);
    }

    public boolean isEnemy() {
        return faction.isEnemy();
    }

    public boolean isAlly() {
        return faction.isAlly();
    }

    public void changeFaction(String newFaction) {
        if (newFaction.equals("ally")) {
            faction.setFaction(new Ally());
        } else if (newFaction.equals("enemy")) {
            faction.setFaction(new Enemy());
        }
    }

    @Override
    public Integer getHealth() {
        return health;
    }
    public int attack() {
        return (int)attackDamage;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }
    
    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
    }
}
