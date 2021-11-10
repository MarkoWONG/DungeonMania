package dungeonmania.mobs;

import java.util.Random;

import dungeonmania.PlayerCharacter;
import dungeonmania.util.Position;

public class Hydra extends Mob{
    private Random rand;

    public Hydra(Position position, int health, int ad) {
        super(new Position(position.getX(), position.getY(),50));
        setAttackDamage(health);
        setAttackDamage(ad);
        rand = new Random(System.currentTimeMillis());
    }


    public Hydra(Position position, int health, int ad, Random rand) {
        super(new Position(position.getX(), position.getY(),50));
        setAttackDamage(health);
        setAttackDamage(ad);
        this.rand = rand;
    }

    public void startFight(PlayerCharacter playerCharacter) {
        playerCharacter.fight(this); //example override for playerCharacter
    }

    public void takeDamage(int damage, boolean usingAnduril) {
        if (usingAnduril || rand.nextInt(2) == 0) {
            takeDamage(damage);
        } else {
            setHealth(getHealth() + damage);
        }
    }

    @Override
    public String getType() {
        return "hydra";
    }
}
