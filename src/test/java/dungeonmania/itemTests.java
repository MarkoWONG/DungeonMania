package dungeonmania;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ItemResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;

public class itemTests {
    @Test
    public void singleCollectionTest(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced", "standard");

        // Sword item
        ItemResponse sword = new ItemResponse("1", "sword");

        //move to collect sword
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
            assertFalse(new_frame.getInventory().contains(sword));
        }
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().contains(sword));
    }

    @Test
    public void muiltpleCollectionTest(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced", "standard");

        // Sword item
        ItemResponse sword = new ItemResponse("1", "sword");
        ItemResponse key1 = new ItemResponse("2", "key");
        ItemResponse wood1 = new ItemResponse("3", "wood");
        
        //move to collect sword
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
            assertTrue(new_frame.getInventory().size() == 0);
        }
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 1);
        assertTrue(new_frame.getInventory().contains(sword));

        // move to collect key
        for (int i = 0; i < 5; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
            assertTrue(new_frame.getInventory().size() == 1);
        }
        for (int i = 0; i < 8; i++){
            new_frame = dungeon.tick("none", Direction.DOWN);
            assertTrue(new_frame.getInventory().size() == 1);
        }
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 2);
        assertTrue(new_frame.getInventory().contains(sword));
        assertTrue(new_frame.getInventory().contains(key1));

        // move to collect wood 
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 3);
        assertTrue(new_frame.getInventory().contains(sword));
        assertTrue(new_frame.getInventory().contains(key1));
        assertTrue(new_frame.getInventory().contains(wood1));
    }

    @Test
    public void duplicateItemCollectionTest(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced", "standard");

        // Sword item
        ItemResponse sword = new ItemResponse("1", "sword");
        ItemResponse key1 = new ItemResponse("2", "key");
        ItemResponse key2 = new ItemResponse("3", "key");
        ItemResponse wood1 = new ItemResponse("4", "wood");
        ItemResponse wood2 = new ItemResponse("5", "wood");
        ItemResponse wood3 = new ItemResponse("6", "wood");
        
        //move to collect sword
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
            assertTrue(new_frame.getInventory().size() == 0);
        }
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 1);
        assertTrue(new_frame.getInventory().contains(sword));

        // move to collect key
        for (int i = 0; i < 5; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
            assertTrue(new_frame.getInventory().size() == 1);
        }
        for (int i = 0; i < 8; i++){
            new_frame = dungeon.tick("none", Direction.DOWN);
            assertTrue(new_frame.getInventory().size() == 1);
        }
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 2);
        assertTrue(new_frame.getInventory().contains(sword));
        assertTrue(new_frame.getInventory().contains(key1));
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 3);
        assertTrue(new_frame.getInventory().contains(sword));
        assertTrue(new_frame.getInventory().contains(key1));
        assertTrue(new_frame.getInventory().contains(key2));

        // move to collect wood 
        new_frame = dungeon.tick("none", Direction.LEFT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 4);
        assertTrue(new_frame.getInventory().contains(wood1));
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(new_frame.getInventory().contains(wood2));
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().contains(sword));
        assertTrue(new_frame.getInventory().contains(key1));
        assertTrue(new_frame.getInventory().contains(key2));
        assertTrue(new_frame.getInventory().contains(wood1));
        assertTrue(new_frame.getInventory().contains(wood2));
        assertTrue(new_frame.getInventory().contains(wood3));
    }

    //only one key at a time
    public void oneKeyTest(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced", "standard");

        // Sword item
        ItemResponse key1 = new ItemResponse("2", "key");
        ItemResponse key2 = new ItemResponse("3", "key");
        
        //move to collect key
        for (int i = 0; i < 10; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
            assertTrue(new_frame.getInventory().size() == 0);
        }
        for (int i = 0; i < 8; i++){
            new_frame = dungeon.tick("none", Direction.DOWN);
            assertTrue(new_frame.getInventory().size() == 1);
        }
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 2);
        assertTrue(new_frame.getInventory().contains(key1));

        // only one key
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 2);
        assertTrue(new_frame.getInventory().contains(key1));
        assertFalse(new_frame.getInventory().contains(key2));
    }

    // one key at a time (use a key (craft / open door) then pick up another key

    // crafting

    // using the potions

    // stat change when picked up a sword or armour

    // test one_ring item (respawn and drop rate)

    // durablity of bow and shield

    // test bomb






}
