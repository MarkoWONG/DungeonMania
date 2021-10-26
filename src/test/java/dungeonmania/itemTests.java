package dungeonmania;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class itemTests {
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

    // stat change when picked up a sword or armour
    @Test
    public void swordStatChange(){

    }

    @Test
    public void armourStatChange(){
        
    }

    // durablity of bow and shield
    @Test
    public void bowDurablity(){
        
    }

    @Test
    public void shieldDurablity(){
        
    }

    // using the potions
    @Test
    public void healthPotion(){
        
    }
    @Test
    public void standardInvincibilityPotion(){
        
    }
    @Test
    public void hardInvincibilityPotion(){
        
    }
    @Test
    public void invisibilityPotion(){
        
    }

    // test bomb
    @Test
    public void placeBomb(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bomb", "standard");
        ItemResponse bomb = new ItemResponse("1", "bomb");
        
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().contains(bomb));
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("1", Direction.LEFT);
        assertTrue(findEntityPos(new_frame, "bomb_1").equals(new Position(4,2)));
        assertTrue(new_frame.getInventory().size() == 0);
    }

    @Test
    public void activateBombBoulderNotOnSwitch(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bomb", "standard");
        
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("1", Direction.LEFT);
        new_frame = dungeon.tick("none", Direction.LEFT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(findEntityPos(new_frame, "switch_2") == null);
        assertTrue(findEntityPos(new_frame, "boulder_1") == null);
        assertTrue(findEntityPos(new_frame, "wall_9") == null);
        assertTrue(findEntityPos(new_frame, "wall_11") == null);
        assertTrue(findEntityPos(new_frame, "wall_15") == null);
        assertTrue(findEntityPos(new_frame, "player_1").equals(new Position(4,3)));
    }

    @Test
    public void activateBombBoulderAlreadyOnSwitch(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bomb", "standard");
        
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.UP);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("1", Direction.LEFT);
        assertTrue(findEntityPos(new_frame, "switch_2") == null);
        assertTrue(findEntityPos(new_frame, "boulder_1") == null);
        assertTrue(findEntityPos(new_frame, "wall_9") == null);
        assertTrue(findEntityPos(new_frame, "wall_11") == null);
        assertTrue(findEntityPos(new_frame, "wall_15") == null);
        assertTrue(findEntityPos(new_frame, "player_1").equals(new Position(4,2)));
    }

    // crafting
    @Test
    public void craftingOneAtATime(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("crafting", "standard");
        ItemResponse bow = new ItemResponse("5", "bow");
        ItemResponse shield1 = new ItemResponse("9", "shield");
        ItemResponse shield2 = new ItemResponse("13", "shield");

        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
        }
        assertFalse(new_frame.getInventory().contains(bow));
        new_frame = dungeon.build("bow");
        assertTrue(new_frame.getInventory().contains(bow));

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick("none", Direction.DOWN);
        }
        assertFalse(new_frame.getInventory().contains(shield1));
        new_frame = dungeon.build("shield");
        assertTrue(new_frame.getInventory().contains(shield1));

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick("none", Direction.LEFT);
        }
        assertFalse(new_frame.getInventory().contains(shield2));
        new_frame = dungeon.build("shield");
        assertTrue(new_frame.getInventory().contains(shield2));
    }
    @Test
    public void craftingAllAtATime(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("crafting", "standard");
        ItemResponse bow = new ItemResponse("11", "bow");
        ItemResponse shield1 = new ItemResponse("12", "shield");
        ItemResponse shield2 = new ItemResponse("13", "shield");

        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
        }
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick("none", Direction.DOWN);
        }
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick("none", Direction.LEFT);
        }
        assertFalse(new_frame.getInventory().contains(bow));
        new_frame = dungeon.build("bow");
        assertTrue(new_frame.getInventory().contains(bow));
        assertFalse(new_frame.getInventory().contains(shield1));
        new_frame = dungeon.build("shield");
        assertTrue(new_frame.getInventory().contains(shield1));
        assertFalse(new_frame.getInventory().contains(shield2));
        new_frame = dungeon.build("shield");
        assertTrue(new_frame.getInventory().contains(shield2));
    }

    //only one key at a time
    public void onlyOneKeyTest(){
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
    @Test
    public void usingKey(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("key_test", "standard");

        new_frame = dungeon.tick("none", Direction.UP);
        //find playerPosition
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

        // collect key and 2 wood and build a shield
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.build("shield");

        // no key = locked door
        new_frame = dungeon.tick("none", Direction.DOWN);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(4,4)));
        
        // collect key and open door
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        playerPos = findEntityPos(new_frame, "player_1");
        assertTrue(playerPos.equals(new Position(5,5)));
    }

    @Test
    public void zombieSpawnWithArmour(){
        
    }

    // defeating a zombie with armour drops an new armour
    @Test
    public void zombieArmourDrop(){
        
    }

    @Test
    public void mercenarySpawnWithArmour(){
        
    }
    // defeating a mercenary with armour drops an new armour
    @Test
    public void mercenaryArmourDrop(){
        
    }
    
    // test one_ring item (respawn and drop rate)
    @Test
    public void oneRing(){
        
    }

    // one big intergation test
    @Test
    public void itemsIntergationTest(){

    }

}
