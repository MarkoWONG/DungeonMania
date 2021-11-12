package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

public class MovementTests {

    // private boolean entityAtPosition(String type, DungeonResponse response, Position position) {
    //     return response.getEntities().stream().filter(e -> e.getType().equals(type)).filter(e -> e.getPosition().equals(position)).count() == 1;
    // }

    public boolean isAdjacent(Position a, Position b) {
        int x = Math.abs(a.getX() - b.getX());
        int y = Math.abs(a.getY() - b.getY());
        return x + y == 1;
    }

    @Test
    public void testMovement_player() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("player_mov", "Standard");
        controller.tick(null, Direction.RIGHT);
        assertEquals(1, response.getEntities().size());
        assertEquals(response.getEntities().get(0).getPosition(), new Position(1, 0));

        controller.tick(null, Direction.DOWN);
        assertEquals(response.getEntities().get(0).getPosition(), new Position(1, 1));

        controller.tick(null, Direction.LEFT);
        assertEquals(response.getEntities().get(0).getPosition(), new Position(0, 1));

        controller.tick(null, Direction.UP);
        assertEquals(response.getEntities().get(0).getPosition(), new Position(0, 0));

        for (int i = 0; i < 10; i++) {
            controller.tick(null, Direction.RIGHT);
        }
        assertEquals(response.getEntities().get(0).getPosition(), new Position(10, 0));
    }

    @Test
    public void testMovement_zombie() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("zombie_mov", "Standard");

        assertEquals(1, response.getEntities().size());
        assertEquals(response.getEntities().get(0).getPosition(), new Position(0, 0));
        
        for (int i = 0; i < 10; i++) {
            Position prev = response.getEntities().get(0).getPosition();
            
            controller.tick(null, null);

            Position current = response.getEntities().get(0).getPosition();
            assert(Position.isAdjacent(prev, current));
        }
        
    }

    @Test
    public void testMovement_hydra() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("hydra_mov", "Hard");

        assertEquals(6, response.getEntities().size());
        assertEquals(response.getEntities().get(0).getPosition(), new Position(1, 1, 50));
        
        for (int i = 0; i < 10; i++) {
            Position prev = response.getEntities().get(0).getPosition();
            
            response = controller.tick(null, Direction.NONE);

            Position current = response.getEntities().get(0).getPosition();
            assert(isAdjacent(prev, current));
        }
        
    }

    @Test
    public void testMovement_mercenary() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("merc_mov", "Standard");
        // player is at 0,0 and merc is at 10,0

        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(9, 0))).count());
        
        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(8, 0))).count());
        
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(3, 0))).count());
        
        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(3, 1))).count());

        controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(3, 2))).count());

        controller.tick(null, Direction.LEFT);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(2, 2))).count());
        
        controller.tick(null, Direction.LEFT);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(1, 2))).count());
        
        controller.tick(null, Direction.RIGHT);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(0, 2))).count());
        
    }

    @Test
    public void testMovement_spider() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("spider_mov", "Standard");
        assertEquals(1, response.getEntities().size());
        assertEquals(response.getEntities().get(0).getPosition(), new Position(1, 1));

        Position start = response.getEntities().get(0).getPosition();
        Position prevprev = response.getEntities().get(0).getPosition();

        for (int i = 0; i < 20; i++) {
            Position prev = response.getEntities().get(0).getPosition();
            
            controller.tick(null, Direction.DOWN);

            Position current = response.getEntities().get(0).getPosition();
            assert(Position.isAdjacent(start, current)); // new position is still in the movement area
            assert(Position.isAdjacent(prev, current)); // new position is next to the old one
            assertFalse(prevprev.equals(current)); // spood has not gone back the way it came

            prevprev = prev;
        }
    }

}