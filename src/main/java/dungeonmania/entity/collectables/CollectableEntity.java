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

    public void usedInBattle(PlayerCharacter player) {
        ; // default case for each collectable
    }

    public void usedInBattle(ZombieToast zombie) {
        ;
    }

    public void usedInBattle(Mercenary mercenary) {
        ;
    }

    public int usedInAttack(int attackDamage) {
        return attackDamage;
    }

    public int usedInDefense(int damage) {
        return damage;
    }


}
