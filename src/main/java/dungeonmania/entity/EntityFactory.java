package dungeonmania.entity;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.collectables.buildable.Bow;
import dungeonmania.entity.collectables.buildable.Shield;
import dungeonmania.entity.staticEnt.*;
import dungeonmania.entity.collectables.*;
import dungeonmania.entity.collectables.potion.*;
import dungeonmania.entity.collectables.rare.*;
import dungeonmania.mobs.Assassin;
import dungeonmania.mobs.Mercenary;
import dungeonmania.mobs.Spider;
import dungeonmania.mobs.Subscriber;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public abstract class EntityFactory {

    protected EntityList entityMap;
    protected Random random;
    private ArrayList<Subscriber> subscribers = new ArrayList<>();

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
        if (entityType.toLowerCase(Locale.ROOT).contains("assassin")) {return makeAssassin(startPos);}
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
        if (entityType.toLowerCase(Locale.ROOT).contains("sun_stone")) {return makeSunStone(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("hydra")) {return makeHydra(startPos);}
        return null;
    }

    private Entity subscribe(Entity e) {
        PlayerCharacter player = entityMap.findPlayer(); 
        if (e instanceof Subscriber && player != null) { // && player exists already
            player.addSubscriber((Subscriber) e);
        } else if (e instanceof Subscriber) { // && player does not exist yet
            subscribers.add((Subscriber) e);
        }
        return e;
    }

    protected Entity makePlayer(Position startPos) {
        Entity player = new PlayerCharacter(startPos,20,2);
        for (Subscriber s : subscribers) {
            ((PlayerCharacter) player).addSubscriber(s);
        }
        return player;
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
        return subscribe(new Mercenary(startPos,1,entityMap,15,4));
    }

    protected Entity makeAssassin(Position startPos) {
        return subscribe(new Assassin(startPos,1,entityMap,15,4));
    }

    protected Entity makeZombie(Position startPos) {
        return new ZombieToast(startPos,10,2);
    }

    protected Entity makeSpider(Position startPos) {
        return new Spider(startPos,5,6);
    }

    protected Entity makeTreasure(Position startPos) {
        if (startPos == null) {
            return new Treasure();
        }
        return new Treasure(startPos);
    }

    protected Entity makeKey(Position startPos, String keyValue) {
        if (startPos == null) {
            return new Key(keyValue);
        }
        return new Key(startPos, keyValue);
    }

    protected Entity makeHealthPotion(Position startPos) {
        if (startPos == null) {
            return new HealthPotion();
        }
        return new HealthPotion(startPos);
    }

    protected Entity makeInvincibilityPotion(Position startPos) {
        if (startPos == null) {
            return new InvincibilityPotion(true);
        }
        return new InvincibilityPotion(startPos,true);
    }

    protected Entity makeInvisibilityPotion(Position startPos) {
        if (startPos == null) {
            return new InvisibilityPotion();
        }
        return new InvisibilityPotion(startPos);
    }

    protected Entity makeWood(Position startPos) {
        if (startPos == null) {
            return new Wood();
        }
        return new Wood(startPos);
    }

    protected Entity makeArrow(Position startPos) {
        if (startPos == null) {
            return new Arrow();
        }
        return new Arrow(startPos);
    }

    protected Entity makeBomb(Position startPos) {
        if (startPos == null) {
            return new Bomb(entityMap);
        }
        return new Bomb(startPos,entityMap);
    }

    protected Entity makeSword(Position startPos) {
        if (startPos == null) {
            return new Sword();
        }
        return new Sword(startPos);
    }

    protected Entity makeArmour(Position startPos) {
        if (startPos == null) {
            return new Armour();
        }
        return new Armour(startPos);
    }

    protected Entity makeOneRing(Position startPos) {
        if (startPos == null) {
            return new OneRing();
        }
        return new OneRing(startPos);
    }
    protected Entity makeSunStone(Position startPos) {
        if (startPos == null) {
            return new SunStone();
        }
        return new SunStone(startPos);
    }

    protected Entity makeHydra(Position startPos) {
        //return new Hydra(startPos);
        return null;
    }

    protected Entity makeBow() { return new Bow(); }

    protected Entity makeShield() { return new Shield(); }


}
