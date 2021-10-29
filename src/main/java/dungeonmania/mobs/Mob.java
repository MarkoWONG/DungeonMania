package dungeonmania.mobs;
import dungeonmania.entity.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.movement.Movement;
import dungeonmania.mobs.faction.*;

public abstract class Mob extends Entity implements Movement{
    private double health;
    private double attackDamage;
    private boolean hasFought;
    private Faction faction;

    public Mob () {
        this.faction = new Faction();
        faction.setFaction(new Enemy());
    }

    @Override
    public abstract void move(Direction d);

    @Override
    public void teleport(Position p) {

    }

    public boolean isEnemy() {
        return faction.isEnemy();
    }

    public boolean isAlly() {
        return faction.isAlly();
    }

}
