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
        this.durability = startingDurability;
    }

    public Armour(Position position){
        super(new Position(position.getX(), position.getY(), 40));
        this.durability = startingDurability;
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
        if (durability <= 0){
            return true;
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