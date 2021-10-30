package dungeonmania;

import dungeonmania.goal.Goal;
import dungeonmania.goal.GoalManager;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class GoalTests {
    @Test
    public void testSingleGoal() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("SingleGoal","Standard");

        assertEquals(":treasure",currResponse.getGoals());
        // collect the treasure
        currResponse = currController.tick(null, Direction.RIGHT);
        assertEquals("",currResponse.getGoals());
    }

    @Test
    public void testOrGoals() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("OrGoal","Standard");

        assertEquals("(:boulders OR :treasure)",currResponse.getGoals());

        currResponse = currController.tick(null, Direction.RIGHT);
        assertEquals("",currResponse.getGoals());
    }

    @Test
    public void testAndGoals() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("AndGoal","Standard");

        assertEquals("(:boulders AND :treasure)",currResponse.getGoals());

        currResponse = currController.tick(null, Direction.RIGHT);
        assertEquals(":boulders",currResponse.getGoals());
    }

    @Test
    public void complexGoals() {
    }
}
