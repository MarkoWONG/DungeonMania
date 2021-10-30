package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DifficultyTests {
    @Test
    public void testPeacefulMode_noFights() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("difficultytest", "Peaceful");

        IntStream.range(0,2).forEach(tick -> currController.tick(null, Direction.NONE));
        currResponse = currController.tick(null, Direction.NONE);
        // ASSUMES: the character would have killed the mercenary if not peaceful mode - dependent on stats of entities
        assertTrue(currResponse.getEntities()
                .stream().map(EntityResponse::getType)
                .collect(Collectors.toList())
                .contains("mercenary"));
    }

    @Test
    public void testStandardMode_hasFights() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("difficultytest", "Standard");

        IntStream.range(0,2).forEach(tick -> currController.tick(null, Direction.NONE));
        currResponse = currController.tick(null, Direction.NONE);
        // ASSUMES: the character has killed the mercenary by now - dependent on stats of entities
        assertFalse(currResponse.getEntities()
                .stream().map(EntityResponse::getType)
                .collect(Collectors.toList())
                .contains("mercenary"));
    }

    @Test
    public void testHardMode_lessHealth() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("difficultytest", "Hard");

        IntStream.range(0,2).forEach(tick -> currController.tick(null, Direction.NONE));
        currResponse = currController.tick(null, Direction.NONE);
        // ASSUMES: In hard mode, mercenaries will one shot the player
        assertFalse(currResponse.getEntities()
                .stream().map(EntityResponse::getType)
                .collect(Collectors.toList())
                .contains("player"));
    }

    @Test
    public void testHardMode_NoInvinc() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("difficultytest", "Hard");

        // get the potion, use it
        currController.tick(null, Direction.LEFT);
        currController.tick("invincibility_potion", Direction.NONE);

        // character will now die in two ticks because potion doesn't do anything in hard
        IntStream.range(0,2).forEach(tick -> currController.tick(null, Direction.NONE));

        // ASSUMES: In hard mode, mercenaries will one shot the player
        assertFalse(currResponse.getEntities()
                .stream().map(EntityResponse::getType)
                .collect(Collectors.toList())
                .contains("Player"));
    }

    @Test
    public void testHardMode_IncreasedZombSpawn() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("difficultytest2", "Hard");

        IntStream.range(0,16).forEach(tick -> currController.tick(null, Direction.NONE));

        assertTrue(currResponse.getEntities()
                .stream().map(EntityResponse::getType)
                .collect(Collectors.toList())
                .contains("zombie_toast"));
    }

}
