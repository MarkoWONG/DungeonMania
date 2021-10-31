package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

public class CollisionTests {
    @Test
    public void testCollisions_playerToWall() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("player_wall_col", "Standard");

        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(3, 1))).count());
        controller.tick(null, Direction.LEFT);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(3, 1))).count());

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.LEFT);
        controller.tick(null, Direction.LEFT);

        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 2))).count());
        controller.tick(null, Direction.UP);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 2))).count());

        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.UP);
        }
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 2))).count());
    }

    @Test
    public void testCollisions_spiderToWall() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("spidedr_col", "Standard");

        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());

        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        
        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());
        
        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(2, 1))).count());
        
        // enters a portal here, check how this is supposed to work
        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(2, 2))).count());
        
        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(1, 2))).count());
        
        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(0, 2))).count());

        // hits boulder at 0,1
        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(0, 2))).count());
        
        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(1, 2))).count());
        
        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(2, 2))).count());
    }

    @Test
    public void testCollisions_zombieToWall() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("zombie_wall_col", "Standard");

        for (int i = 0; i <10; i++) {
            controller.tick(null, null);
            assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("zombie_toast")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());
        }
    }

    @Test
    public void testCollisions_playerToDoor() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("player_wall", "Standard");

        controller.tick(null, Direction.RIGHT);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());

        // player now has the key

        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        // player cannot go through door that is locked

        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(2, 1))).count());
        // player is able to move through a door they have the key for
    }

    @Test
    public void testCollsisions_zombieToDoor() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("zombie_door", "Standard");

        for (int i = 0; i < 10; i++) {
            controller.tick(null, null);
            assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("zombie_toast")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());
        }

        controller.tick(null, Direction.UP); // gets key
        controller.tick(null, Direction.UP); // unlocks door
        controller.tick(null, Direction.DOWN); // steps back

        for (int i = 0; i < 10; i++) {
            controller.tick(null, null);
            assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("zombie_toast")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());
        }
        // zombie cannot move anywhere even with an open door
        
    }

    @Test
    public void testCollsisions_mercenaryToDoor() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("merc_door", "Standard");

        for (int i = 0; i < 10; i++) {
            controller.tick(null, null);
            assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());
        }

        controller.tick(null, Direction.UP); // gets key
        controller.tick(null, Direction.UP); // unlocks door
        controller.tick(null, Direction.DOWN); // steps back

        for (int i = 0; i < 10; i++) {
            controller.tick(null, null);
            assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());
        }
        // merc cannot move anywhere even with an open door
        
    }

    @Test
    public void testCollisions_playerToBoulder() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("player_boulder", "Standard");

        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(0, 0))).count());
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("boulder")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());

        controller.tick(null, Direction.RIGHT);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("boulder")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());

        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.RIGHT);
        controller.tick(null, Direction.UP);

        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(3, 0))).count());
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("boulder")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());

        controller.tick(null, Direction.LEFT);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("boulder")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        
    }

    @Test
    public void testCollisions_mobToMob() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("mob_col", "Standard");

        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("zombie_toast")).filter(e -> e.getPosition().equals(new Position(2, 1))).count());
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(0, 2))).count());

        controller.tick(null, null);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("zombie_toast")).filter(e -> e.getPosition().equals(new Position(2, 1))).count());
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(0, 1))).count());

        for (int i = 0; i < 10; i++) {
            controller.tick(null, null);
            assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());
            assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("zombie_toast")).filter(e -> e.getPosition().equals(new Position(2, 1))).count());
            assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(0, 1))).count());
        }
        // mobs cannot move as there is a mob in the other space
    }
}