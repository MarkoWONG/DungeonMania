package dungeonmania.entity.collectables;

import dungeonmania.entity.Entity;
import dungeonmania.PlayerCharacter;
import dungeonmania.mobs.Mob;
import dungeonmania.util.Position;

public class Armour extends CollectableEntity{

    private int durability;
    private static final int startingDurability = 3;

    public Armour() {
        super(null);
        // times 2 as items are used twice per battle one for attacking and one for defending
        this.durability = startingDurability * 2;
    }

    public Armour(Position position){
        super(new Position(position.getX(), position.getY(), 40));
        // times 2 as items are used twice per battle one for attacking and one for defending
        this.durability = startingDurability * 2;
    }

    @Override
    public String getType() {
        return "armour";
    }
    public int getDurability() {
        return durability;
    }

    @Override
    public void startInteraction(Entity entity) {
        entity.interact(this);
    }

    @Override
    public void interact(PlayerCharacter player){
        player.addItemToInventory(this);
    }

    public boolean usedInBattle(PlayerCharacter player){
        durability--;
        // System.out.println("armour durablity is now: " + durability);
        if (durability <= 0){
            return true;
            // player.getInventory().removeIf(ent -> ent.equals(this));
        }
        return false;
    }

    public void usedInBattle(Mob mob) {
        durability--;
        if (durability <= 0) {
            mob.setArmour(null);
        }
    }

    @Override
    public int usedInDefense(int damage) {
        return damage / 2;
    }
}