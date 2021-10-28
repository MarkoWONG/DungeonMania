package dungeonmania.entity;

import dungeonmania.PlayerCharacter;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public abstract class EntityFactory {

    private HashMap<Position, ArrayList<Entity>> entityMap;

    public EntityFactory(HashMap<Position, ArrayList<Entity>> entityMap ) {
        this.entityMap = entityMap;
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
                return makeFloorSwitch(startPos);
            case "door":
                return makeDoor(startPos);
            case "portal":
                return makePortal(startPos);
            case "toaster":
                return makeToaster(startPos);
            case "mercenary":
                return makeMercenary(startPos);
            case "zombie":
                return makeZombie(startPos);
            case "spider":
                return makeSpider(startPos);
            case "treasure":
                return makeTreasure(startPos);
            case "key":
                return makeKey(startPos);
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
        }
    }

    public Entity makePlayer(Position startPos) {
        return new PlayerCharacter(startPos);
    }

    public Entity makeWall(Position startPos) {
        return new Wall(startPos);
    }

    public Entity makeExit(Position startPos) {
        return new Exit(startPos);
    }

    public Entity makeBoulder(Position startPos) {
        return new Boulder(startPos);
    }

    public Entity makeFloorSwitch(Position startPos) {
        return new floorSwitch(startPos);
    }

    public Entity makeDoor(Position startPos) {
        return new Door(startPos);
    }

    public Entity makePortal(Position startPos) {
        return new Portal(startPos);
    }

    public Entity makeToaster(Position startPos) {
        return new Toaster(startPos);
    }

    public Entity makeMercenary(Position startPos) {
        return new Mercenary(startPos);
    }

    public Entity makeZombie(Position startPos) {
        return new Zombie(startPos);
    }

    public Entity makeSpider(Position startPos) {
        return new Spider(startPos);
    }

    public Entity makeTreasure(Position startPos) {
        return new Treasure(startPos);
    }

    public Entity makeKey(Position startPos) {
        return new Key(startPos);
    }

    public Entity makeHealthPotion(Position startPos) {
        return new HealthPotion(startPos);
    }

    public Entity makeInvincibilityPotion(Position startPos) {
        return new InvincibilityPotion(startPos);
    }

    public Entity makeInvisibilityPotion(Position startPos) {
        return new InvisibilityPotion(startPos);
    }

    public Entity makeWood(Position startPos) {
        return new Wood(startPos);
    }

    public Entity makeArrow(Position startPos) {
        return new Arrow(startPos);
    }

    public Entity makeBomb(Position startPos) {
        return new Bomb(startPos);
    }

    public Entity makeSword(Position startPos) {
        return new Arrow(startPos);
    }

    public Entity makeArmour(Position startPos) {
        return new Arrow(startPos);
    }

    public Entity makeOneRing(Position startPos) {
        return new OneRing(startPos);
    }


}
