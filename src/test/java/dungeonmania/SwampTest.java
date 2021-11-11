package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SwampTest {
    // Run these tests with the spwan spiders and merc lines in dungeon commented out for best results

    private boolean entityAtPosition(String type, DungeonResponse response, Position position) {
        return response.getEntities().stream().filter(e -> e.getType().equals(type)).filter(e -> e.getPosition().equals(position)).count() >= 1;
    }

    @Test
    public void playerSwamp() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("player_in_the_swamp", "Standard");

        // movement factor of 2
        assertTrue(entityAtPosition("player",response,new Position(0,0)));
        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("player",response,new Position(1,0)));
        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("player",response,new Position(2,0)));
        response = controller.tick(null, Direction.RIGHT);
        assertTrue(entityAtPosition("player",response,new Position(3,0)));
        response = controller.tick(null, Direction.DOWN);

        // movement factor of 3
        assertTrue(entityAtPosition("player",response,new Position(3,1)));
        response = controller.tick(null, Direction.LEFT);
        assertTrue(entityAtPosition("player",response,new Position(2,1)));
        response = controller.tick(null, Direction.LEFT);
        assertTrue(entityAtPosition("player",response,new Position(1,1)));
        response = controller.tick(null, Direction.DOWN);
    }

    @Test
    public void mobSwamp() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse response = controller.newGame("mob_in_the_swamp", "Standard");

        
        assertTrue(entityAtPosition("zombie_toast",response,new Position(3,0)));
        assertTrue(entityAtPosition("spider",response,new Position(5,0)));

        assertTrue(entityAtPosition("zombie_toast",response,new Position(3,5)));
        assertTrue(entityAtPosition("spider",response,new Position(5,5)));
        response = controller.tick(null, Direction.NONE);
        
        // on mov factor 2 ground
        assertTrue(entityAtPosition("spider",response,new Position(5,0)));
        assertTrue(entityAtPosition("zombie_toast",response,new Position(3,0)));
        
        // on mov factor 3 ground
        assertTrue(entityAtPosition("zombie_toast",response,new Position(3,5)));
        assertTrue(entityAtPosition("spider",response,new Position(5,5)));

        response = controller.tick(null, Direction.NONE);

        // on mov factor 3 ground
        assertTrue(entityAtPosition("zombie_toast",response,new Position(3,5)));
        assertTrue(entityAtPosition("spider",response,new Position(5,5)));

        response = controller.tick(null, Direction.NONE);
    }
}
