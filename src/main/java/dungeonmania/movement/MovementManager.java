package dungeonmania.movement;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.entity.staticEnt.Door;
import dungeonmania.mobs.Mob;
import dungeonmania.mobs.Spider;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;


public class MovementManager {
    private PlayerCharacter player;
    private EntityList entities;

    public MovementManager(EntityList entities) {
        this.entities = entities;
    }

    public void setCharacter(PlayerCharacter player) {
        this.player = player;
    }


    public void doInteractions() {
        for (Entity eachEntity : entities) {
            for (Entity eachOtherEntity : entities.searchTile(eachEntity)) {
                eachEntity.startInteraction(eachOtherEntity);
            }
        }
        // clean up picked up items
        for(int i = entities.size() - 1; i >= 0; --i) {
            Entity curr = entities.get(i);
            if(curr.getPosition() == null) {
                entities.remove(i);
            }
        }
    }


    public void moveChar(Direction moveDir) {
        checkBoulder(moveDir);
        ArrayList<Entity> player = entities.search("player");
        for (Entity thePlayer : player) {
            if (checkMove(thePlayer,moveDir)) {
                thePlayer.move(moveDir);
            }
        }
    }

    /**
     * calls move for every mob on the map
     * passes a random, possible direction for the mob to move
     * if the player is invincible, it runs away
     */
    public void moveMobs() {
        for (Entity eachEntity : entities) {
            if ( eachEntity instanceof Mob ) {
                if (player.getInvincibleTicks() > 0 && !(player.getInvisibleTicks() > 0)) {
                    eachEntity.move(runAway(eachEntity));
                }
                eachEntity.move(getRandDirection(eachEntity));
            }
        }
    }

    /**
     * generates a random, possible movement direction
     * @param entity
     * @return a random direction the entity can move in
     */
    private Direction getRandDirection(Entity entity) {
        ArrayList<Direction> possibleMoves = new ArrayList<>();

        if (checkMove(entity, Direction.UP)) {
            possibleMoves.add(Direction.UP);
        }
        if (checkMove(entity, Direction.RIGHT)) {
            possibleMoves.add(Direction.RIGHT);
        }
        if (checkMove(entity, Direction.DOWN)) {
            possibleMoves.add(Direction.DOWN);
        }
        if (checkMove(entity, Direction.LEFT)) {
            possibleMoves.add(Direction.LEFT);
        }

        Random rand = new Random(System.currentTimeMillis());
        if (possibleMoves.size() != 0) {
            int x = rand.nextInt(possibleMoves.size());
            return possibleMoves.get(x);
        }
        return Direction.NONE;

    }
    
    /**
     * Calculates the shortest path, then inverts that direction to run away
     * If the move is valid, that direction is returned. Otherwise, none.
     * @precondition the player is invincible
     * @param entity
     * @return Direction taken to get away from player
     */
    private Direction runAway(Entity entity) {
        Direction path = shortestPath(entity, player, entities);
        Direction newPath = Direction.NONE;
        switch (path) {
            case UP: newPath = Direction.DOWN;
            case LEFT: newPath = Direction.RIGHT;
            case DOWN: newPath = Direction.UP;
            case RIGHT: newPath = Direction.LEFT;
            case NONE: newPath = Direction.NONE;
        }

        if (checkMove(entity, newPath)) {
            return newPath;
        } else {
            return Direction.NONE;
        }
    }

    /**
     * checks if the player is going to run into a boulder
     * if so, moves the boulder
     * @param direction where the character wants to move
     */
    private void checkBoulder(Direction direction) {
        Position newPlayerPos = player.getPosition().translateBy(direction);
        ArrayList<Entity> tile = entities.search(newPlayerPos);
        Entity theBoulder = tile.stream().filter(e -> e.getType().equals("boulder")).findFirst().orElse(null);
        if (theBoulder != null) {
            theBoulder.setPosition(theBoulder.getPosition().asLayer(70));
            if (checkMove(theBoulder,direction)) {
                theBoulder.move(direction);
            }
            theBoulder.setPosition(theBoulder.getPosition().asLayer(100));
        }
    }

    /**
     * @precondition the entity passed is movable (ie, implements movement)
     * @return true if the move is possible, false is not
     */
    private Boolean checkMove(Entity entity, Direction direction) {
        Position newEntityPosition = entity.getPosition().translateBy(direction);
        ArrayList<Entity> tile = entities.search(newEntityPosition);
        for (Entity eachEntity : tile) {
            if (eachEntity instanceof Door && entity instanceof PlayerCharacter) {
                return (((Door) eachEntity).unlockDoor((PlayerCharacter) entity));
            }
            if (eachEntity.getPosition().getLayer() >= entity.getPosition().getLayer()) {
                if ((eachEntity instanceof Mob || eachEntity instanceof PlayerCharacter) && (entity instanceof Mob || entity instanceof PlayerCharacter)) {
                    return true;
                }
                return false;
            }
        }
        return true;
    }

    public static Boolean staticCheckMove(Entity entity, Direction direction, EntityList entities) {
        Position newEntityPosition = entity.getPosition().translateBy(direction);
        ArrayList<Entity> tile = entities.search(newEntityPosition);
        for (Entity eachEntity : tile) {
            if (eachEntity instanceof Door && entity instanceof PlayerCharacter) {
                return (((Door) eachEntity).unlockDoor((PlayerCharacter) entity));
            }
            if (eachEntity.getPosition().getLayer() >= entity.getPosition().getLayer()) {
                if ((eachEntity instanceof Mob || eachEntity instanceof PlayerCharacter) && (entity instanceof Mob || entity instanceof PlayerCharacter)) {
                    return true;
                }
                if ((eachEntity.getType().equals("spider") && entity.getType().equals("boulder"))) {
                    ((Spider) eachEntity).changeDirection();
                } else if ((eachEntity.getType().equals("boulder") && entity.getType().equals("spider"))) {
                    ((Spider) entity).changeDirection();
                }
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param a the position of some entity a
     * @param b the position of some entity b
     * @return the direction a must travel to get to b
     */
    public static Direction shortestPath(Entity a, Entity b, EntityList entities) {
        Position btwn = Position.calculatePositionBetween(a.getPosition(), b.getPosition());
        int xDistance = btwn.getX();
        int yDistance = btwn.getY();
        Direction d = Direction.NONE;

        if (Math.abs(xDistance) < Math.abs(yDistance)) { // further away on the y axis
            d = (yDistance > 0) ? Direction.DOWN : Direction.UP;
        } else { // further away on the x axis OR equal
            d = (xDistance > 0) ? Direction.RIGHT : Direction.LEFT;
        } 

        if (staticCheckMove(a, d, entities)) {
            return d;
        } else {
            return Direction.NONE;
        }
    }

}
