package dungeonmania.movement;

import dungeonmania.PlayerCharacter;
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
    private HashMap<Position, ArrayList<Entity>> entitiesMap;
    private PlayerCharacter player;

    public MovementManager(HashMap<Position, ArrayList<Entity>> entitiesMap, PlayerCharacter player) {
        this.entitiesMap = entitiesMap;
        this.player = player;
    }

    public void simulate(Direction direction) {
        moveChar(direction);
        doInteractions();
        moveMobs();
        doInteractions();
    }

    private void moveChar(Direction direction) {
        checkBoulder(direction);
        if (checkMove(player, direction)){
            player.move(direction);
        }
    }

    private void doInteractions() {
        // ???
        // fights
        // switches
        // other things?? idk
    }

    /**
     * calls move for every mob on the map
     * passes a random, possible direction for the mob to move
     */
    private void moveMobs() {
        for (ArrayList<Entity> es : entitiesMap.values()) {
            for (Entity e : es) {
                if (e instanceof Mob) {
                    Mob e.move(getRandDirection(e)); // needs to have something passed to it
                }
            }
        }
    }

    /**
     * generates a random, possible movement direction
     * @param entity
     * @return a random direction the entity can move in
     */
    private Direction getRandDirection(Entity entity) {
        Position entityPosition = entity.getPosition();
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

        Random rand = new Random();
        int x = rand.nextInt(possibleMoves.size());

        return possibleMoves.get(x);
    }

    /**
     * checks if the player is going to run into a boulder
     * if so, moves the boulder
     * @param direction where the character wants to move
     */
    private void checkBoulder(Direction direction) {
        // if the player is walking into a boulder then move boulder
        Position newPlayerPos = player.getPosition().translateBy(direction);
        ArrayList<Entity> thingsInPosition = entitiesMap.get(newPlayerPos);
        if (thingsInPosition.stream().filter(e -> e.getType().equals("boulder")).count() == 1) {
            Boulder boulder = (Boulder) thingsInPosition.stream().filter(e -> e.getType().equals("boulder")).collect(Collectors.toList()).get(0);
            if (checkMove(boulder, direction)) {
                boulder.move(direction);
            }
        }
    }

    /**
     * @precondition the entity passed is movable (ie, implements movement)
     * @return true if the move is possible, false is not
     */
    public Boolean checkMove(Entity entity, Direction direction) {
        Position newEntityPosition = entity.getPosition().translateBy(direction);
        ArrayList<Entity> thingsInPosition = entitiesMap.get(newEntityPosition);

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
