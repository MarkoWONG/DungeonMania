package dungeonmania.entity.collectables;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.Mercenary;
import dungeonmania.mobs.Mob;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.util.Position;

public abstract class CollectableEntity extends Entity{
    public CollectableEntity(Position position){
        super(position);
    }
    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    public boolean usedInBattle(PlayerCharacter player) {
        return false; // default case for each collectable
    }

    public boolean usedInBattle(ZombieToast zombie) {
        return false;
    }

    public boolean usedInBattle(Mercenary mercenary) {
        return false;
    }

    public int usedInAttack(int attackDamage) {
        return attackDamage;
    }

    public int usedInDefense(int damage) {
        return damage;
    }


}
