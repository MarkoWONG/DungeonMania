package dungeonmania.movement;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;
import dungeonmania.entity.staticEnt.Door;
import dungeonmania.mobs.Mob;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


public class MovementManager {
    private PlayerCharacter player;
    private EntityList entities;
    private HashMap<Entity, Integer> ticksTilMove;
    private Random currRandom;

    public MovementManager(EntityList entities, Random currRandom) {
        this.entities = entities;
        this.ticksTilMove = new HashMap<>();
        this.currRandom = currRandom;
    }

    /**
     * Set the current instance's player field
     * @param player The player object for the current dungeon
     */
    public void setCharacter(PlayerCharacter player) {
        this.player = player;
    }

    /**
     * Apply the correct movement factor to each mob
     * @param entities The EntityList for the current dungeon
     */
    public void initTicksTilMove(EntityList entities) {
        for (Entity e : entities) {
            if (! ticksTilMove.containsKey(e)) {
                if (e.getType().equals("player")) {
                    ticksTilMove.put(e, 1);
                } else {
                    ticksTilMove.put(e, calcMovFactor(e.getPosition()));
                }
            }
        }
    }

    /**
     * @param p The given position
     * @return the movement factor for the given position
     */
    private int calcMovFactor(Position p) {
        int mov_factor = 1;
        for (Entity e : entities.search(p)) {
            mov_factor *= e.getMovementFactor();
        }
        return mov_factor;
    }


    /**
     * For each entity sharing a tile with another, do all interactions
     */
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


    /**
     * Move the character in the given direction
     * @param moveDir
     */
    public void moveChar(Direction moveDir) {
        initTicksTilMove(entities);
        checkBoulder(moveDir);
        ArrayList<Entity> player = entities.search("player");
        for (Entity thePlayer : player) {
            if (checkMove(thePlayer,moveDir) && ticksTilMove.get(thePlayer) == 1) {
                thePlayer.move(moveDir);
                ticksTilMove.replace(thePlayer, 1);
            } else if (checkMove(thePlayer,moveDir) && ticksTilMove.get(thePlayer) > 1) {
                int m = ticksTilMove.get(thePlayer);
                ticksTilMove.replace(thePlayer, m-1);
            }
        }
    }

    /**
     * calls move for every mob on the map
     * passes a random, possible direction for the mob to move
     * if the player is invincible, it runs away
     */
    public void moveMobs() {
        initTicksTilMove(entities);
        for (Entity eachEntity : entities) {
            if ( eachEntity instanceof Mob && !(player.getInvisibleTicks() > 0)) {
                if (ticksTilMove.get(eachEntity) == 1) {
                    eachEntity.move(getRandDirection(eachEntity));
                    ticksTilMove.replace(eachEntity, calcMovFactor(eachEntity.getPosition()));
                } else {
                    int m = ticksTilMove.get(eachEntity);
                    ticksTilMove.replace(eachEntity, m-1);
                }
            }
        }
    }

    /**
     * Generates a random, possible movement direction
     * @param entity The given entity
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

        Random rand = currRandom;
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
     * @param position The position to be checked
     * @param entities The EntityList for the current dungeon
     * @return whether the position contains a boulder
     */
    public static Boolean checkBoulder(Position position,EntityList entities) {
        return entities.search(position)
                .stream().map(Entity::getType)
                .collect(Collectors.toList())
                .contains("boulder");
    }

    /**
     * @param entity The entity to be checked
     * @param direction The direction to be checked for possible move
     * @return True if the move is possible, False is not
     */
    private Boolean checkMove(Entity entity, Direction direction) {
        Position newEntityPosition = entity.getPosition().translateBy(direction);
        ArrayList<Entity> tile = entities.search(newEntityPosition);
        for (Entity eachEntity : tile) {
            if (eachEntity instanceof Door && entity instanceof PlayerCharacter) {
                return (((Door) eachEntity).unlockDoor((PlayerCharacter) entity));
            }
            if (eachEntity.getType().equals("player") && direction.equals(Direction.NONE)){
                return true;
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

    /**
     * @param entity An entity to be checked
     * @param direction The direction of the possible move
     * @param entities The EntityList for the current dungeon
     * @return Whether a move can be made for the given entity in the given direction
     */
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
                return false;
            }
        }
        return true;
    }

    /**
     * @param entity An entity to be checked
     * @param position The position of the possible move
     * @param entities The EntityList for the current dungeon
     * @return Whether a move can be made for the given entity in the given direction
     */
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
        if (a.getPosition().equals(b)) {
            return Direction.NONE;
        }
        return Dijkstra(a, b, entities);
    }

    /**
     * @param d Any direction
     * @return The opposite of the given direction
     */
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

    /**
     * Perform dijkstra's algorithm between two points, finding the shortest path, and returning the first step in that path
     * @param a The entity that is conducting the pathfinding
     * @param b The ending position
     * @param entities The EntityList of the current dungeon
     * @return The first step in the shortest path
     */
    private static Direction Dijkstra(Entity a, Position b, EntityList entities) {
        
        List<Position> grid = entities.grid(a);

        Map<Position, Double> dist = new HashMap<>();
        Map<Position, Position> prev = new HashMap<>();
        List<Position> queue = new ArrayList<>();

        for (Position p : grid) {
            dist.put(p, Double.POSITIVE_INFINITY);
            prev.put(p, null);
            queue.add(p);
        }
        dist.replace(b.asLayer(0), 0.0);

        while (!queue.isEmpty()) {
            // get node with smallest distance
            Position u = getDijkstraMin(queue, dist);
            queue.remove(u);

            int mov_factor = 1;
            for (Entity e : entities.search(u)) {
                mov_factor *= e.getMovementFactor();
            }

            // get neighbours
            List<Position> neighbours = u.getAdjacentPositions();
            for (int i = 1; i < 8; i = i+2) { // we only want directly adjacent, not diagonal
                Position v = neighbours.get(i);
                
                if (queue.contains(v)) {
                    Double tempDistance = dist.get(u) + mov_factor;
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
        for (int i = 1; i < 8; i = i+2) { 
            if (dist.containsKey(neighbours.get(i)) && dist.get(neighbours.get(i)) < min) {
                min = dist.get(neighbours.get(i));
                if (i == 1) {path = Direction.UP;}
                if (i == 3) {path = Direction.RIGHT;}
                if (i == 5) {path = Direction.DOWN;}
                if (i == 7) {path = Direction.LEFT;}
            } 
        }
        return path;
    }

    /**
     * Given a list of positions, return the closest
     * @param queue A queue of all possible positions
     * @param dist The distance for each position
     * @return The position with the lowest distance
     */
    private static Position getDijkstraMin(List<Position> queue, Map<Position, Double> dist) {
        Position shortest = null;
        Double min = Double.POSITIVE_INFINITY; 
        for (Position p : queue) {
            if (dist.get(p) <= min) {
                shortest = p;
                min = dist.get(p);
            }
        }
        return shortest;
    }

}
