package dungeonmania;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class staticEntitesTests {
    /**
     * helper function to find player postion
     * @param frame
     * @return player postion or null if no player
     */
    public Position findEntityPos(DungeonResponse frame, String id){
        for (EntityResponse ent : frame.getEntities()){
            if (ent.getId().equals(id)){
                return ent.getPosition();
            }
        }
        return null;
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
        Position playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(1,1)));
        new_frame = dungeon.tick("none", Direction.RIGHT);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(1,1)));
        new_frame = dungeon.tick("none", Direction.LEFT);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(1,1)));
        new_frame = dungeon.tick("none", Direction.DOWN);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(1,2)));
        new_frame = dungeon.tick("none", Direction.DOWN);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(1,3)));
        for (int i = 0; i < 10; i++){
            new_frame = dungeon.tick("none", Direction.DOWN);
            playerPos = findEntityPos(new_frame, "player_1");
            assertTrue(playerPos.equals(new Position(1,3)));
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
        Position playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(3,0)));
        assertTrue(orginal_goals.equals(new_frame.getGoals()));

        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.UP);
        assertTrue(playerPos.equals(new Position(3,0)));
        assertTrue(new_frame.getGoals().length() == 0);
    }

    @Test
    public void openClosedDoors(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("key_test", "standard");

        new_frame = dungeon.tick("none", Direction.UP);
        Position playerPos = findEntityPos(new_frame, "player_1");
        // playerPosition should be in the same postion as player don't have a key
        assertTrue(playerPos.equals(new Position(2,2)));

        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.UP);
        playerPos = findEntityPos(new_frame, "player_1");
        // playerPosition should be on top of the opened door (key used to open door)
        assertTrue(playerPos.equals(new Position(3,1)));

        new_frame = dungeon.tick("none", Direction.DOWN);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(3,2)));
        
        // playerPosition should be in the same postion as player don't have a key
        new_frame = dungeon.tick("none", Direction.DOWN);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(3,2)));
    }
    
    // test portals
    @Test
    public void portals(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("protalExit", "standard");
        Position playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(2,2)));

        new_frame = dungeon.tick("none", Direction.RIGHT);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(2,1)));

        new_frame = dungeon.tick("none", Direction.LEFT);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(2,2)));

        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.UP);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(1,0)));

        new_frame = dungeon.tick("none", Direction.DOWN);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(3,3)));

        new_frame = dungeon.tick("none", Direction.LEFT);
        new_frame = dungeon.tick("none", Direction.UP);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(3,1)));

        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.LEFT);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(0,1)));
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
        assertTrue(findEntityPos(new_frame, "player_1").equals(new Position(3,2)));
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(findEntityPos(new_frame, "player_1").equals(new Position(3,2)));
        assertTrue(entityCounter(new_frame, "zombie_toast_spawner") == 1);

        new_frame = dungeon.tick("none", Direction.UP);
        assertTrue(entityCounter(new_frame, "zombie_toast_spawner") == 0);
        
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(findEntityPos(new_frame, "player_1").equals(new Position(4,2)));
    }

    @Test
    public void boulderMovement(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bomb", "standard");
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(findEntityPos(new_frame, "player_1").equals(new Position(4,3)));
        assertTrue(findEntityPos(new_frame, "boulder_1").equals(new Position(5,3)));
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(findEntityPos(new_frame, "player_1").equals(new Position(4,4)));
        assertTrue(findEntityPos(new_frame, "boulder_2").equals(new Position(4,5)));
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(findEntityPos(new_frame, "player_1").equals(new Position(4,4)));
        assertTrue(findEntityPos(new_frame, "boulder_2").equals(new Position(4,5)));
        assertTrue(findEntityPos(new_frame, "boulder_5").equals(new Position(4,6)));
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