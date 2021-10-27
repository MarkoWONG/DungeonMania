package dungeonmania.entity;

import dungeonmania.PlayerCharacter;
import dungeonmania.entity.staticEnt.Wall;
import java.util.Locale;

public abstract class EntityFactory {

    public Entity create(String entityType) {
        switch (entityType.toLowerCase(Locale.ROOT)) {
            // case "player":
            //     return makePlayer();
            case "wall":
                return makeWall();
            // case "exit":
            //     return makeExit();
            // case "boulder":
            //     return makeBoulder();
            // case "floorswitch":
            //     return makeFloorSwitch();
            // case "door":
            //     return makeDoor();
            // case "portal":
            //     return makePortal();
            // case "toaster":
            //     return makeToaster();
            // case "mercenary":
            //     return makeMercenary();
            // case "zombie":
            //     return makeZombie();
            // case "spider":
            //     return makeSpider();
            // case "treasure":
            //     return makeTreasure();
            // case "key":
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
        }
    }

    protected Entity makePlayer() {
        return new PlayerCharacter();
    }

    protected Entity makeWall() {
        return new Wall();
    }

    protected Entity makeExit() {
        return new Exit();
    }

    protected Entity makeBoulder() {
        return new Boulder();
    }

    protected Entity makeFloorSwitch() {
        return new floorSwitch();
    }

    protected Entity makeDoor() {
        return new Door();
    }

    protected Entity makePortal() {
        return new Portal();
    }

    protected Entity makeToaster() {
        return new Toaster();
    }

    protected Entity makeMercenary() {
        return new Mercenary();
    }

    protected Entity makeZombie() {
        return new Zombie();
    }

    protected Entity makeSpider() {
        return new Spider();
    }

    protected Entity makeTreasure() {
        return new Treasure();
    }

    protected Entity makeKey() {
        return new Key();
    }

    protected Entity makeHealthPotion() {
        return new HealthPotion();
    }

    protected Entity makeInvincibilityPotion() {
        return new InvincibilityPotion();
    }

    protected Entity makeInvisibilityPotion() {
        return new InvisibilityPotion();
    }

    protected Entity makeWood() {
        return new Wood();
    }

    protected Entity makeArrow() {
        return new Arrow();
    }

    protected Entity makeBomb() {
        return new Bomb();
    }

    protected Entity makeSword() {
        return new Arrow();
    }

    protected Entity makeArmour() {
        return new Arrow();
    }

    protected Entity makeOneRing() {
        return new OneRing();
    }


}
