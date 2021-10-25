package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

public class DungeonTests {
    @Test
    public void testEntityMapCreation_StaticOnly() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_static", "Standard");
        assertEquals(7, (int) currResponse.getEntities().size());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("wall")).filter(e -> e.getPosition().equals(new Position(0, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("exit")).filter(e -> e.getPosition().equals(new Position(1, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("boulder")).filter(e -> e.getPosition().equals(new Position(2, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("switch")).filter(e -> e.getPosition().equals(new Position(3, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("door")).filter(e -> e.getPosition().equals(new Position(4, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("portal")).filter(e -> e.getPosition().equals(new Position(5, 0))).count());
        assertEquals(1, currResponse.getEntities().stream().filter(e -> e.getType().equals("zombie_toast_spawner")).filter(e -> e.getPosition().equals(new Position(6, 0))).count());
    }

    @Test
    public void testEntityMapCreation_MobsOnly() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_moving", "Standard");
        assertEquals(3, (int) currResponse.getEntities().size());
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
        assertEquals(11, (int) currResponse.getEntities().size());
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

}
