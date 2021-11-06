package dungeonmania.mobs;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.movement.Movement;
import dungeonmania.mobs.faction.*;

public abstract class Mob extends Entity implements Movement{
    private Integer health;
    private Integer attackDamage;
    private Faction faction;
    private Armour armour;

    public Mob (Position position) {
        super(position);
        this.faction = new Faction();
        faction.setFaction(new Enemy());
    }

    public void setArmour(Armour armour) {
        this.armour = armour;
    }

    public Armour getArmour() {
        return armour;
    }

    public void takeDamage(int damage) {
        int reducedDamage = damage;
        if (armour!= null) {
            reducedDamage = armour.usedInDefense(reducedDamage);
            armour.usedInBattle(this);
        }
        setHealth(getHealth() - damage);
    }

    public void startFight(PlayerCharacter playerCharacter) {
        playerCharacter.fight(this); //example override for playerCharacter
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

    public void setAttackDamage(Integer attackDamage) {
        this.attackDamage = attackDamage;
    }

    @Override
    public Integer getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

}
