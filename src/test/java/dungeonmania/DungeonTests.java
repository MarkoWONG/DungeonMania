package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonTests {
    @Test
    public void testEntityMapCreation_StaticOnly() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_static", "Standard");
        assertEquals(8, (int) currResponse.getEntities().size());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("wall")).filter(e -> e.getPosition().equals(new Position(0, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("exit")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("boulder")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("switch")).filter(e -> e.getPosition().equals(new Position(3, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("door")).filter(e -> e.getPosition().equals(new Position(4, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("portal_blue")).filter(e -> e.getPosition().equals(new Position(5, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("zombie_toast_spawner")).filter(e -> e.getPosition().equals(new Position(6, 0))).count());
    }

    @Test
    public void testEntityMapCreation_MobsOnly() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_moving", "Standard");
        assertEquals(4, (int) currResponse.getEntities().size());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("spider")).filter(e -> e.getPosition().equals(new Position(0, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("zombie_toast")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("mercenary")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());
    }

    @Test
    public void testEntityMapCreation_CharacterOnly() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_character", "Standard");
        assertEquals(1, (int) currResponse.getEntities().size());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("player")).filter(e -> e.getPosition().equals(new Position(0, 0))).count());
    }

    @Test
    public void testEntityMapCreation_CollectableOnly() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_collectable", "Standard");
        assertEquals(12, (int) currResponse.getEntities().size());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("treasure")).filter(e -> e.getPosition().equals(new Position(0, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("key")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("health_potion")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("invincibility_potion")).filter(e -> e.getPosition().equals(new Position(3, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("invisibility_potion")).filter(e -> e.getPosition().equals(new Position(4, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("wood")).filter(e -> e.getPosition().equals(new Position(5, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("arrow")).filter(e -> e.getPosition().equals(new Position(6, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("bomb")).filter(e -> e.getPosition().equals(new Position(7, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("sword")).filter(e -> e.getPosition().equals(new Position(8, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("armour")).filter(e -> e.getPosition().equals(new Position(9, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("one_ring")).filter(e -> e.getPosition().equals(new Position(10, 0))).count());
    }

    @Test
    public void testItemUsedAndPickedUpOnTick() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("difficultytest", "Standard");

        // get the potion, use it
        currResponse = currController.tick(null, Direction.LEFT);

        assertTrue(currResponse.getInventory()
                .stream().map(ItemResponse::getType)
                .collect(Collectors.toList())
                .contains("invincibility_potion"));

        // get item ID, only one item possible so we can hardcode this
        String invisPotionId = currResponse.getInventory().get(0).getId();

        currResponse = currController.tick(invisPotionId, Direction.LEFT);

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
    public void testClickingSpawner() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("clickableEntities1", "Standard");

        // get the id of the spawner
        String spawnerId = currResponse.getEntities().stream().filter(e -> e.getType().equals("zombie_toast_spawner")).findAny().get().getId();

        // assert spawner interactable immediately
        assertTrue(currResponse.getEntities().stream().filter(e -> e.getType().equals("zombie_toast_spawner")).findAny().get().isInteractable());

        currResponse = currController.tick(null, Direction.RIGHT);

        // click on the adjacent spawner, doesnt tick the game world
        currResponse = currController.interact(spawnerId);

        // assert spawner is killed
        assertFalse(currResponse.getEntities()
                .stream().map(EntityResponse::getType)
                .collect(Collectors.toList())
                .contains("zombie_toast_spawner"));
    }

    @Test
    public void testClickingMercenary() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("clickableEntities2", "Standard");

        // get the id of the mercenary
        String mercenaryId = currResponse.getEntities().stream().filter(e -> e.getType().equals("mercenary")).findAny().get().getId();

        // assert mercenary interactable immediately
        assertTrue(currResponse.getEntities().stream().filter(e -> e.getType().equals("mercenary")).findAny().get().isInteractable());

        // go left and collect the treasure
        currController.tick(null, Direction.LEFT);

        // click on the mercenary
        currResponse = currController.interact(mercenaryId);

        // assert no gold in inventory
        assertFalse(currResponse.getInventory()
                .stream().map(ItemResponse::getType)
                .collect(Collectors.toList())
                .contains("treasure"));

        // assert mercenary not interactable anymore
        assertFalse(currResponse.getEntities().stream().filter(e -> e.getType().equals("mercenary")).findAny().get().isInteractable());
    }




    @Test
    public void testBuilding() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("buildableEntities", "Standard");

        IntStream.range(0,3).forEach(tick -> currController.tick(null, Direction.UP));
        currResponse = currController.build("bow");
        assertTrue(currResponse.getInventory()
                .stream().map(ItemResponse::getType)
                .collect(Collectors.toList())
                .contains("bow"));

        IntStream.range(0,3).forEach(tick -> currController.tick(null, Direction.UP));
        currResponse = currController.build("shield");
        assertTrue(currResponse.getInventory()
                .stream().map(ItemResponse::getType)
                .collect(Collectors.toList())
                .contains("shield"));
    }

}
