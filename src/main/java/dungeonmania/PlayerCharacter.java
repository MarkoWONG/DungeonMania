package dungeonmania;

import dungeonmania.entity.Entity;
import dungeonmania.entity.Mob.MobEntity;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.mobs.Mob;
import dungeonmania.movement.Movement;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;

public class PlayerCharacter extends Entity {
    private ArrayList<CollectableEntity> inventory;
    private ArrayList<MobEntity> allies;
    private double Health;
    private double attackDamage;
    private Boolean hasArmour;
    private Position position;
    private Boolean invincible;
    private Boolean invisible;
    private Boolean hasOneRing;
    private Double health;
    private Double defense;
    

    public PlayerCharacter(Position position) {
        super(position, "player");
        this.position = position;
        this.inventory = new ArrayList<CollectableEntity>();
        // this.allies = new ArrayList<Mercenary>();
        this.Health = 10;
        this.attackDamage = 1;
        this.hasArmour = false;
        this.invisible = false;
        this.invincible = false;
        this.hasOneRing = false;
    }

    public PlayerCharacter(Double health, Double attack, Double defense) {
        super(null, "player");
        this.health = health;
        this.attackDamage = attack;
        this.defense = defense;
        this.hasArmour = false;
    }


    public void addItemToInventory(CollectableEntity item) {
        ; //REMEMBER TO Remove Entity from Map
    }

    public void removeItemFromInventory(CollectableEntity item){
        
    }

    // @Override
    // public void fight(MobEntity mob) {

    // }

    @Override
    public void startInteraction(Entity entity) {

    }

    // Getter and Setters
    public ArrayList<CollectableEntity> getInventory() {
        return this.inventory;
    }

    public void removeItem(CollectableEntity item) {
        inventory.remove(item);
    }

    public void takeDamage(Double damage) {
        Double damageTaken = damage * defense;
        health -= damageTaken;

        if (health <= 0) {
            // notify something coz of death and all that
        }
    }

    @Override
    public void move(Direction direction) {

    }

    @Override
    public void teleport(Position position) {

    }

    public void setInventory(ArrayList<CollectableEntity> inventory) {
        this.inventory = inventory;
    }

    public ArrayList<MobEntity> getAllies() {
        return this.allies;
    }

    public void setAllies(ArrayList<MobEntity> allies) {
        this.allies = allies;
    }

    public double getHealth() {
        return this.Health;
    }

    public void setHealth(double Health) {
        this.Health = Health;
    }

    public void fight(Entity e) {
        // does nothing
    }

    public void fight(Mob mob) {

        // get attack damage before fight so that order of attacks doesnt matter
        int charAttack = getHealth() * attack() / 5;
        int mobAttack = mob.getHealth() * mob.attack() / 10;

        //takeDamage(mobAttack);
        mob.takeDamage(charAttack);
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

    public Boolean getHasOneRing() {
        return this.hasOneRing;
    }

    public void setHasOneRing(Boolean hasOneRing) {
        this.hasOneRing = hasOneRing;
    }


}
