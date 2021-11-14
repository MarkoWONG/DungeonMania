package dungeonmania.entity;

import dungeonmania.entity.collectables.*;
import dungeonmania.entity.collectables.potion.HealthPotion;
import dungeonmania.entity.collectables.potion.InvincibilityPotion;
import dungeonmania.entity.collectables.potion.InvisibilityPotion;
import dungeonmania.entity.collectables.rare.OneRing;
import dungeonmania.mobs.Mercenary;
import dungeonmania.mobs.Mob;
import dungeonmania.mobs.Spider;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.entity.staticEnt.*;
import dungeonmania.PlayerCharacter;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Entity implements Interacts {

    private Position position;
    private final String id;
    private boolean hasFought;
    private int movementFactor = 1;

    public Entity(Position position) {
        this.id = UUID.randomUUID().toString();
        this.position = position;
        hasFought = false;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public Position getPosition() {
        return position;
    }

    public int getXPos() {
        return position.getX();
    }

    public int getYPos() {
        return position.getY();
    }

    /**
     * @return How many ticks it takes for an entity (other than the player) to cross this tile
     */
    public int getMovementFactor() {
        return movementFactor;
    }

    /**
     * How many ticks it takes an entity (other than the player) to cross this tile
     * @param movementFactor The number of ticks for this tile
     */
    public void setMovementFactor(int movementFactor) {
        this.movementFactor = movementFactor;
    }

    public String getId() {
        return id;
    }

    /**
     * @return The string representing the entity
     */
    abstract public String getType();

    /**
     * @return Whether the entity can be clicked on by the frontend
     */
    public boolean isInteractable() {
        return false;
    }

    /**
     * Perform the entity behaviour for being clicked on
     * @param character The player character currently being controlled
     */
    public void click(PlayerCharacter character) {} // nothing by default

    /**
     * Move the entity in the given direction, assumes the move is valid
     * @param direction The direction to be moved in
     */
    public void move(Direction direction) {

    }

    public void fight(Entity e) {}

    public void fight(Mob mob) {}


    /**
     * Increment this entities internal tick counter if needed
     */
    public void incrementTick(){}

    public void startFight(PlayerCharacter playerCharacter) {
        playerCharacter.fight(this); //example override for playerCharacter
    }

    /**
     * Mark the entity as having fought or not fought this tick
     * @param value Whether the entity has fought
     */
    public void setHasFought(Boolean value) {
        hasFought = value;
    }

    /**
     * @return whether the entity has fought this tick
     */
    public boolean hasFought() {
        return hasFought;
    }

    /**
     * @return whether the entity is capable of reviving
     */
    public boolean canRevive() {
        return false;
    }

    /**
     * Revive the entity where possible
     * @param e This entity
     */
    public void revive() {

    }

    /**
     * Get the other info of this entity, the type of this info defined by getOtherInfoType()
     * @return A string containing the other info
     */
    public String getOtherInfo() {
        return "";
    }

    /**
     * Get the other info type of this entity, the actual info defined by getOtherInfo()
     * @return A string containing the other info
     */
    public String getOtherInfoType() {
        return "";
    }

    @Override
    public void startInteraction(Entity entity) {

    }

    @Override
    public void interact(Entity entity) {

    }

    @Override
    public void interact(PlayerCharacter player) {

    }

    @Override
    public void interact(Wall wall) {

    }

    @Override
    public void interact(Exit exit) {

    }

    @Override
    public void interact(Boulder boulder) {

    }

    @Override
    public void interact(Switch floorSwitch) {

    }

    @Override
    public void interact(Door door) {

    }

    @Override
    public void interact(Portal portal) {

    }

    @Override
    public void interact(Toaster toaster) {

    }

    @Override
    public void interact(Mercenary mercenary) {

    }

    @Override
    public void interact(ZombieToast zombie) {

    }

    @Override
    public void interact(Spider spider) {

    }

    @Override
    public void interact(Treasure treasure) {

    }

    @Override
    public void interact(Key key) {

    }

    @Override
    public void interact(HealthPotion healthPotion) {

    }

    @Override
    public void interact(InvincibilityPotion invincibilityPotion) {

    }

    @Override
    public void interact(InvisibilityPotion invisibilityPotion) {

    }

    @Override
    public void interact(Wood wood) {

    }

    @Override
    public void interact(Arrow arrow) {

    }

    @Override
    public void interact(Bomb bomb) {

    }

    @Override
    public void interact(Sword sword) {

    }

    @Override
    public void interact(Armour armour) {

    }

    @Override
    public void interact(OneRing oneRing) {

    }

    /**
     * @return The current health of the entity
     */
    public Integer getHealth() {
        return 1;
    }

    /**
     * Given a mob, add it to this entities allies list
     * @param newAlly The mob to be added as an ally
     */
    public void addAlly(Mob newAlly) {
    }

    /**
     * Get the inventory of the
     * @return an ArrayList of CollectableEntities
     */
    public ArrayList<CollectableEntity> getInventory() {
        return new ArrayList<CollectableEntity>();
    }
}
