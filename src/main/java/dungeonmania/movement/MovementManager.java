package dungeonmania.movement;

import dungeonmania.PlayerCharacter;
import dungeonmania.mobs.Mob;
import dungeonmania.movement.Movement;
import dungeonmania.entity.Entity;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

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

    private void moveMobs() {
        for (ArrayList<Entity> es : entitiesMap.values()) {
            for (Entity e : es) {
                if (e instanceof Mob) {
                    Mob e.move();
                }
            }
        }
    }

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


}
