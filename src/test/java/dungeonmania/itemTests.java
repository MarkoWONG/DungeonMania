package dungeonmania;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
    * Checks how many of type item is in the inventory
    * @param frame
    * @param type
    * @return
    */
    public int inventoryItemCount(DungeonResponse frame, String type){
        int counter = 0;
        for (ItemResponse item : frame.getInventory()){
            if (item.getType().equals(type)){
                counter++;
            }
        }
        return counter;
    }

    /**
    * Returns the item id in inventory
    * @param frame
    * @param type
    * @return itemId
    */
    public String getItemId(DungeonResponse frame, String type){
        for (ItemResponse item : frame.getInventory()){
            if (item.getType().equals(type)){
                return item.getId();
            }
        }
        return null;
    }

    @Test
    public void singleCollectionTest(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced-2", "Standard");

        //move to collect sword
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
            assertTrue(inventoryItemCount(new_frame, "sword") == 0);
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);
    }

    @Test
    public void muiltpleCollectionTest(){
    DungeonManiaController dungeon = new DungeonManiaController();
    DungeonResponse new_frame = dungeon.newGame("advanced-2", "Standard");

    //move to collect sword
    for (int i = 0; i < 4; i++){
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 0);
    }
    new_frame = dungeon.tick(null, Direction.RIGHT);
    assertTrue(new_frame.getInventory().size() == 1);
    assertTrue(inventoryItemCount(new_frame, "sword") == 1);

    // move to collect key
    for (int i = 0; i < 5; i++){
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 1);
    }
    for (int i = 0; i < 8; i++){
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 1);
    }
    new_frame = dungeon.tick(null, Direction.DOWN);
    assertTrue(new_frame.getInventory().size() == 2);
    assertTrue(inventoryItemCount(new_frame, "sword") == 1);
    assertTrue(inventoryItemCount(new_frame, "key") == 1);

    // move to collect wood
    new_frame = dungeon.tick(null, Direction.DOWN);
    assertTrue(new_frame.getInventory().size() == 3);
    assertTrue(inventoryItemCount(new_frame, "sword") == 1);
    assertTrue(inventoryItemCount(new_frame, "key") == 1);
    assertTrue(inventoryItemCount(new_frame, "wood") == 1);
    }

    @Test
    public void duplicateItemCollectionTest(){
    DungeonManiaController dungeon = new DungeonManiaController();
    DungeonResponse new_frame = dungeon.newGame("advanced-2", "Standard");

    //move to collect sword
    for (int i = 0; i < 4; i++){
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 0);
    }
    new_frame = dungeon.tick(null, Direction.RIGHT);
    assertTrue(new_frame.getInventory().size() == 1);
    assertTrue(inventoryItemCount(new_frame, "sword") == 1);

    // move to collect key
    for (int i = 0; i < 5; i++){
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 1);
    }
    for (int i = 0; i < 8; i++){
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 1);
    }
    new_frame = dungeon.tick(null, Direction.DOWN);
    assertTrue(new_frame.getInventory().size() == 2);
    assertTrue(inventoryItemCount(new_frame, "sword") == 1);
    assertTrue(inventoryItemCount(new_frame, "key") == 1);

    // move to collect wood
    new_frame = dungeon.tick(null, Direction.DOWN);
    new_frame = dungeon.tick(null, Direction.DOWN);
    assertTrue(new_frame.getInventory().size() == 4);
    assertTrue(inventoryItemCount(new_frame, "wood") == 2);
    new_frame = dungeon.tick(null, Direction.DOWN);
    new_frame = dungeon.tick(null, Direction.DOWN);
    new_frame = dungeon.tick(null, Direction.RIGHT);
    new_frame = dungeon.tick(null, Direction.RIGHT);
    assertTrue(new_frame.getInventory().size() == 8);
    assertTrue(inventoryItemCount(new_frame, "wood") == 3);
    assertTrue(inventoryItemCount(new_frame, "arrow") == 3);
    }

    @Test
    public void sword(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/fighting_items", "Standard");

        assertTrue(inventoryItemCount(new_frame, "sword") == 0);
        //pick up sword
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);
        
        // move to ememy
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick(null, Direction.UP);
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);

        // move to next ememy
        for (int i = 0; i < 5; i++){
            new_frame = dungeon.tick(null, Direction.LEFT);
        }

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);

        // move to next ememy
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        for (int i = 0; i < 7; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "sword") == 0);
    }

    @Test
    public void armour(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/fighting_items", "Standard");

        assertTrue(inventoryItemCount(new_frame, "armour") == 0);
        //pick up armour
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "armour") == 1);
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        new_frame = dungeon.tick(null, Direction.UP);
        new_frame = dungeon.tick(null, Direction.UP);
        // fight enemy 
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "armour") == 1);
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(8, 0)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8, 0)));

        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(inventoryItemCount(new_frame, "armour") == 1);
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(7, 0)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7, 0)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(inventoryItemCount(new_frame, "armour") == 0);
        assertTrue(!checkEntityOnPosition(new_frame, "mercenary", new Position(6, 0)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(6, 0)));
    }

    @Test
    public void bow(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/fighting_items", "Standard");

        assertTrue(inventoryItemCount(new_frame, "bow") == 0);
        // make bow
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick(null, Direction.LEFT);
        }
        new_frame = dungeon.build("bow");
        new_frame = dungeon.tick(null, Direction.LEFT);

        // move to next ememy
        for (int i = 0; i < 2; i++){
            new_frame = dungeon.tick(null, Direction.UP);
        }

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "bow") == 1);
        assertTrue(!checkEntityOnPosition(new_frame, "mercenary", new Position(-2, 0)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-2, 0)));
        

        // move to next ememy
        for (int i = 0; i < 5; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "bow") == 1);
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(-2, 6)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-2, 6)));

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(inventoryItemCount(new_frame, "bow") == 0);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-3, 6)));
    }


    @Test
    public void shield(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/fighting_items", "Standard");

        assertTrue(inventoryItemCount(new_frame, "shield") == 0);
        // make bow
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }
        new_frame = dungeon.build("shield");
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);

        // move to next ememy
        for (int i = 0; i < 2; i++){
            new_frame = dungeon.tick(null, Direction.LEFT);
        }

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(inventoryItemCount(new_frame, "shield") == 0);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-1, 9)));
    }

    @Test
    public void healthPotion(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Hard");
        for (int i = 0; i < 5; i++){ 
            new_frame = dungeon.tick(null, Direction.LEFT);
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        // Fight ememy
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-2,2)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(-2,2)));
        new_frame = dungeon.tick(getItemId(new_frame, "health_potion"), Direction.NONE);
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(-2,2)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-2,2)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(-3,2)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-3,2)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(-4,2)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-4,2)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-4,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(-4,3)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-4,4)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(-4,4)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(!checkEntityOnPosition(new_frame, "mercenary", new Position(-4,5)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-4,5)));
    }

    @Test
    public void StandardInvincibilityPotion(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Standard");
        for (int i = 0; i < 3; i++){ 
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        assertTrue(inventoryItemCount(new_frame, "invincibility_potion") == 1);
        // let enemy catch up
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,3)));
        new_frame = dungeon.tick(getItemId(new_frame, "invincibility_potion"), Direction.NONE);
        assertTrue(inventoryItemCount(new_frame, "invincibility_potion") == 0);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,3)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,2)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(5,3)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(4,3)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(6,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(2,3)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(5,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(1,3)));
        
        // potion wore out
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(2,3)));
    }

    @Test
    public void hardInvincibilityPotion(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Hard");
        for (int i = 0; i < 3; i++){ 
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        assertTrue(inventoryItemCount(new_frame, "invincibility_potion") == 1);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,3)));
        new_frame = dungeon.tick(getItemId(new_frame, "invincibility_potion"), Direction.NONE);
        assertTrue(inventoryItemCount(new_frame, "invincibility_potion") == 0);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,3)));
        //potion no effect
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(7,3)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,2)));
        // player dies
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(!checkEntityOnPosition(new_frame, "player", new Position(8,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(8,3)));
    }

    @Test
    public void invisibilityPotion(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Standard");
        // Starting position
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(2,3)));
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);

        // test normal movement
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(5,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "invisibility_potion") == 1);
        
        // invis movement
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(getItemId(new_frame, "invisibility_potion"), Direction.NONE);
        assertTrue(inventoryItemCount(new_frame, "invisibility_potion") == 0);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(0,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-1,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-2,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-3,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));

        //potion wore off
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-4,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(2,3)));
    }

    @Test
    public void placeBomb(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bombs", "Standard");

        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "bomb") == 1);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(getItemId(new_frame,"bomb"), Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "bomb", new Position(4,2)));
        assertTrue(new_frame.getInventory().size() == 0);
    }

    @Test
    public void activateBombBoulderNotOnSwitch(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bombs", "Standard");

        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(getItemId(new_frame,"bomb"), Direction.NONE);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(!checkEntityOnPosition(new_frame, "switch", new Position(5,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "boulder", new Position(5,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "bomb", new Position(5,2)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,2)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,1)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,3)));
    }

    @Test
    public void activateBombBoulderAlreadyOnSwitch(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bombs", "Standard");

        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.UP);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(getItemId(new_frame,"bomb"), Direction.LEFT);
        assertTrue(!checkEntityOnPosition(new_frame, "switch", new Position(5,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "boulder", new Position(5,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "bomb", new Position(5,2)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,2)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,1)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,2)));
    }

    @Test
    public void craftingOneAtATime(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/crafting", "Standard");

        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        assertTrue(inventoryItemCount(new_frame, "bow") == 0);
        new_frame = dungeon.build("bow");
        assertTrue(inventoryItemCount(new_frame, "bow") == 1);

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }
        assertTrue(inventoryItemCount(new_frame, "shield") == 0);
        new_frame = dungeon.build("shield");
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.LEFT);
        }
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);
        new_frame = dungeon.build("shield");
        assertTrue(inventoryItemCount(new_frame, "shield") == 2);
    }
    @Test
    public void craftingAllAtATime(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/crafting", "Standard");
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.LEFT);
        }
        assertTrue(inventoryItemCount(new_frame, "bow") == 0);
        new_frame = dungeon.build("bow");
        assertTrue(inventoryItemCount(new_frame, "bow") == 1);
        assertTrue(inventoryItemCount(new_frame, "shield") == 0);
        new_frame = dungeon.build("shield");
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);
        new_frame = dungeon.build("shield");
        assertTrue(inventoryItemCount(new_frame, "shield") == 2);
    }

    @Test
    public void onlyOneKeyTest(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced-2", "Standard");

        //move to collect key
        for (int i = 0; i < 10; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        for (int i = 0; i < 8; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
            assertTrue(new_frame.getInventory().size() == 1);
        }
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 2);
        assertTrue(inventoryItemCount(new_frame, "key") == 1);

        // only one key
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 2);
        assertTrue(inventoryItemCount(new_frame, "key") == 1);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);
    }

    @Test
    public void usingKey(){
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
    }

    // test one_ring item (respawn and drop rate) in fighting tests
    @Test
    public void oneRing(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/one_ring", "Hard");
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 1);
        // fight enemy and dies activing one_ring
        new_frame = dungeon.tick(null, Direction.RIGHT);
        
        //fight enemy
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(5,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(5,1)));
        
        
        // defeated by enemy
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(!checkEntityOnPosition(new_frame, "player", new Position(5,2)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(5,2)));
    }

    @Test
    public void MidnightArmour(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/midnightArmour", "Standard");

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);

        // wait for zombie spawn
        for (int i = 0; i < 14; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }

        assertTrue(inventoryItemCount(new_frame, "midnight_armour") == 0);
        assertEquals(new_frame.getBuildables(), Arrays.asList("midnight_armour"));
        new_frame = dungeon.build("midnight_armour");
        assertTrue(inventoryItemCount(new_frame, "midnight_armour") == 1);
        assertEquals(new_frame.getBuildables(), Arrays.asList("midnight_armour"));

        // spawn zombie
        assertTrue(entityCounter(new_frame, "zombie_toast") == 0);
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "zombie_toast", new Position(3,5)));
        assertEquals(new_frame.getBuildables(), Arrays.asList());

        // kill zombie
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(entityCounter(new_frame, "zombie_toast") == 0);

        // craftable again as no more zombie
        new_frame = dungeon.tick(null, Direction.UP);
        assertEquals(new_frame.getBuildables(), Arrays.asList("midnight_armour"));
        new_frame = dungeon.build("midnight_armour");
        assertTrue(inventoryItemCount(new_frame, "midnight_armour") == 2);
    }
}
