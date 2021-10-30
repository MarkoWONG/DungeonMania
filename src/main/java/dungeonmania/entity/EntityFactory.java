package dungeonmania.entity;

import dungeonmania.PlayerCharacter;
import dungeonmania.entity.staticEnt.*;
import dungeonmania.entity.collectables.*;
import dungeonmania.entity.collectables.potion.*;
import dungeonmania.entity.collectables.rare.*;
import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public abstract class EntityFactory {

    private HashMap<Position, ArrayList<Entity>> entityMap;
    private Random random;

    public EntityFactory(HashMap<Position, ArrayList<Entity>> entityMap) {
        this.entityMap = entityMap;
        this.random = new Random(System.currentTimeMillis());
    }

    public Entity create(String entityType, Position startPos, String colour, String key) {
        switch (entityType.toLowerCase(Locale.ROOT)) {
            case "player":
                return makePlayer(startPos);
            case "wall":
                return makeWall(startPos);
            case "exit":
                return makeExit(startPos);
            case "boulder":
                return makeBoulder(startPos);
            case "floorswitch":
                return makeSwitch(startPos);
            case "door":
                return makeDoor(startPos, key);
            case "portal":
                return makePortal(startPos, colour);
            case "toaster":
                return makeToaster(startPos);
             case "mercenary":
                 return makeMercenary();
             case "zombie":
                 return makeZombie();
             case "spider":
                 return makeSpider();
            case "treasure":
                return makeTreasure(startPos);
            case "key":
                return makeKey(startPos, key);
            case "healthpotion":
                return makeHealthPotion(startPos);
            case "invincibilitypotion":
                return makeInvincibilityPotion(startPos);
            case "invisibilitypotion":
                return makeInvisibilityPotion(startPos);
            case "wood":
                return makeWood(startPos);
            case "arrow":
                return makeArrow(startPos);
            case "bomb":
                return makeBomb(startPos);
            case "sword":
                return makeSword(startPos);
            case "armour":
                return makeArmour(startPos);
            case "onering":
                return makeOneRing(startPos);
            default:
                return null;
        }
    }

    protected Entity makePlayer(Position startPos) {
        return new PlayerCharacter(startPos);
    }

    protected Entity makeWall(Position startPos) {
        return new Wall(startPos);
    }

    protected Entity makeExit(Position startPos) {
        return new Exit(startPos);
    }

    protected Entity makeBoulder(Position startPos) {
        return new Boulder(startPos);
    }

    protected Entity makeSwitch(Position startPos) {
        return new Switch(startPos);
    }

    protected Entity makeDoor(Position startPos, String keyIdentifer) {
        return new Door(startPos, keyIdentifer);
    }

    protected Entity makePortal(Position startPos, String colour) {
        return new Portal(startPos, colour, entityMap);
    }

    protected Entity makeToaster(Position startPos) {
        return new Toaster(startPos, 20, entityMap);
    }

     protected Entity makeMercenary(Position startPos) {
         return new Mercenary(startPos);
     }

     protected Entity makeZombie() {
         return new Zombie();
     }

     protected Entity makeSpider() {
         return new Spider();
     }

    protected Entity makeTreasure(Position startPos) {
        return new Treasure(startPos);
    }

    protected Entity makeKey(Position startPos, String keyIdentifer) {
        return new Key(startPos, keyIdentifer);
    }

    protected Entity makeHealthPotion(Position startPos) {
        return new HealthPotion(startPos, entityMap);
    }

    protected Entity makeInvincibilityPotion(Position startPos) {
        return new InvincibilityPotion(startPos, entityMap);
    }

    protected Entity makeInvisibilityPotion(Position startPos) {
        return new InvisibilityPotion(startPos);
    }

    protected Entity makeWood(Position startPos) {
        return new Wood(startPos);
    }

    protected Entity makeArrow(Position startPos) {
        return new Arrow(startPos);
    }

    protected Entity makeBomb(Position startPos) {
        return new Bomb(startPos, entityMap);
    }

    protected Entity makeSword(Position startPos) {
        return new Sword(startPos, (random.nextInt(5-2) + 2));
    }

    protected Entity makeArmour(Position startPos) {
        return new Armour(startPos);
    }

    protected Entity makeOneRing(Position startPos) {
        return new OneRing(startPos);
    }


}
