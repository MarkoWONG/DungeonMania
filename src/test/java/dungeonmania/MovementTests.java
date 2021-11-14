package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

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
        DungeonResponse response = controller.newGame("player_mov", "Peaceful");
        response = controller.tick(null, Direction.RIGHT);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());

        response = controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());

        response = controller.tick(null, Direction.LEFT);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(0, 1))).count());

        response = controller.tick(null, Direction.UP);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(0, 0))).count());

        for (int i = 0; i < 10; i++) {
            response =controller.tick(null, Direction.RIGHT);
        }
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(10, 0))).count());
    }

    @Test
    public void testMovement_zombie() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("zombie_mov", "Standard",123L);

        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("zombie_toast")).filter(e -> e.getPosition().equals(new Position(1, 1))).count());
        
        for (int i = 0; i < 10; i++) {
            response = controller.tick(null, Direction.NONE);
            // isAdjacent is bugged (doesn't do absolute value) or we would use that here
        }
        Position finalPos = response.getEntities().stream().filter(e -> e.getType().equals("zombie_toast")).map(EntityResponse::getPosition).collect(Collectors.toList()).get(0);
        assertEquals(finalPos, new Position(1,3, 50));


    }
    // mercenary movement tests conducted in DijkstraTests

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
    public void testMovement_spider() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("spider_mov", "Standard", 123L);
        response = controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        for (int i = 0; i < 20; i++) {
            response = controller.tick(null, Direction.DOWN);
        }
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(1, 2))).count());
    }

}