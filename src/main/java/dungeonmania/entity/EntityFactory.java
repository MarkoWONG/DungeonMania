package dungeonmania.entity;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.collectables.buildable.Bow;
import dungeonmania.entity.collectables.buildable.Shield;
import dungeonmania.entity.staticEnt.*;
import dungeonmania.entity.collectables.*;
import dungeonmania.entity.collectables.potion.*;
import dungeonmania.entity.collectables.rare.*;
import dungeonmania.mobs.Mercenary;
import dungeonmania.mobs.Spider;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.util.Position;

import java.util.Locale;
import java.util.Random;

public abstract class EntityFactory {

    protected EntityList entityMap;
    protected Random random;

    public EntityFactory(EntityList entityMap) {
        this.entityMap = entityMap;
        this.random = new Random(System.currentTimeMillis());
    }

    public Entity create(String entityType, Position startPos, String colour, String key) {
        if (entityType.toLowerCase(Locale.ROOT).contains("player")) {return makePlayer(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("wall")) {return makeWall(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("exit")) {return makeExit(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("boulder")) {return makeBoulder(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("switch")) {return makeFloorSwitch(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("door")) {return makeDoor(startPos, key);}
        if (entityType.toLowerCase(Locale.ROOT).contains("portal")) {return makePortal(startPos, colour);}
        if (entityType.toLowerCase(Locale.ROOT).contains("zombie_toast_spawner")) {return makeToaster(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("mercenary")) {return makeMercenary(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("zombie_toast")) {return makeZombie(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("spider")) {return makeSpider(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("treasure")) {return makeTreasure(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("key")) {return makeKey(startPos, key);}
        if (entityType.toLowerCase(Locale.ROOT).contains("health_potion")) {return makeHealthPotion(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("invincibility_potion")) {return makeInvincibilityPotion(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("invisibility_potion")) {return makeInvisibilityPotion(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("wood")) {return makeWood(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("arrow")) {return makeArrow(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("bomb")) {return makeBomb(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("sword")) {return makeSword(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("armour")) {return makeArmour(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("one_ring")) {return makeOneRing(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("shield")) {return makeShield();}
        if (entityType.toLowerCase(Locale.ROOT).contains("bow")) {return makeBow();}
        return null;
    }

    protected Entity makePlayer(Position startPos) {
        return new PlayerCharacter(startPos,20,2);
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

    protected Entity makeFloorSwitch(Position startPos) {
        return new Switch(startPos);
    }

    protected Entity makeDoor(Position startPos, String keyValue) {
        return new Door(startPos,keyValue);
    }

    protected Entity makePortal(Position startPos, String colourValue) {
        return new Portal(startPos,colourValue,entityMap);
    }

    protected Entity makeToaster(Position startPos) {
        return new Toaster(startPos,20,entityMap);
    }

    protected Entity makeMercenary(Position startPos) {
        return new Mercenary(startPos,1,entityMap,15,4);
    }

    protected Entity makeZombie(Position startPos) {
        return new ZombieToast(startPos,10,2);
    }

    protected Entity makeSpider(Position startPos) {
        return new Spider(startPos,5,6);
    }

    protected Entity makeTreasure(Position startPos) {
        return new Treasure(startPos);
    }

    protected Entity makeKey(Position startPos, String keyValue) {
        return new Key(startPos, keyValue);
    }

    protected Entity makeHealthPotion(Position startPos) {
        return new HealthPotion(startPos);
    }

    protected Entity makeInvincibilityPotion(Position startPos) {
        return new InvincibilityPotion(startPos,true);
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
        return new Bomb(startPos,entityMap);
    }

    protected Entity makeSword(Position startPos) {
        return new Sword(startPos);
    }

    protected Entity makeArmour(Position startPos) {
        return new Armour(startPos);
    }

    protected Entity makeOneRing(Position startPos) {
        return new OneRing(startPos);
    }

    protected Entity makeBow() { return new Bow(); }

    protected Entity makeShield() { return new Shield(); }


}
