package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

public class RandomSeedTests {
    
    private HashMap<Position, String> toMap(List<EntityResponse> list) {
        HashMap<Position, String> result = new HashMap<>();
        for (EntityResponse e : list) {
            result.put(e.getPosition(), e.getType());
        }
        return result;
    }

    private static<T extends Comparable<? super T>> void assertListAreEqualIgnoringOrder(List<T> a, List<T> b) {
        Collections.sort(a);
        Collections.sort(b);
        assertArrayEquals(a.toArray(), b.toArray());
    }


    @Test
    public void concurrent() {
        Long seed = 0L;
        DungeonManiaController controller1 = new DungeonManiaController(seed);
        DungeonResponse response1 = controller1.newGame("lots_of_zombies", "Standard");

        DungeonManiaController controller2 = new DungeonManiaController(seed);
        DungeonResponse response2 = controller2.newGame("lots_of_zombies", "Standard");

        DungeonManiaController controller3 = new DungeonManiaController(seed);
        DungeonResponse response3 = controller3.newGame("lots_of_zombies", "Standard");

        assert(toMap(response1.getEntities()).equals(toMap(response2.getEntities())));
        assert(toMap(response1.getEntities()).equals(toMap(response3.getEntities())));

        response1 = controller1.tick(null, Direction.DOWN);
        response2 = controller2.tick(null, Direction.DOWN);
        response3 = controller3.tick(null, Direction.DOWN);

        assertEquals(toMap(response1.getEntities()), toMap(response2.getEntities()));
        assert(toMap(response1.getEntities()).equals(toMap(response2.getEntities())));
        assert(toMap(response1.getEntities()).equals(toMap(response3.getEntities())));

        response1 = controller1.tick(null, Direction.LEFT);
        response2 = controller2.tick(null, Direction.LEFT);
        response3 = controller3.tick(null, Direction.LEFT);

        assert(toMap(response1.getEntities()).equals(toMap(response2.getEntities())));
        assert(toMap(response1.getEntities()).equals(toMap(response3.getEntities())));

        response1 = controller1.tick(null, Direction.UP);
        response2 = controller2.tick(null, Direction.UP);
        response3 = controller3.tick(null, Direction.UP);

        assert(toMap(response1.getEntities()).equals(toMap(response2.getEntities())));
        assert(toMap(response1.getEntities()).equals(toMap(response3.getEntities())));

        response1 = controller1.tick(null, Direction.RIGHT);
        response2 = controller2.tick(null, Direction.RIGHT);
        response3 = controller3.tick(null, Direction.RIGHT);

        assert(toMap(response1.getEntities()).equals(toMap(response2.getEntities())));
        assert(toMap(response1.getEntities()).equals(toMap(response3.getEntities())));
    }

    @Test
    public void oneAtATime() {
        Long seed = System.currentTimeMillis();
        DungeonManiaController controller1 = new DungeonManiaController(seed);
        DungeonResponse response1 = controller1.newGame("lots_of_zombies", "Standard");

        response1 = controller1.tick(null, Direction.DOWN);
        HashMap<Position, String> expected1 = toMap(response1.getEntities());
        response1 = controller1.tick(null, Direction.LEFT);
        HashMap<Position, String> expected2 = toMap(response1.getEntities());
        response1 = controller1.tick(null, Direction.UP);
        HashMap<Position, String> expected3 = toMap(response1.getEntities());
        response1 = controller1.tick(null, Direction.RIGHT);
        HashMap<Position, String> expected4 = toMap(response1.getEntities());

        DungeonManiaController controller2 = new DungeonManiaController(seed);
        DungeonResponse response2 = controller2.newGame("lots_of_zombies", "Standard");

        response2 = controller2.tick(null, Direction.DOWN);
        assert(expected1.equals(toMap(response2.getEntities())));
        response2 = controller2.tick(null, Direction.LEFT);
        assert(expected2.equals(toMap(response2.getEntities())));
        response2 = controller2.tick(null, Direction.UP);
        assert(expected3.equals(toMap(response2.getEntities())));
        response2 = controller2.tick(null, Direction.RIGHT);
        assert(expected4.equals(toMap(response2.getEntities())));

        DungeonManiaController controller3 = new DungeonManiaController(seed);
        DungeonResponse response3 = controller3.newGame("lots_of_zombies", "Standard");

        response3 = controller3.tick(null, Direction.DOWN);
        assert(expected1.equals(toMap(response3.getEntities())));
        response3 = controller3.tick(null, Direction.LEFT);
        assert(expected2.equals(toMap(response3.getEntities())));
        response3 = controller3.tick(null, Direction.UP);
        assert(expected3.equals(toMap(response3.getEntities())));
        response3 = controller3.tick(null, Direction.RIGHT);
        assert(expected4.equals(toMap(response3.getEntities())));

    }
}
