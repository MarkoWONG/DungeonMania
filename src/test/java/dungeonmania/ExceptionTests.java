package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTests {

    @Test
    public void testUseItemExceptions() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_collectable", "Standard",1L);
        String keyID = currResponse.getEntities().stream().filter(e -> e.getType().equals("key")).findAny().get().getId();
        String healthPotionID = currResponse.getEntities().stream().filter(e -> e.getType().equals("health_potion")).findAny().get().getId();
        assertThrows(IllegalArgumentException.class,() -> currController.tick(keyID, Direction.NONE));
        assertThrows(InvalidActionException.class,() -> currController.tick(healthPotionID, Direction.NONE));
    }

    @Test
    public void testClickException() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_collectable", "Standard",1L);
        assertThrows(IllegalArgumentException.class,() -> currController.interact("badID"));
    }

    @Test
    public void testBuildableExceptions() {
        Dungeon currDungeon = new Dungeon("buildableEntities", "Standard", 123L);
        IntStream.range(0, 2).forEach(tick -> currDungeon.tick(null, Direction.UP));
        assertThrows(IllegalArgumentException.class,() -> currDungeon.build("badItem"));
        assertThrows(InvalidActionException.class,() -> currDungeon.build("bow"));
    }

    @Test
    public void testNewGameException() {
        DungeonManiaController currController = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class,() -> currController.newGame("dungeon_collectable", "BadGameMode"));
        assertThrows(IllegalArgumentException.class,() -> currController.newGame("BADDUNGEONNAME", "Standard"));
    }
}
