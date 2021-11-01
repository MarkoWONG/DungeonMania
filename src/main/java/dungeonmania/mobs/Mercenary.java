package dungeonmania.mobs;
import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;

import static java.lang.Math.abs;

public class Mercenary extends Mob{
    private EntityList entities;
    private PlayerCharacter characterTracker;
    private int price;
    private Position charPosition;
    private int battleRadius;
    private Boolean battleInRadius;

    public Mercenary(Position position, int price, EntityList entities,int health, int ad) {
        super(new Position(position.getX(), position.getY(),50));
        setAttackDamage(ad);
        setHealth(health);
        this.entities = entities;
        this.price = price;
        Random rand = new Random();
        if (rand.nextInt(5) == 4) {
            setArmour(new Armour());
        } else {
            setArmour(null);
        }
    }

    @Override
    public boolean isInteractable() {
        return true;
    }

    /**
     * bribe the mercenary to change its faction. 
     * the amount given is cumilative
     * @param amount an amount of money given
     * @return false if its not enough, true if the merc has become an ally
     */
    public void bribe(int amount) {
        price -= amount;
        if (price > 0) {
            return;
        }
        super.changeFaction("ally");
    }

    @Override
    public String getType() {
        return "mercenary";
    }

    @Override
    public void click(PlayerCharacter character) {
        Entity treasure = character.getItemByType("treasure");
        if (treasure == null) {
            throw new InvalidActionException("Gold is required to bribe");
        }
        Position posBetween = Position.calculatePositionBetween(character.getPosition(),this.getPosition());
        if (abs(posBetween.getX()) == 2 ||  abs(posBetween.getY()) == 2) {
            bribe(1);
        } else {
            throw new InvalidActionException("Mercenary out of range");
        }
    }

    @Override
    public void move(Direction direction) {
        this.characterTracker = entities.findPlayer();
        super.move(MovementManager.shortestPath(super.getPosition(), characterTracker.getPosition()));
    }

    @Override
    public void takeDamage(int damage) {
        int reducedDamage = damage;
        if (getArmour() != null) {
            reducedDamage = getArmour().usedInDefense(reducedDamage);
            getArmour().usedInBattle(this);
        }
        super.takeDamage(reducedDamage);
    }
}
