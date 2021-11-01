package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

public class CollisionTests {

    private boolean entityAtPosition(String type, DungeonResponse response, Position position) {
        return response.getEntities().stream().filter(e -> e.getType().equals(type)).filter(e -> e.getPosition().equals(position)).count() == 1;
    }

    @Test
    public void testCollisions_playerToWall() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("player_wall_col", "Standard");

        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("player",response,new Position(1, 0)));

        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("player",response,new Position(2, 0)));
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.LEFT);
        assertTrue(entityAtPosition("player",response,new Position(2, 2)));
        response = controller.tick(null, Direction.UP);
        assertTrue(entityAtPosition("player",response,new Position(2, 2)));
        for (int i = 0; i < 10; i++) {
            response = controller.tick(null, Direction.UP);
        }
        assertTrue(entityAtPosition("player",response,new Position(2, 2)));
    }

    @Test
    public void testCollisions_spiderToWall() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("spider_col", "Standard");

        assertTrue(entityAtPosition("spider",response,new Position(1, 1)));

        response = controller.tick(null, Direction.NONE);
        assertTrue(entityAtPosition("spider",response,new Position(1, 0)));

        response = controller.tick(null, Direction.NONE);
        assertTrue(entityAtPosition("spider",response,new Position(2, 0)));;

        response = controller.tick(null, Direction.NONE);
        assertTrue(entityAtPosition("spider",response,new Position(2, 1)));
        
        // enters a portal here, check how this is supposed to work
        response = controller.tick(null, Direction.NONE);
        assertTrue(entityAtPosition("spider",response,new Position(2, 2)));

        response = controller.tick(null, Direction.NONE);
        assertTrue(entityAtPosition("spider",response,new Position(1, 2)));

        response = controller.tick(null, Direction.NONE);
        assertTrue(entityAtPosition("spider",response,new Position(0, 2)));

        // hits boulder at 0,1
        response = controller.tick(null, Direction.NONE);
        assertTrue(entityAtPosition("spider",response,new Position(0, 2)));

        response = controller.tick(null, Direction.NONE);
        assertTrue(entityAtPosition("spider",response,new Position(1, 2)));

        response = controller.tick(null, Direction.NONE);
        assertTrue(entityAtPosition("spider",response,new Position(2, 2)));
    }

    @Test
    public void testCollisions_zombieToWall() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("zombie_wall_col", "Standard");

        for (int i = 0; i <10; i++) {
            response = controller.tick(null, Direction.NONE);
            assertTrue(entityAtPosition("zombie_toast",response,new Position(1, 1)));
        }
    }

    @Test
    public void testCollisions_playerToDoor() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("player_door", "Standard");

        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("player",response,new Position(1, 0)));

        // player now has the key

        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("player",response,new Position(1, 0)));
        // player cannot go through door that is locked

        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("player",response,new Position(2, 1)));
        // player is able to move through a door they have the key for
    }

    @Test
    public void testCollsisions_zombieToDoor() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("zombie_door", "Standard");

        for (int i = 0; i < 10; i++) {
            response =  controller.tick(null, Direction.NONE);
            assertTrue(entityAtPosition("zombie_toast",response,new Position(1, 1)));
        }

        response =  controller.tick(null, Direction.UP); // gets key
        response = controller.tick(null, Direction.UP); // unlocks door
        response =  controller.tick(null, Direction.DOWN); // steps back

        for (int i = 0; i < 10; i++) {
            response = controller.tick(null, Direction.NONE);
            assertTrue(entityAtPosition("zombie_toast",response,new Position(1, 1)));
        }
        // zombie cannot move anywhere even with an open door
        
    }

    @Test
    public void testCollsisions_mercenaryToDoor() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("merc_door", "Standard");

        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.NONE);
            assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        }

        controller.tick(null, Direction.UP); // gets key
        controller.tick(null, Direction.UP); // unlocks door
        controller.tick(null, Direction.DOWN); // steps back

        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.NONE);
            assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        }
        // merc cannot move anywhere even with an open door
        
    }

    @Test
    public void testCollisions_playerToBoulder() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("player_boulder", "Standard");

        assertTrue(entityAtPosition("player",response,new Position(0, 0)));
        assertTrue(entityAtPosition("boulder",response,new Position(1, 0)));

        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("player",response,new Position(1, 0)));
        assertTrue(entityAtPosition("boulder",response,new Position(2, 0)));

        response = controller.tick(null, Direction.DOWN);
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.RIGHT);
        response = controller.tick(null, Direction.UP);

        assertTrue(entityAtPosition("player",response,new Position(3, 0)));
        assertTrue(entityAtPosition("boulder",response,new Position(2, 0)));

        response = controller.tick(null, Direction.LEFT);
        assertTrue(entityAtPosition("player",response,new Position(2, 0)));
        assertTrue(entityAtPosition("boulder",response,new Position(1, 0)));
        
    }

    @Test
    public void testCollisions_mobToMob() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("mob_col", "Standard");

        for (int i = 0; i < 10; i++) {
            response = controller.tick(null, Direction.NONE);
            assertTrue(entityAtPosition("zombie_toast",response,new Position(0, -1)));
            assertTrue(entityAtPosition("zombie_toast",response,new Position(1, -1)));
            assertTrue(entityAtPosition("zombie_toast",response,new Position(0, 0)));
            assertTrue(entityAtPosition("zombie_toast",response,new Position(1, 0)));
        }
    }
}