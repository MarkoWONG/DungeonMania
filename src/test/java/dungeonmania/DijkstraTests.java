package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DijkstraTests {

    // Run these tests with the spwan spiders and merc lines in dungeon commented out for best results

    private boolean entityAtPosition(String type, DungeonResponse response, Position position) {
        return response.getEntities().stream().filter(e -> e.getType().equals(type)).filter(e -> e.getPosition().equals(position)).count() == 1;
    }

    @Test
    public void mazeTest() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("dijkstra_maze", "Standard",1L);

        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        response = controller.tick(null, Direction.UP);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 2)));
        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 3)));
        response = controller.tick(null, Direction.UP);
        assertTrue(entityAtPosition("mercenary",response,new Position(2, 3)));
        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("mercenary",response,new Position(3, 3)));
        response = controller.tick(null, Direction.UP);
        assertTrue(entityAtPosition("mercenary",response,new Position(4, 3)));
        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("mercenary",response,new Position(5, 3)));
        response = controller.tick(null, Direction.UP);
        assertTrue(entityAtPosition("mercenary",response,new Position(6, 3)));
        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("mercenary",response,new Position(6, 4)));
        response = controller.tick(null, Direction.UP);

        for (int i = 0; i < 21; i++) {
            response = controller.tick(null, Direction.DOWN);
            response = controller.tick(null, Direction.UP);
        }
        response = controller.tick(null, Direction.DOWN);

        assertTrue(entityAtPosition("mercenary",response,new Position(18, 16)));

    }

    @Test
    public void trapped() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("merc_trapped", "Standard",5L);
        // merc has no way to get to player, so does not move

        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        response = controller.tick(null, Direction.LEFT);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        response = controller.tick(null, Direction.LEFT);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        response = controller.tick(null, Direction.UP);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        response = controller.tick(null, Direction.UP);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
        response = controller.tick(null, Direction.DOWN);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
    }

    @Test
    public void twoRoutes() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("merc_equal", "Standard",9L);

        // path checks clockwise from "up". if the two shortest paths are equal, 
        // the one checked sooner will be taken (ie, UP or RIGHT)

        assertTrue(entityAtPosition("mercenary",response,new Position(4, 2)));
        response = controller.tick(null, Direction.LEFT);
        assertTrue(entityAtPosition("mercenary",response,new Position(4, 1)));
        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("mercenary",response,new Position(4, 0)));
        response = controller.tick(null, Direction.LEFT);
        assertTrue(entityAtPosition("mercenary",response,new Position(3, 0)));
        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("mercenary",response,new Position(2, 0)));
        response = controller.tick(null, Direction.LEFT);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 0)));
        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("mercenary",response,new Position(1, 1)));
    }
}
