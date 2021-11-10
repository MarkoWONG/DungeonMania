package dungeonmania.entity.collectables;

import dungeonmania.util.Position;

public class Anduril extends Sword{
    public Anduril(Position position){
        super(position);
    }
    public Anduril() {
        super();
    }

    @Override
    public String getType() {
        return "anduril";
    }

    @Override
    public int usedInAttack(int attackDamage) {
        return attackDamage + 4;
    }
}
