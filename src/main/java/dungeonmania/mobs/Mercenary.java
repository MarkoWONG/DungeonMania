package dungeonmania.mobs;
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
    private int price;
    private Position charPosition;
    private int battleRadius;
    private Boolean battleInRadius;
    private Armour armour;

    public Mercenary(Position position, int price) {
        super(new Position(position.getX(), position.getY(),50));
        this.setHealth(15);
        this.price = price;
        Random rand = new Random();
        if (rand.nextInt(5) == 4) {
            armour = new Armour();
        } else {
            armour = null;
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
    public Boolean bribe(int amount) {
        price -= amount;
        if (price > 0) {
            return false;
        }
        super.changeFaction("ally");
        return true;
    }

    @Override
    public void takeDamage(int damage) {
        int reducedDamage = damage;
        if (armour != null) {
            reducedDamage = armour.usedInDefense(reducedDamage);
            armour.usedInBattle(this);
        }
        super.takeDamage(reducedDamage);
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
            throw new InvalidActionException("Out of range");
        }
    }

    @Override
    public void move(Direction direction) {
        if ( charPosition != null ) {
            super.move(MovementManager.shortestPath(super.getPosition(), charPosition));
        } else {
            ;
        }

    }
}
