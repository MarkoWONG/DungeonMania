package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PersistenceTests {
    // these tests are duplicates of different dungeon tests (core functionality), except wrapped in saveGame and loadGame calls

    @Test
    public void saveAndLoad_NoTicks() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_static", "Standard");

        currResponse = currController.saveGame("dungeon_static-1636179960394");
        assert currController.allGames().contains("dungeon_static-1636179960394");

        currResponse = currController.loadGame("dungeon_static-1636179960394");

        assertEquals(8, (int) currResponse.getEntities().size());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("wall")).filter(e -> e.getPosition().equals(new Position(0, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("exit")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("boulder")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("switch")).filter(e -> e.getPosition().equals(new Position(3, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("door")).filter(e -> e.getPosition().equals(new Position(4, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("portal")).filter(e -> e.getPosition().equals(new Position(5, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("zombie_toast_spawner")).filter(e -> e.getPosition().equals(new Position(6, 0))).count());
    }

    @Test
    public void saveAndLoad_Tick() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_static", "Standard");
        currController.tick(null, Direction.RIGHT);
        currResponse = currController.saveGame("dungeon_static-1636179961410");
        assert currController.allGames().contains("dungeon_static-1636179961410");
        currResponse = currController.newGame("dungeon_static", "Standard"); // reset the level
        currResponse = currController.loadGame("dungeon_static-1636179961410"); // restore the level state

        assertEquals(8, (int) currResponse.getEntities().size());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(8, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("wall")).filter(e -> e.getPosition().equals(new Position(0, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("exit")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("boulder")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("switch")).filter(e -> e.getPosition().equals(new Position(3, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("door")).filter(e -> e.getPosition().equals(new Position(4, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("portal")).filter(e -> e.getPosition().equals(new Position(5, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("zombie_toast_spawner")).filter(e -> e.getPosition().equals(new Position(6, 0))).count());
    }

    @Test
    public void saveAndLoad_ItemUseTick() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("difficultytest", "Standard");
        currResponse = currController.tick(null, Direction.LEFT);
        String invisPotionId = currResponse.getInventory().get(0).getId();
        currResponse = currController.tick(invisPotionId, Direction.LEFT);
        // with the item used, save the game
        currController.saveGame("difficultytest-1636179960394");
        assert currController.allGames().contains("difficultytest-1636179960394");
        currController.newGame("difficultytest", "Standard"); // reset
        currController.loadGame("difficultytest-1636179960394"); // restore

        // assert no potion in inventory
        assertFalse(currResponse.getInventory()
                .stream().map(ItemResponse::getType)
                .collect(Collectors.toList())
                .contains("invincibility_potion"));
        // assert no potion in dungeon
        assertFalse(currResponse.getEntities()
                .stream().map(EntityResponse::getType)
                .collect(Collectors.toList())
                .contains("invincibility_potion"));
    }

    @Test
    public void saveAndLoad_GoalPreserved() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("AndGoal","Standard");
        currResponse = currController.tick(null, Direction.RIGHT);
        assertEquals(":boulder",currResponse.getGoals()); // this is what we hope to see at the end
        currController.saveGame("AndGoal-1636179960394");
        assert currController.allGames().contains("AndGoal-1636179960394");
        currController.newGame("AndGoal", "Standard"); // reset
        currController.loadGame("AndGoal-1636179960394"); // restore
        assertEquals(":boulder",currResponse.getGoals()); //
    }
}
