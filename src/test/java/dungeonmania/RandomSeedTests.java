package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

public class RandomSeedTests {

    @Test
    public void testCollisions_playerToWall() {
        DungeonManiaController controller1 = new DungeonManiaController();
        DungeonResponse response1 = controller1.newGame("lots_of_zombies", "Standard", 1321L);

        DungeonManiaController controller2 = new DungeonManiaController();
        DungeonResponse response2 = controller2.newGame("lots_of_zombies", "Standard",1321L);

        DungeonManiaController controller3 = new DungeonManiaController();
        DungeonResponse response3 = controller3.newGame("lots_of_zombies", "Standard",1321L);


        response1 = controller1.tick(null, Direction.DOWN);
        response2 = controller2.tick(null, Direction.DOWN);
        response3 = controller3.tick(null, Direction.DOWN);

        assertEquals(response1.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()), response2.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()));
        assertEquals(response2.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()), response3.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()));

        response1 = controller1.tick(null, Direction.LEFT);
        response2 = controller2.tick(null, Direction.LEFT);
        response3 = controller3.tick(null, Direction.LEFT);

        assertEquals(response1.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()), response2.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()));
        assertEquals(response2.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()), response3.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()));

        response1 = controller1.tick(null, Direction.UP);
        response2 = controller2.tick(null, Direction.UP);
        response3 = controller3.tick(null, Direction.UP);

        assertEquals(response1.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()), response2.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()));
        assertEquals(response2.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()), response3.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()));

        response1 = controller1.tick(null, Direction.RIGHT);
        response2 = controller2.tick(null, Direction.RIGHT);
        response3 = controller3.tick(null, Direction.RIGHT);

        assertEquals(response1.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()), response2.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()));
        assertEquals(response2.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()), response3.getEntities().stream().map(EntityResponse::getPosition).collect(Collectors.toList()));

    }
}
