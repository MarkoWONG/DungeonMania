package dungeonmania.movement;

import dungeonmania.PlayerCharacter;
import dungeonmania.entity.staticEnt.Boulder;
import dungeonmania.mobs.Mob;
import dungeonmania.movement.Movement;
import dungeonmania.entity.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.lang.Math;

public class MovementManager {
    private PlayerCharacter player;

    public MovementManager( PlayerCharacter player) {
        this.player = player;
    }

    /**
//     *
//     * @param direction the direction the player wants to move
//     * @param entitiesMap the current map
//     * @return the new map of the board
//     */
//    public HashMap<Position, ArrayList<Entity>> simulate(Direction direction, HashMap<Position, ArrayList<Entity>> entitiesMap) {
//        newMap = new HashMap<>();
//        oldMap = entitiesMap;
//
//        moveChar(direction);
//        doInteractions();
//        moveMobs();
//        doInteractions();
//
//        return newMap;
//    }

/*    private void moveChar(Direction direction) {
        checkBoulder(direction);
        if (checkMove(player, direction)){
            player.move(direction);
        }
        addToMap(player);

    }*/

    private void doInteractions() {
        // ???
        // fights
        // switches
        // other things?? idk
    }


    public HashMap<Position, ArrayList<Entity>> moveChar(HashMap<Position, ArrayList<Entity>> oldMap, Direction moveDir) {
        HashMap<Position, ArrayList<Entity>> newMap = new HashMap<>();
        checkBoulder(oldMap,moveDir);
        for (ArrayList<Entity> es : oldMap.values()) {
            for (Entity e : es) {
                if (e instanceof PlayerCharacter && checkMove(oldMap,player, moveDir) ) {
                    e.move(moveDir); // needs to have something passed to it
                }
                Position currPosition = e.getPosition();
                if (!newMap.containsKey(currPosition)) { // we can do this because position overrides hashCode and equals
                    newMap.put(currPosition, new ArrayList<Entity>());
                }
                newMap.get(currPosition).add(e);
            }
        }
        return newMap;
    }

    /**
     * calls move for every mob on the map
     * passes a random, possible direction for the mob to move
     */
    public HashMap<Position, ArrayList<Entity>> moveMobs(HashMap<Position, ArrayList<Entity>> oldMap) {
        HashMap<Position, ArrayList<Entity>> newMap = new HashMap<>();
        for (ArrayList<Entity> es : oldMap.values()) {
            for (Entity e : es) {
                if (e instanceof Mob) {
                    e.move(getRandDirection(oldMap,e)); // needs to have something passed to it
                }
                Position currPosition = e.getPosition();
                if (!newMap.containsKey(currPosition)) { // we can do this because position overrides hashCode and equals
                    newMap.put(currPosition, new ArrayList<Entity>());
                }
                newMap.get(currPosition).add(e);
            }
        }
        return newMap;
    }

    /**
     * generates a random, possible movement direction
     * @param entity
     * @return a random direction the entity can move in
     */
    private Direction getRandDirection(HashMap<Position, ArrayList<Entity>> oldMap, Entity entity) {
        ArrayList<Direction> possibleMoves = new ArrayList<>();

        if (checkMove(oldMap, entity, Direction.UP)) {
            possibleMoves.add(Direction.UP);
        }
        if (checkMove(oldMap, entity, Direction.RIGHT)) {
            possibleMoves.add(Direction.RIGHT);
        }
        if (checkMove(oldMap, entity, Direction.DOWN)) {
            possibleMoves.add(Direction.DOWN);
        }
        if (checkMove(oldMap, entity, Direction.LEFT)) {
            possibleMoves.add(Direction.LEFT);
        }

        Random rand = new Random(System.currentTimeMillis());
        int x = rand.nextInt(possibleMoves.size());

        return possibleMoves.get(x);
    }

    /**
     * checks if the player is going to run into a boulder
     * if so, moves the boulder
     * @param direction where the character wants to move
     */
    private void checkBoulder(HashMap<Position, ArrayList<Entity>> oldMap, Direction direction) {
        // if the player is walking into a boulder then move boulder
        Position newPlayerPos = player.getPosition().translateBy(direction);
        ArrayList<Entity> thingsInPosition = oldMap.get(newPlayerPos);
        if (thingsInPosition.stream().filter(e -> e.getType().equals("boulder")).count() == 1) {
            Boulder boulder = (Boulder) thingsInPosition.stream().filter(e -> e.getType().equals("boulder")).collect(Collectors.toList()).get(0);
            if (checkMove(oldMap, boulder, direction)) {
                boulder.move(direction);
            }
        }
    }

    /**
     * @precondition the entity passed is movable (ie, implements movement)
     * @return true if the move is possible, false is not
     */
    private Boolean checkMove(HashMap<Position, ArrayList<Entity>> oldMap, Entity entity, Direction direction) {
        Position newEntityPosition = entity.getPosition().translateBy(direction);
        ArrayList<Entity> thingsInPosition = oldMap.get(newEntityPosition);

        for(Entity e : thingsInPosition) {
            if (e.getPosition().getLayer() >= entity.getPosition().getLayer()) {
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
    public static Direction shortestPath(Position a, Position b) {
        Position btwn = Position.calculatePositionBetween(a, b);
        int xDistance = btwn.getX();
        int yDistance = btwn.getY();
        Direction d = Direction.NONE;

        if (Math.abs(xDistance) < Math.abs(yDistance)) { // further away on the y axis
            d = (yDistance > 0) ? Direction.UP : Direction.DOWN;
        } else { // further away on the x axis OR equal
            d = (xDistance > 0) ? Direction.RIGHT : Direction.LEFT;
        } 

        return d;
    }

}
