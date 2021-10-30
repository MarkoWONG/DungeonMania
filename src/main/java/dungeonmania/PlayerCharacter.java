package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.Mob.Mob;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class PlayerCharacter extends Entity {
    private ArrayList<CollectableEntity> inventory;
    private ArrayList<Mob> allies;
    private double Health;
    private double attackDamage;
    private Position position;
    private Boolean invincible;
    private Boolean invisible;
    

    public PlayerCharacter(Position position) {
        super(position, "player");
        this.position = position;
        this.inventory = new ArrayList<CollectableEntity>();
        this.allies = new ArrayList<Mob>();
        this.Health = 10;
        this.attackDamage = 1;
        this.invisible = false;
        this.invincible = false;
    }


    public void addItemToInventory(CollectableEntity item) {
        ; //REMEMBER TO Remove Entity from Map
    }

    public void removeItemFromInventory(CollectableEntity item){
        
    }

    // @Override
    // public void fight(Mob mob) {

    // }

    @Override
    public void startInteraction(Entity entity) {

    }

    // Getter and Setters
    public ArrayList<CollectableEntity> getInventory() {
        return this.inventory;
    }

    public void setInventory(ArrayList<CollectableEntity> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<Mob> getAllies() {
        return this.allies;
    }

    public void setAllies(ArrayList<Mob> allies) {
        this.allies = allies;
    }

    public double getHealth() {
        return this.Health;
    }

    public void setHealth(double Health) {
        this.Health = Health;
    }

    public double getAttackDamage() {
        return this.attackDamage;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public Boolean getHasArmour() {
        return this.hasArmour;
    }

    public void setHasArmour(Boolean hasArmour) {
        this.hasArmour = hasArmour;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Boolean getInvincible() {
        return this.invincible;
    }

    public void setInvincible(Boolean invincible) {
        this.invincible = invincible;
    }

    public Boolean getInvisible() {
        return this.invisible;
    }

    public void setInvisible(Boolean invisible) {
        this.invisible = invisible;
    }

}
