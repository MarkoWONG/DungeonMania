package dungeonmania.mobs;
import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Mercenary extends Mob implements Subscriber{
    private EntityList entities;
    private Position charPosition;
    private Boolean charIsInvisible;
    private Boolean charIsInvincible;
    private int price;
    private int battleRadius;

    public Mercenary(Position position, int price, EntityList entities,int health, int ad) {
        super(new Position(position.getX(), position.getY(),50));
        setAttackDamage(ad);
        setHealth(health);
        this.entities = entities;
        this.price = price;
        this.charPosition = null;
        this.charIsInvincible = false;
        this.charIsInvisible = false;
        this.battleRadius = 5;
        Random rand = new Random();
        if (rand.nextInt(5) == 4) {
            setArmour(new Armour());
        } else {
            setArmour(null);
        }
    }

    @Override
    public boolean isInteractable() {
        return isEnemy();
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
        if (abs(posBetween.getX()) <= 2 ||  abs(posBetween.getY()) <= 2) {
            bribe(1);
            character.consume(new ArrayList<String>(List.of("treasure")));
        } else {
            throw new InvalidActionException("Mercenary out of range");
        }
    }

    @Override
    public void move(Direction direction) {
        if (charPosition != null && !charIsInvisible){
            if (charIsInvincible) {
                super.move(MovementManager.invertDirection(direction));
            }
            else {
                super.move(MovementManager.shortestPath(this, charPosition, entities));
            }
        }
    }

    @Override
    public void notifyFight() {
        if (! (charIsInvincible || charIsInvisible) && battleInRadius()) {
            move(MovementManager.shortestPath(this, charPosition, entities));
        }
    }

    @Override
    public void notifyMove(Position position) {
        charPosition = position;
    }

    @Override
    public void notifyInvisible(Boolean isInvisible) {
        charIsInvisible = isInvisible;
    }

    @Override
    public void notifyInvincible(Boolean isInvincible){
        charIsInvincible = isInvincible;
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

    private Boolean battleInRadius() {
        int xDiff = abs(super.getPosition().getX() - charPosition.getX());
        int yDiff = abs(super.getPosition().getY() - charPosition.getY());

        if (xDiff + yDiff <= battleRadius) {
            return true;
        } else {
            return false;
        }
    }
}
