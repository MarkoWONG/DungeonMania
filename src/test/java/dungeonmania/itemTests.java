package dungeonmania;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.exceptions.InvalidActionException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    /**
    * Returns the entity id
    * @param frame
    * @param type
    * @return itemId
    */
    public String getEntityId(DungeonResponse frame, String type){
        for (EntityResponse ent : frame.getEntities()){
            if (ent.getType().equals(type)){
                return ent.getId();
            }
        }
        return null;
    }

    /**
     * is the id entity interactable
     * @param frame
     * @param id
     * @return true for is interactable
     */
    public boolean iteractable(DungeonResponse frame, String id){
        for (EntityResponse ent : frame.getEntities()){
            if (ent.getId().equals(id) && ent.isInteractable()){
                return true;
            }
        }
        return false;
    }

    @Test
    public void singleCollectionTest(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced-2", "Standard",1636704092283L);

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
    DungeonResponse new_frame = dungeon.newGame("advanced-2", "Standard", 1636704092283L);

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
    DungeonResponse new_frame = dungeon.newGame("advanced-2", "Standard", 1636704092283L);

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
        DungeonResponse new_frame = dungeon.newGame("test_maps/fighting_items", "Standard", 1636702667232L);

        assertTrue(inventoryItemCount(new_frame, "sword") == 0);
        //pick up sword
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);
        
        // move to ememy
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.UP);
        }

        // fight enemy 
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);

        // fight next enemy 
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);

        // fight next enemy 
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(inventoryItemCount(new_frame, "sword") == 0);
    }

    @Test
    public void armour(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/fighting_items", "Standard", 1636702114052L);

        assertTrue(inventoryItemCount(new_frame, "armour") == 0);
        //pick up armour
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "armour") == 1);
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        // fight mercenary 
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "armour") == 1);
        // fight mercenary 
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "armour") == 1);
        // fight mercenary 
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "armour") == 0);
    }

    @Test
    public void bow(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/fighting_items", "Standard", 1636703337536L);

        assertTrue(inventoryItemCount(new_frame, "bow") == 0);
        // make bow
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick(null, Direction.LEFT);
        }
        new_frame = dungeon.build("bow");
        // move to mer
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.DOWN);
        // fight mer
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "bow") == 1);

        // move to mer
        new_frame = dungeon.tick(null, Direction.UP);
        // fight mer
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "bow") == 1);
        // fight mer
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "bow") == 0);
    }

    @Test
    public void shield(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/fighting_items", "Standard", 1636703974808L);

        assertTrue(inventoryItemCount(new_frame, "shield") == 0);
        // make bow
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }
        new_frame = dungeon.build("shield");
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);

        // move to next ememy
        new_frame = dungeon.tick(null, Direction.RIGHT);

        // fight mer 
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);

        // fight mer 
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);

        // fight mer 
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "shield") == 0);
    }

    @Test
    public void healthPotion(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Hard", 1636704092283L);
        for (int i = 0; i < 5; i++){ 
            new_frame = dungeon.tick(null, Direction.LEFT);
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        // Fight mer
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "health_potion") == 1);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-2,2)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(-2,2)));
        new_frame = dungeon.tick(getItemId(new_frame, "health_potion"), Direction.NONE);
        assertTrue(inventoryItemCount(new_frame, "health_potion") == 0);
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
        DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Standard", 1636704092283L);
        for (int i = 0; i < 3; i++){ 
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        assertTrue(inventoryItemCount(new_frame, "invincibility_potion") == 1);
        // let enemy catch up
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,2)));
        new_frame = dungeon.tick(getItemId(new_frame, "invincibility_potion"), Direction.NONE);
        assertTrue(inventoryItemCount(new_frame, "invincibility_potion") == 0);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,1)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,2)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(5,1)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(4,1)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,1)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(6,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(2,1)));
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(6,2)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(1,1)));
        
        // potion wore out
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(6,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(2,1)));
    }

    @Test
    public void hardInvincibilityPotion(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Hard",1636704092283L);
        for (int i = 0; i < 3; i++){ 
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        assertTrue(inventoryItemCount(new_frame, "invincibility_potion") == 1);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,2)));
        new_frame = dungeon.tick(getItemId(new_frame, "invincibility_potion"), Direction.NONE);
        assertTrue(inventoryItemCount(new_frame, "invincibility_potion") == 0);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,1)));
        //potion no effect
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(7,1)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,2)));
        // player dies
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(!checkEntityOnPosition(new_frame, "player", new Position(7,2)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(7,2)));
    }

    @Test
    public void invisibilityPotion(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Standard",1636704092283L);
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
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,1)));
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "invisibility_potion") == 1);
        
        // invis movement
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(getItemId(new_frame, "invisibility_potion"), Direction.NONE);
        assertTrue(inventoryItemCount(new_frame, "invisibility_potion") == 0);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(0,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,1)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-1,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,1)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-2,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,1)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-3,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,1)));

        //potion wore off
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-4,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(5,1)));
    }

    @Test
    public void placeBomb(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("bombs", "Standard", 1636704092283L);

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
        DungeonResponse new_frame = dungeon.newGame("bombs", "Standard", 1636704092283L);

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
        DungeonResponse new_frame = dungeon.newGame("bombs", "Standard", 1636704092283L);

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
        DungeonResponse new_frame = dungeon.newGame("test_maps/crafting", "Standard", 1636704092283L);

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
        DungeonResponse new_frame = dungeon.newGame("test_maps/crafting", "Standard", 1636704092283L);
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
        DungeonResponse new_frame = dungeon.newGame("advanced-2", "Standard", 1636704092283L);

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
        DungeonResponse new_frame = dungeon.newGame("test_maps/key_test", "Peaceful", 1636704092283L);

        new_frame = dungeon.tick(null, Direction.UP);
        // playerPosition should be in the same postion as player don't have a key
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,2)));

        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3,2)));
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

    @Test
    public void oneRing(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/one_ring", "Hard", 1636711331450L);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 1);
        // fight enemy and dies activing one_ring
        new_frame = dungeon.tick(null, Direction.RIGHT);
        
        //fight enemy
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 0);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(4,1)));
        
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,2)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(4,2)));

        // defeated by enemy
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(!checkEntityOnPosition(new_frame, "player", new Position(4,3)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(4,3)));
    }

    @Test
    public void MidnightArmour(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/midnightArmour", "Standard", 1636711524052L);

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

    @Test
    public void SceptreCraftingArrowKey(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636711524052L);
        // collect materials
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick(null, Direction.LEFT);
        }
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 0);
        assertEquals(new_frame.getBuildables(), Arrays.asList("sceptre"));
        new_frame = dungeon.build("sceptre");
        assertEquals(new_frame.getBuildables(), Arrays.asList());
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 1);
    }

    @Test
    public void SceptreCraftingArrowTreasure(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636711524052L);
        // collect materials
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.LEFT);
        }
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 0);
        assertEquals(new_frame.getBuildables(), Arrays.asList("sceptre"));
        new_frame = dungeon.build("sceptre");
        assertEquals(new_frame.getBuildables(), Arrays.asList());
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 1);
    }
    @Test
    public void SceptreCraftingWoodKey(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636711524052L);
        // collect materials
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.UP);
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 0);
        assertEquals(new_frame.getBuildables(), Arrays.asList("sceptre"));
        new_frame = dungeon.build("sceptre");
        assertEquals(new_frame.getBuildables(), Arrays.asList());
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 1);
    }
    @Test
    public void SceptreCraftingWoodTreasure(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636711524052L);
        // collect materials
        new_frame = dungeon.tick(null, Direction.DOWN);
        for (int i = 0; i < 3; i++){
        new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 0);
        assertEquals(new_frame.getBuildables(), Arrays.asList("sceptre"));
        new_frame = dungeon.build("sceptre");
        assertEquals(new_frame.getBuildables(), Arrays.asList());
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 1);
    }


    @Test
    public void SceptreBribeAssassinDurablity(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636713824112L);

        // collect materials
        new_frame = dungeon.tick(null, Direction.DOWN);
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        
        new_frame = dungeon.build("sceptre");
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 1);
        String id = getEntityId(new_frame, "assassin");
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(id);});

        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 1);

        assertTrue(iteractable(new_frame, getEntityId(new_frame, "assassin")));
        new_frame = dungeon.interact(getEntityId(new_frame, "assassin"));
        assertTrue(inventoryItemCount(new_frame, "treasure") == 1);
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 1);
        assertTrue(!iteractable(new_frame, getEntityId(new_frame, "assassin")));

        // move to battle/vist ally
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.UP);

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
            new_frame = dungeon.tick(null, Direction.LEFT);
            assertTrue(!iteractable(new_frame, getEntityId(new_frame, "assassin")));
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        // mind control wore off
        assertTrue(iteractable(new_frame, getEntityId(new_frame, "assassin")));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,3)));
        assertTrue(checkEntityOnPosition(new_frame, "assassin", new Position(8,3)));

        new_frame = dungeon.tick(null, Direction.DOWN);
        // player dies but revive cause one_ring
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,4)));
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 0);
        assertTrue(checkEntityOnPosition(new_frame, "assassin", new Position(8,4)));

        // assassin dies
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(9,4)));
        assertTrue(!checkEntityOnPosition(new_frame, "assassin", new Position(9,4)));
    }

    @Test
    public void SceptreBribeMercenaryDurablity(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636713824112L);

        // collect materials
        new_frame = dungeon.tick(null, Direction.DOWN);
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        
        new_frame = dungeon.build("sceptre");
        assertTrue(inventoryItemCount(new_frame, "sceptre") == 1);
        
        new_frame = dungeon.tick(null, Direction.LEFT);
        // move to mercenary 
        for (int i = 0; i < 2; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        new_frame = dungeon.interact(getEntityId(new_frame, "mercenary"));
        assertTrue(inventoryItemCount(new_frame, "treasure") == 1);
        assertTrue(!iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        // move with ally
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);

        for (int i = 0; i < 8; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
            assertTrue(!iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        // make sure assassin does not kill us
        assertTrue(iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        new_frame = dungeon.interact(getEntityId(new_frame, "assassin"));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(13,11)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(13, 11)));
        // mind control wore off
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);

        // enemy dies
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(15, 11)));
        assertTrue(!checkEntityOnPosition(new_frame, "mercenary", new Position(15, 11)));
    }

    // test normal bribe
    @Test
    public void SunstoneBribeAssassinDurablity(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636713824112L);

        // collect materials
        new_frame = dungeon.tick(null, Direction.DOWN);
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        
        String id = getEntityId(new_frame, "assassin");
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(id);});

        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 1);
        assertTrue(inventoryItemCount(new_frame, "sun_stone") == 1);
        assertTrue(inventoryItemCount(new_frame, "treasure") == 2);

        assertTrue(iteractable(new_frame, getEntityId(new_frame, "assassin")));
        new_frame = dungeon.interact(getEntityId(new_frame, "assassin"));
        assertTrue(inventoryItemCount(new_frame, "treasure") == 2);
        assertTrue(inventoryItemCount(new_frame, "sun_stone") == 1);
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 0);
        assertTrue(!iteractable(new_frame, getEntityId(new_frame, "assassin")));

        // move to battle/vist ally
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.UP);

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
            new_frame = dungeon.tick(null, Direction.LEFT);
            assertTrue(!iteractable(new_frame, getEntityId(new_frame, "assassin")));
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        // mind control wore doesn't off
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,3)));
        assertTrue(checkEntityOnPosition(new_frame, "assassin", new Position(8,3)));

        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,4)));
        assertTrue(checkEntityOnPosition(new_frame, "assassin", new Position(8,4)));

        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(9,4)));
        assertTrue(checkEntityOnPosition(new_frame, "assassin", new Position(9,4)));
    }

    @Test
    public void SunStoneBribeMercenaryDurablity(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636713824112L);

        // collect materials
        new_frame = dungeon.tick(null, Direction.DOWN);
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
        }
        
        new_frame = dungeon.tick(null, Direction.LEFT);
        // move to mercenary 
        for (int i = 0; i < 2; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }
        assertTrue(inventoryItemCount(new_frame, "treasure") == 2);
        assertTrue(inventoryItemCount(new_frame, "sun_stone") == 1);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(inventoryItemCount(new_frame, "sun_stone") == 2);
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        new_frame = dungeon.interact(getEntityId(new_frame, "mercenary"));
        assertTrue(inventoryItemCount(new_frame, "treasure") == 2);
        assertTrue(inventoryItemCount(new_frame, "sun_stone") == 2);
        assertTrue(!iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        // move with ally
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);

        for (int i = 0; i < 8; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
            assertTrue(!iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(13,11)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(13, 11)));
        // mind control doesn't wore off
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);

        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(15, 11)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(15, 11)));
    }

    @Test
    public void TeasureBribeAssassinDurablity(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636713824112L);

        // collect materials
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.UP);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        
        String id = getEntityId(new_frame, "assassin");
        assertThrows(InvalidActionException.class, () -> {dungeon.interact(id);});

        new_frame = dungeon.tick(null, Direction.UP);
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 1);
        assertTrue(inventoryItemCount(new_frame, "treasure") == 1);

        assertTrue(iteractable(new_frame, getEntityId(new_frame, "assassin")));
        new_frame = dungeon.interact(getEntityId(new_frame, "assassin"));
        assertTrue(inventoryItemCount(new_frame, "treasure") == 0);
        assertTrue(inventoryItemCount(new_frame, "one_ring") == 0);
        assertTrue(!iteractable(new_frame, getEntityId(new_frame, "assassin")));

        // move to battle/vist ally
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
            new_frame = dungeon.tick(null, Direction.LEFT);
            assertTrue(!iteractable(new_frame, getEntityId(new_frame, "assassin")));
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        // mind control wore doesn't off
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,3)));
        assertTrue(checkEntityOnPosition(new_frame, "assassin", new Position(8,3)));

        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,4)));
        assertTrue(checkEntityOnPosition(new_frame, "assassin", new Position(8,4)));

        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(9,4)));
        assertTrue(checkEntityOnPosition(new_frame, "assassin", new Position(9,4)));
    }

    @Test
    public void TreasureBribeMercenaryDurablity(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/bribe", "Standard", 1636713824112L);

        // collect materials
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
        // move to mercenary 
        for (int i = 0; i < 2; i++){
            new_frame = dungeon.tick(null, Direction.DOWN);
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(inventoryItemCount(new_frame, "treasure") == 1);
        assertTrue(iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        new_frame = dungeon.interact(getEntityId(new_frame, "mercenary"));
        assertTrue(inventoryItemCount(new_frame, "treasure") == 0);
        assertTrue(!iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        // move with ally
        new_frame = dungeon.tick(null, Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.DOWN);

        for (int i = 0; i < 8; i++){
            new_frame = dungeon.tick(null, Direction.RIGHT);
            assertTrue(!iteractable(new_frame, getEntityId(new_frame, "mercenary")));
        }
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(13,11)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(13, 11)));
        // mind control doesn't wore off
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);

        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(15, 11)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(15, 11)));
    }

    // sun stone door
    @Test
    public void SunStoneDoor(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/sun_stone", "Peaceful", 1636713824112L);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "key") == 1);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "sun_stone") == 1);
        new_frame = dungeon.tick(null, Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(3, 3)));
        assertTrue(inventoryItemCount(new_frame, "sun_stone") == 1);
        assertTrue(inventoryItemCount(new_frame, "key") == 1);
    }
    // sun stone shield
    @Test
    public void SunStoneShield(){
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("test_maps/sun_stone", "Peaceful", 1636713824112L);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "key") == 1);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "sun_stone") == 1);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "wood") == 2);
        new_frame = dungeon.tick(null, Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "treasure") == 1);
        assertTrue(inventoryItemCount(new_frame, "shield") == 0);
        assertEquals(new_frame.getBuildables(), Arrays.asList("shield", "sceptre"));
        new_frame = dungeon.build("shield");
        assertEquals(new_frame.getBuildables(), Arrays.asList());
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);
        assertTrue(inventoryItemCount(new_frame, "treasure") == 1);
        assertTrue(inventoryItemCount(new_frame, "sun_stone") == 1);
        assertTrue(inventoryItemCount(new_frame, "wood") == 0);
    }
    // anduril test elsewhere
}
