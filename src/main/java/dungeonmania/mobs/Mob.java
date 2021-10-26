package dungeonmania.mobs;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class Mob {
    private double health;
    private double attackDamage;
    private boolean hasFought;
    private Faction faction;

    public Mob () {
        this.faction = new Faction();
        faction.setFaction(new Enemy());
    }

    public abstract void move(Direction d);

    public void teleport(Position p) {

    }

    public boolean isEnemy() {
        return false;
    }

    public boolean isAlly() {
        return false;
    }

}
