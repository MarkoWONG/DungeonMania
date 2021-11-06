package dungeonmania;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class staticEntitesTests {
    /**
     * check if an entity type is on a position
     * @param frame
     * @param type
     * @param pos
     * @return
     */
    public Boolean checkEntityOnPosition(DungeonResponse frame, String type, Position pos){
        for (EntityResponse ent : frame.getEntities()){
            if (ent.getPosition().equals(pos) && ent.getType().equals(type)){
                return true;
            }
        }
        return false;
    }
    /**
     * counts the number of entities of type
     * @param frame
     * @param type
     * @return number of entities of type
     */
    public int entityCounter(DungeonResponse frame, String type){
        int counter = 0;
        for (EntityResponse ent : frame.getEntities()){
            if (ent.getType().equals(type)){
                counter++;
            }
        }
        return counter;
    }

    @Test
    public void wallCollosion(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("maze", "Standard");

        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,1)));
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,1)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,1)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,2)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,3)));
        for (int i = 0; i < 10; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
            assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,3)));
        }
    }

    @Test
    public void exit(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/portalExit", "Standard");

        new_frame = dungeon.tick(null, Direction.UP);
        new_frame = dungeon.tick(null, Direction.UP);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,0)));

        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,0)));
        assertTrue(":exit".equals(new_frame.getGoals()));
    }

    @Test
    public void openClosedDoors(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/key_test", "Standard");

        new_frame = dungeon.tick(null, Direction.UP);
        // playerPosition should be in the same postion as player don't have a key
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,2)));

        new_frame = dungeon.tick(null, Direction.RIGHT);
        // can't open door with incorrect key
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,2)));

        new_frame = dungeon.tick(null, Direction.UP);
        // playerPosition should be on top of the opened door (key used to open door)
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,1)));

        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,2)));
        
        // playerPosition should be in the same postion as player don't have a key
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,4)));

        // // use key to build
        // new_frame = dungeon.build("shield");
        // new_frame = dungeon.tick(null, Direction.DOWN);
        // assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,4)));
        
        // new_frame = dungeon.tick(null, Direction.RIGHT);
        // new_frame = dungeon.tick(null, Direction.DOWN);
        // assertTrue(checkEntityOnPosition(new_frame, "player", new Position(5,5)));

    }
    
    // test portals
    @Test
    public void portals(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/portalExit", "Standard");
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,2)));

        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,1)));

        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(0,1)));

        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,2)));

        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,3)));
    }

    @Test
    public void StandardSpawnZombies(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/zombieSpawn", "Standard");

        for (int expected_zoms = 0; expected_zoms < 5; expected_zoms++){
            for (int i = 0; i < 19; i++){
                new_frame = dungeon.tick(null, Direction.UP);
                assertTrue(entityCounter(new_frame, "zombie_toast") == expected_zoms);
            }
            new_frame = dungeon.tick(null, Direction.UP);
            assertTrue(entityCounter(new_frame, "zombie_toast") == (expected_zoms + 1));
        }
    }

    @Test
    public void hardSpawnZombies(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/zombieSpawn", "Hard");

        for (int expected_zoms = 0; expected_zoms < 5; expected_zoms++){
            for (int i = 0; i < 14; i++){
                new_frame = dungeon.tick(null, Direction.UP);
                assertTrue(entityCounter(new_frame, "zombie_toast") == expected_zoms);
            }
            new_frame = dungeon.tick(null, Direction.UP);
            assertTrue(entityCounter(new_frame, "zombie_toast") == (expected_zoms + 1));
        }
    }

    @Test
    public void boulderMovement(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bombs", "Standard");
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,3)));
        assertTrue(checkEntityOnPosition(new_frame, "boulder", new Position(5,3)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,4)));
        assertTrue(checkEntityOnPosition(new_frame, "boulder", new Position(4,5)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,4)));
        assertTrue(checkEntityOnPosition(new_frame, "boulder", new Position(4,5)));
        assertTrue(checkEntityOnPosition(new_frame, "boulder", new Position(4,6)));
    }

    @Test
    public void switches(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/simpleBoulders", "Standard");
        new_frame = dungeon.tick(null, Direction.UP);
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.UP);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(new_frame.getGoals().length() == 0);
    }
}