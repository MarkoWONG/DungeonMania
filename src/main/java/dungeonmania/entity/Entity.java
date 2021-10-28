package dungeonmania.entity;

import dungeonmania.PlayerCharacter;
import dungeonmania.util.Position;
import dungeonmania.entity.staticEnt.*;

import java.util.UUID;

public abstract class Entity implements Interacts {

    private Position position;
    private final String id;
    private final String type;
    public Entity(Position position, String type) {
        this.id = UUID.randomUUID().toString();
        this.position = position;
        this.type = type;
    }

    public Entity(Position position, String type, String colour) {
        this.id = UUID.randomUUID().toString();
        this.position = position;
        this.type = type;
    }
    
    @Override
    public void fight(Mob mob) {

    }

    public void incrementTick(){
        return;
    }
    // none of these do anything by default you need to override them in the specific class to implement the behaviour
    // startFight and startInteraction just call .fight(this) when overridden

    @Override
    public void startFight(PlayerCharacter playerCharacter) {
        // playerCharacter.fight(this); example override for playerCharacter
    }

    @Override
    public void fight(Mob mob) {

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
    public void interact(Exit wall) {

    }

    @Override
    public void interact(Boulder boulder) {

    }

    @Override
    public void interact(floorSwitch floorSwitch) {

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
    public void interact(Zombie zombie) {

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



    // Getter and Setters
    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

}
