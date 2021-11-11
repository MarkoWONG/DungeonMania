package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoalTests {
    @Test
    public void testSingleGoalIncomplete() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("SingleGoal","Standard");

        assertEquals(":treasure",currResponse.getGoals());
    }

    @Test
    public void testSingleGoalComplete() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("SingleGoal","Standard");

        assertEquals(":treasure",currResponse.getGoals());
        // collect the treasure
        currResponse = currController.tick(null, Direction.RIGHT);
        assertEquals("",currResponse.getGoals());
    }

    @Test
    public void testOrGoalsIncomplete() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("OrGoal","Standard");

        assertEquals("( :boulder OR :treasure )",currResponse.getGoals());
    }

    @Test
    public void testOrGoalsComplete() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("OrGoal","Standard");

        assertEquals("( :boulder OR :treasure )",currResponse.getGoals());

        currResponse = currController.tick(null, Direction.RIGHT);
        assertEquals("",currResponse.getGoals());
    }

    @Test
    public void testAndGoalsIncomplete() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("AndGoal","Standard");

        assertEquals("( :boulder AND :treasure )",currResponse.getGoals());
    }

    @Test
    public void testAndGoalsComplete() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("AndGoal","Standard");

        assertEquals("( :boulder AND :treasure )",currResponse.getGoals());

        currResponse = currController.tick(null, Direction.RIGHT);
        assertEquals(":boulder",currResponse.getGoals());
    }

    @Test
    public void testAndGoalsComplete2() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("advanced","Standard");
        assertEquals("( :mercenary AND :treasure )",currResponse.getGoals());
    }
}
