package dungeonmania.entity.collectables;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.Mercenary;
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

    /**
     * Marks the CollectableEntity as having been used in a battle by a player
     * @param player The player that has used the item
     * @return Whether the item was used
     */
    public boolean usedInBattle(PlayerCharacter player) {
        return false; // default case for each collectable
    }

    /**
     * Marks the CollectableEntity has having been used in a battle by a zombie
     * @param zombie The zombie that has used the item
     * @return
     */
    public boolean usedInBattle(ZombieToast zombie) {
        return false;
    }

    /**
     * Marks the CollectableEntity has having been used in a battle by a mercenary
     * @param mercenary The mercenary that has used the item
     * @return Whether the item was used
     */
    public boolean usedInBattle(Mercenary mercenary) {
        return false;
    }

    /**
     * Uses the item in battle, for the sake of calculating new damage values
     * @param attackDamage The current attack damage
     * @return The new attack damage
     */
    public int usedInAttack(int attackDamage) {
        return attackDamage;
    }

    /**
     * Uses the item in battle, for the sake of calculating reduced damage
     * @param damage The current damage to be taken
     * @return The new damage after defense
     */
    public int usedInDefense(int damage) {
        return damage;
    }


}
