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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
            if ( eachEntity instanceof Mob && !(player.getInvisibleTicks() > 0)) {
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
            if (eachEntity.getType().equals(entity.getType())) {
                return false;
            }
            if (eachEntity.getPosition().getLayer() >= entity.getPosition().getLayer()) {
                if ((eachEntity instanceof Mob && entity instanceof PlayerCharacter) || (eachEntity instanceof PlayerCharacter && entity instanceof Mob)) {
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

    public static Boolean staticCheckMove(Entity entity, Position position, EntityList entities) {
        Position newEntityPosition = position;
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
     * @param a some entity a
     * @param b destination position
     * @return the direction a must travel to get to b
     */
    public static Direction shortestPath(Entity a, Position b, EntityList entities) {
        /*Position btwn = Position.calculatePositionBetween(a.getPosition(), b);
        int xDistance = btwn.getX();
        int yDistance = btwn.getY();
        Direction d = Direction.NONE;

        if (Math.abs(xDistance) < Math.abs(yDistance)) { // further away on the y axis
            d = (yDistance > 0) ? Direction.DOWN : Direction.UP;
        } else if (Math.abs(yDistance) < Math.abs(xDistance)) { // further away on the x axis OR equal
            d = (xDistance > 0) ? Direction.RIGHT : Direction.LEFT;
        } 

        if (staticCheckMove(a, d, entities)) {
            return d;
        } else {
            return Direction.NONE;
        }
        */
        return dijksra(a, b, entities);
    }

    public static Direction invertDirection(Direction d) {
        if (d.toString().equals("UP")){
            return Direction.DOWN;
        }
        else if (d.toString().equals("LEFT")){
            return Direction.RIGHT;
        }
        else if (d.toString().equals("DOWN")){
            return Direction.UP;
        }
        else if (d.toString().equals("RIGHT")){
            return Direction.LEFT;
        }
        else{
            return Direction.NONE;
        }
    }

    private static Direction dijksra(Entity a, Position b, EntityList entities) {
        List<Position> grid = entities.grid(a);

        //let dist be a Map<Position, Double>
        Map<Position, Double> dist = new HashMap<>();
        //let prev be a Map<Position, Position>
        Map<Position, Position> prev = new HashMap<>();
        //let queue be a Queue<Position> of every position in grid
        List<Position> queue = new ArrayList<>();

        //for each Position p in grid:
        //    dist[p] := infinity
        //    previous[p] := null
        //dist[source] := 0
        for (Position p : grid) {
            dist.put(p, Double.POSITIVE_INFINITY);
            prev.put(p, null);
            queue.add(p);
        }
        dist.replace(a.getPosition(), 0.0);

        while (!queue.isEmpty()) {
            // get node with smallest distance
            Position u = getDijkstraMin(queue, dist);
            queue.remove(u);

            // get neighbours
            List<Position> neighbours = u.getAdjacentPositions();

            for (Position v : neighbours) {
                if (queue.contains(v)) {
                    Double tempDistance = dist.get(u) + 1; // * u.movementFactor();
                    if (tempDistance < dist.get(v)) {
                        dist.replace(v, tempDistance);
                        prev.replace(v, u);
                    }
                }
            }
        }

        // return the direction of the shortest path

        Direction path = Direction.NONE;
        Double min = Double.POSITIVE_INFINITY;

        List<Position> neighbours = a.getPosition().getAdjacentPositions();
        for (int i = 1; i < 8; i = i+2) { // we only want directly adjacent, not diagonal
            if (dist.get(neighbours.get(i)) < min) {
                min = dist.get(neighbours.get(i));
                if (i == 1) {path = Direction.UP;}
                if (i == 3) {path = Direction.RIGHT;}
                if (i == 5) {path = Direction.DOWN;}
                if (i == 7) {path = Direction.LEFT;}
                
            }
        }

        return path;
    }

    private static Position getDijkstraMin(List<Position> queue, Map<Position, Double> dist) {
        Position shortest = null;
        Double min = Double.POSITIVE_INFINITY; 
        for (Position p : queue) {
            if (dist.get(p) < min) {
                shortest = p;
                min = dist.get(p);
            }
        }
        return shortest;
    }

}
