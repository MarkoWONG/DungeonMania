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
        assertEquals(finalPos, new Position(1,1));


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
        DungeonResponse response = controller.newGame("spider_mov", "Standard", 123L);
        response = controller.tick(null, Direction.DOWN);
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        for (int i = 0; i < 20; i++) {
            response = controller.tick(null, Direction.DOWN);
        }
        assertEquals(1, response.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(1, 2))).count());
    }

}