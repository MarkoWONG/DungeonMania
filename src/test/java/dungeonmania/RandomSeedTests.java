package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

public class RandomSeedTests {

    private boolean entityAtPosition(String type, DungeonResponse response, Position position) {
        return response.getEntities().stream().filter(e -> e.getType().equals(type)).filter(e -> e.getPosition().equals(position)).count() == 3;
    }

    @Test
    public void testCollisions_playerToWall() {
        Long seed = System.currentTimeMillis();
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonResponse response1 = controller1.newGame("lots_of_zombies", "Standard", 1321L);

        DungeonManiaController controller2 = new DungeonManiaController();
        DungeonResponse response2 = controller2.newGame("lots_of_zombies", "Standard",1321L);

        DungeonManiaController controller3 = new DungeonManiaController();
        DungeonResponse response3 = controller3.newGame("lots_of_zombies", "Standard",1321L);


        response1 = controller1.tick(null, Direction.DOWN);
        response2 = controller2.tick(null, Direction.DOWN);
        response3 = controller3.tick(null, Direction.DOWN);


        response1 = controller1.tick(null, Direction.LEFT);
        response2 = controller2.tick(null, Direction.LEFT);
        response3 = controller3.tick(null, Direction.LEFT);


        response1 = controller1.tick(null, Direction.UP);
        response2 = controller2.tick(null, Direction.UP);
        response3 = controller3.tick(null, Direction.UP);


        response1 = controller1.tick(null, Direction.RIGHT);
        response2 = controller2.tick(null, Direction.RIGHT);
        response3 = controller3.tick(null, Direction.RIGHT);

    }
}
