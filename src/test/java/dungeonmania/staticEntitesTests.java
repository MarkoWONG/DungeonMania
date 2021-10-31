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
        DungeonResponse new_frame = dungeon.newGame("maze", "standard");

        new_frame = dungeon.tick("none", Direction.UP);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,1)));
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,1)));
        new_frame = dungeon.tick("none", Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,1)));
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,2)));
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,3)));
        for (int i = 0; i < 10; i++){
            new_frame = dungeon.tick("none", Direction.DOWN);
            assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,3)));
        }
    }

    @Test
    public void exit(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("protalExit", "standard");
        String orginal_goals = new_frame.getGoals();

        new_frame = dungeon.tick("none", Direction.UP);
        new_frame = dungeon.tick("none", Direction.UP);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,0)));
        assertTrue(orginal_goals.equals(new_frame.getGoals()));

        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.UP);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,0)));
        assertTrue(new_frame.getGoals().length() == 0);
    }

    @Test
    public void openClosedDoors(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("key_test", "standard");

        new_frame = dungeon.tick("none", Direction.UP);
        // playerPosition should be in the same postion as player don't have a key
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,2)));

        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.UP);
        // playerPosition should be on top of the opened door (key used to open door)
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,1)));

        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,2)));
        
        // playerPosition should be in the same postion as player don't have a key
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,2)));
    }
    
    // test portals
    @Test
    public void portals(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("protalExit", "standard");
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,2)));

        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,1)));

        new_frame = dungeon.tick("none", Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,2)));

        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.UP);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(1,0)));

        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,3)));

        new_frame = dungeon.tick("none", Direction.LEFT);
        new_frame = dungeon.tick("none", Direction.UP);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,1)));

        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(0,1)));
    }

    @Test
    public void standardSpawnZombies(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("zombieSpawn", "standard");

        for (int expected_zoms = 0; expected_zoms < 5; expected_zoms++){
            for (int i = 0; i < 19; i++){
                new_frame = dungeon.tick("none", Direction.UP);
                assertTrue(entityCounter(new_frame, "zombie_toast") == expected_zoms);
            }
            new_frame = dungeon.tick("none", Direction.UP);
            assertTrue(entityCounter(new_frame, "zombie_toast") == (expected_zoms + 1));
        }
    }

    @Test
    public void hardSpawnZombies(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("zombieSpawn", "hard");

        for (int expected_zoms = 0; expected_zoms < 5; expected_zoms++){
            for (int i = 0; i < 14; i++){
                new_frame = dungeon.tick("none", Direction.UP);
                assertTrue(entityCounter(new_frame, "zombie_toast") == expected_zoms);
            }
            new_frame = dungeon.tick("none", Direction.UP);
            assertTrue(entityCounter(new_frame, "zombie_toast") == (expected_zoms + 1));
        }
    }

    @Test
    public void destoryZombiesSpawner(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("zombie", "standard");
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,2)));
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,2)));
        assertTrue(entityCounter(new_frame, "zombie_toast_spawner") == 1);

        new_frame = dungeon.tick("none", Direction.UP);
        assertTrue(entityCounter(new_frame, "zombie_toast_spawner") == 0);
        
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,2)));
    }

    @Test
    public void boulderMovement(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bomb", "standard");
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,2)));
        assertTrue(checkEntityOnPosition(new_frame, "boulder", new Position(5,3)));
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,4)));
        assertTrue(checkEntityOnPosition(new_frame, "boulder", new Position(4,5)));
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,4)));
        assertTrue(checkEntityOnPosition(new_frame, "boulder", new Position(4,5)));
        assertTrue(checkEntityOnPosition(new_frame, "boulder", new Position(4,6)));
    }

    @Test
    public void switches(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("simpleBoulders", "standard");
        new_frame = dungeon.tick("none", Direction.UP);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.UP);
        new_frame = dungeon.tick("none", Direction.LEFT);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getGoals().length() == 0);
    }
}