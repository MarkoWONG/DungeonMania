package dungeonmania.entity;

// import dungeonmania.PlayerCharacter;
import dungeonmania.entity.staticEnt.*;
import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public abstract class EntityFactory {

    private HashMap<Position, ArrayList<Entity>> entityMap;

    public EntityFactory(HashMap<Position, ArrayList<Entity>> entityMap ) {
        this.entityMap = entityMap;
    }

    public Entity create(String entityType, Position startPos, String colour) {
        switch (entityType.toLowerCase(Locale.ROOT)) {
            // case "player":
            //     return makePlayer(startPos);
            case "wall":
                return makeWall(startPos);
            case "exit":
                return makeExit(startPos);
            case "boulder":
                return makeBoulder(startPos);
            case "floorswitch":
                return Switch(startPos);
            case "door":
                return makeDoor(startPos);
            case "portal":
                return makePortal(startPos, colour);
            case "toaster":
                return makeToaster(startPos);
            // case "mercenary":
            //     return makeMercenary();
            // case "zombie":
            //     return makeZombie();
            // case "spider":
            //     return makeSpider();
            // case "treasure":
            //     return makeTreasure();
            // case "key": // colour
            //     return makeKey();
            // case "healthpotion":
            //     return makeHealthPotion();
            // case "invincibilitypotion":
            //     return makeInvincibilityPotion();
            // case "invisibilitypotion":
            //     return makeInvisibilityPotion();
            // case "wood":
            //     return makeWood();
            // case "arrow":
            //     return makeArrow();
            // case "bomb":
            //     return makeBomb();
            // case "sword":
            //     return makeSword();
            // case "armour":
            //     return makeArmour();
            // case "onering":
            //     return makeOneRing();
            default:
                return null;
        }
    }

    // protected Entity makePlayer(Position startPos) {
    //     return new PlayerCharacter(startPos);
    // }

    protected Entity makeWall(Position startPos) {
        return new Wall(startPos);
    }

    protected Entity makeExit(Position startPos) {
        return new Exit(startPos);
    }

    protected Entity makeBoulder(Position startPos) {
        return new Boulder(startPos);
    }

    protected Entity Switch(Position startPos) {
        return new Switch(startPos);
    }

    protected Entity makeDoor(Position startPos) {
        return new Door(startPos, entityMap);
    }

    protected Entity makePortal(Position startPos, String colour) {
        return new Portal(startPos, colour, entityMap);
    }

    protected Entity makeToaster(Position startPos) {
        return new Toaster(startPos, 20, entityMap);
    }

    // protected Entity makeMercenary(Position startPos) {
    //     return new Mercenary(startPos);
    // }

    // protected Entity makeZombie() {
    //     return new Zombie();
    // }

    // protected Entity makeSpider() {
    //     return new Spider();
    // }

    // protected Entity makeTreasure() {
    //     return new Treasure();
    // }

    // protected Entity makeKey() {
    //     return new Key();
    // }

    // protected Entity makeHealthPotion() {
    //     return new HealthPotion();
    // }

    // protected Entity makeInvincibilityPotion() {
    //     return new InvincibilityPotion();
    // }

    // protected Entity makeInvisibilityPotion() {
    //     return new InvisibilityPotion();
    // }

    // protected Entity makeWood() {
    //     return new Wood();
    // }

    // protected Entity makeArrow() {
    //     return new Arrow();
    // }

    // protected Entity makeBomb() {
    //     return new Bomb();
    // }

    // protected Entity makeSword() {
    //     return new Arrow();
    // }

    // protected Entity makeArmour() {
    //     return new Arrow();
    // }

    // protected Entity makeOneRing() {
    //     return new OneRing();
    // }


}
