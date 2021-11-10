package dungeonmania.mobs;
import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.entity.collectables.BribeMaterial;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Mercenary extends Mob implements Subscriber{
    private EntityList entities;
    private Position charPosition;
    private Boolean charIsInvisible;
    private Boolean charIsInvincible;
    private int price;
    private int battleRadius;
    private int bribeDuration;

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
        this.bribeDuration = -1;
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

    @Override
    public String getType() {
        return "mercenary";
    }

    @Override
    public void click(PlayerCharacter character) {
        BribeMaterial bribeMat = searchBribeMaterial(character);
        if (bribeMat == null) {
            throw new InvalidActionException("bribe material is required to bribe");
        }
        if (checkBribeRange(charPosition, this.getPosition())) {
            bribe(bribeMat.getBribeAmount(price), bribeMat.getBribeDuration());
            bribeMat.usedInBribe(character);
        } else {
            throw new InvalidActionException("Mercenary out of range");
        }
    }

    private boolean checkBribeRange(Position characterPos, Position thisPos){
        if (abs(characterPos.getX()-thisPos.getX()) <= 2 && characterPos.getY() == thisPos.getY()){
            return true;
        }
        else if (abs(characterPos.getY()-thisPos.getY()) <= 2 && characterPos.getX() == thisPos.getX()){
            return true;
        }
        return false;
    }

    /**
     * bribe the mercenary to change its faction. 
     * the amount given is cumilative
     * @param amount an amount of money given
     * @return false if its not enough, true if the merc has become an ally
     */
    public void bribe(int amount, int bribeDuration) {
        price -= amount;
        if (price > 0) {
            return;
        }
        this.bribeDuration = bribeDuration;
        super.changeFaction("ally");
    }

    private BribeMaterial searchBribeMaterial(PlayerCharacter character){
        // get all avaliable bribe Materials
        ArrayList<BribeMaterial> bribeMats = new ArrayList<BribeMaterial>();
        for (CollectableEntity ent : character.getInventory()){
            if (ent instanceof BribeMaterial){
                bribeMats.add((BribeMaterial) ent);
            }
        }

        // return the highest Piority birbe Material (spectre -> sun_stone -> teasure)
        BribeMaterial highestPiorityMat = null;
        if (bribeMats.size() > 0){
            highestPiorityMat = bribeMats.get(0);
            for (BribeMaterial mat: bribeMats){
                if (highestPiorityMat.getBribePriority() < mat.getBribePriority()){
                    highestPiorityMat = mat;
                }
            }
        }
        return highestPiorityMat;
    }

    @Override
    public void move(Direction direction) {
        if (charPosition != null && !charIsInvisible){
            if (charIsInvincible) {
                Direction newMove = MovementManager.invertDirection(MovementManager.shortestPath(this, charPosition, entities));
                super.move(MovementManager.staticCheckMove(this, newMove, entities) ? newMove : Direction.NONE);
            }
            else {
                super.move(MovementManager.shortestPath(this, charPosition, entities));
            }
            if (bribeDuration == 0){
                super.changeFaction("enemy");
            }
            else if (bribeDuration > -1){
                bribeDuration--;
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
