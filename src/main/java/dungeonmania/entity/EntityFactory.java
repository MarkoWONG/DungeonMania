package dungeonmania.entity;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.collectables.buildable.Bow;
import dungeonmania.entity.collectables.buildable.Shield;
import dungeonmania.entity.staticEnt.*;
import dungeonmania.entity.collectables.*;
import dungeonmania.entity.collectables.potion.*;
import dungeonmania.entity.collectables.rare.*;
import dungeonmania.mobs.*;
import dungeonmania.entity.floorTiles.SwampTile;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public abstract class EntityFactory {

    protected EntityList entityMap;
    protected Random currRandom;
    private ArrayList<Subscriber> subscribers = new ArrayList<>();

    public EntityFactory(EntityList entityMap, Random currRandom) {
        this.entityMap = entityMap;
        this.currRandom = currRandom;
    }

    /**
     * Create an entity of the corresponding type
     * @param entityType The string type of the entity
     * @param startPos The entities starting position
     * @param colour String colour of the entity (where applicable)
     * @param key String keyID of the entity (where applicable)
     * @param mov_factor The movement factor of the entity (where applicable)
     * @return The entity that has been created
     */
    public Entity create(String entityType, Position startPos, String colour, String key, int mov_factor) {
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
        if (entityType.toLowerCase(Locale.ROOT).contains("anduril")) {return makeAnduril(startPos);}
        if (entityType.toLowerCase(Locale.ROOT).contains("swamp_tile")) {return makeSwampTile(startPos, mov_factor);}
        return null;
    }

    /**
     * Given an entity, have them set up to observe the player character, and then return the same entity
     * @param e The entity that should watch the player
     * @return The entity given as input
     */
    private Entity subscribe(Entity e) {
        PlayerCharacter player = entityMap.findPlayer(); 
        if (e instanceof Subscriber && player != null) { // && player exists already
            player.addSubscriber((Subscriber) e);
        } else if (e instanceof Subscriber) { // && player does not exist yet
            subscribers.add((Subscriber) e);
        }
        return e;
    }

    /**
     * Create the player, and set up all their subscribers
     * @param startPos The player's starting position
     * @return The player
     */
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
        return new Toaster(startPos,20,entityMap,currRandom);
    }

    /**
     * Create a mercenary, but with a chance to create an assassin in it's place
     * @param startPos The spawn position of the mercenary or assassin
     * @return The mercenary or assassin
     */
    protected Entity makeMercenary(Position startPos) {
        if (currRandom.nextInt(100) < 30) {
            return subscribe(makeAssassin(startPos));
        } else {
            return subscribe(new Mercenary(startPos,1,entityMap,15,4,currRandom));
        }
    }

    /**
     * Create an assassin, where you want to force an assassin spawn
     * @param startPos The spawn position of the mercenary or assassin
     * @return
     */
    protected Entity makeAssassin(Position startPos) {
        return subscribe(new Assassin(startPos,1,entityMap,15,10,currRandom));
    }

    protected Entity makeZombie(Position startPos) {
        return new ZombieToast(startPos,10,2, currRandom);
    }

    protected Entity makeSpider(Position startPos) {
        return new Spider(startPos,5,6,entityMap);
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

    protected Entity makeAnduril(Position startPos) {
        if (startPos == null) {
            return new Anduril();
        }
        return new Anduril(startPos);
    }
    

    protected Entity makeHydra(Position startPos) {
        return new Hydra(startPos, 50, 2, currRandom);
    }

    protected Entity makeBow() { return new Bow(); }

    protected Entity makeShield() { return new Shield(); }

    protected Entity makeSwampTile(Position startPos, int mov_factor) {
        return new SwampTile(startPos, mov_factor);
    }


}
