package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonAdapterTests {

    @Test
    public void testDungeonName() {
        Dungeon currDungeon = new Dungeon("dungeon_static","Standard");
        DungeonResponseAdapter adapter = new DungeonResponseAdapter(currDungeon);
        DungeonResponse currResponse = adapter.createDungResponse();
        assertEquals("dungeon_static",currResponse.getDungeonName());
    }

    @Test
    public void testEntityOnly() {
        Dungeon currDungeon = new Dungeon("dungeon_static","Standard");
        DungeonResponseAdapter adapter = new DungeonResponseAdapter(currDungeon);
        DungeonResponse currResponse = adapter.createDungResponse();
        assertEquals(8, (int) currResponse.getEntities().size());
        // this test is already done by DungeonTests (more or less), so we'll keep it short
    }

    @Test
    public void testItemResponse() {
        Dungeon currDungeon = new Dungeon("dungeon_collectable","Standard");
        IntStream.range(0,10).forEach(tick -> currDungeon.tick(null, Direction.LEFT));
        DungeonResponseAdapter adapter = new DungeonResponseAdapter(currDungeon);
        DungeonResponse currResponse = adapter.createDungResponse();
        List<String> allItems = currResponse.getInventory().stream().map(ItemResponse::getType).collect(Collectors.toList());
        assertEquals(10, (int) allItems.size());
        assertTrue(allItems.contains("key"));
        assertTrue(allItems.contains("health_potion"));
        assertTrue(allItems.contains("invincibility_potion"));
        assertTrue(allItems.contains("invisibility_potion"));
        assertTrue(allItems.contains("wood"));
        assertTrue(allItems.contains("arrow"));
        assertTrue(allItems.contains("bomb"));
        assertTrue(allItems.contains("sword"));
        assertTrue(allItems.contains("armour"));
        assertTrue(allItems.contains("one_ring"));
    }

    @Test
    public void testBuildableContents() {
        Dungeon currDungeon = new Dungeon("buildableEntities","Standard");
        IntStream.range(0,6).forEach(tick -> currDungeon.tick(null, Direction.UP));
        DungeonResponseAdapter adapter = new DungeonResponseAdapter(currDungeon);
        DungeonResponse currResponse = adapter.createDungResponse();
        List<String> allBuildables = currResponse.getBuildables();
        assertEquals(2, allBuildables.size());
        assertTrue(allBuildables.contains("bow"));
        assertTrue(allBuildables.contains("shield"));
    }

    @Test
    public void testGoalContents() {
        Dungeon currDungeon = new Dungeon("advanced","Standard");
        DungeonResponseAdapter adapter = new DungeonResponseAdapter(currDungeon);
        DungeonResponse currResponse = adapter.createDungResponse();
        String goals = currResponse.getGoals();
        assertTrue(goals.contains("enemies"));
        assertTrue(goals.contains("treasure"));
        assertTrue(goals.contains("AND"));
    }

}
