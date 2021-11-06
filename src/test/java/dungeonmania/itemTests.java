package dungeonmania;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.entity.collectables.Sword;
import dungeonmania.entity.collectables.buildable.Bow;
import dungeonmania.entity.collectables.buildable.Shield;
import dungeonmania.entity.collectables.potion.HealthPotion;
import dungeonmania.mobs.*;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

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
   public void swordDurablity(){
    
        
   }
// needs to be rewritten or replaced
//    @Test
//    public void armourStatUpdate(){
//        PlayerCharacter player = new PlayerCharacter(new Position(0,0,0));
//        assertTrue(!player.getHasArmour());
//        Armour armour = new Armour(new Position(0,0,0));
//        player.addItemToInventory(armour);
//        assertTrue(player.getHasArmour());
//    }
//
//    @Test
//    public void bowDurablity(){
//        Position position = new Position(0,0,0);
//        PlayerCharacter character = new PlayerCharacter(position);
//
//        Entity mercenary1 = new Mercenary(position);
//
//        while (!mercenary.hasArmour()) {
//            mercenary = new Mercenary(position);
//        }
//
//        Bow bow = new Bow();
//        character.addItemToInventory(bow);
//
//        ArrayList<Entity> square = new ArrayList<Entity>();
//        square.add((Entity)character);
//        square.add(mercenary);
//        //do the fights
//        FightManager fightManager = new FightManager();
//        fightManager.doCharFights(character, square);
//        assertTrue(character.getInventory().contains(bow));
//        fightManager.doCharFights(character, square);
//        assertTrue(character.getInventory().contains(bow));
//        fightManager.doCharFights(character, square);
//        assertTrue(character.getInventory().contains(bow));
//        fightManager.doCharFights(character, square);
//        assertTrue(!character.getInventory().contains(bow));
//    }
//
//    @Test
//    public void swordDurablity(){
//        Position position = new Position(0,0,0);
//        PlayerCharacter character = new PlayerCharacter(position);
//
//        Entity mercenary1 = new Mercenary(position);
//
//        while (!mercenary.hasArmour()) {
//            mercenary = new Mercenary(position);
//        }
//
//        Sword sword = new Sword(position, 2);
//        character.addItemToInventory(sword);
//
//        ArrayList<Entity> square = new ArrayList<Entity>();
//        square.add((Entity)character);
//        square.add(mercenary);
//        //do the fights
//        FightManager fightManager = new FightManager();
//        fightManager.doCharFights(character, square);
//        assertTrue(character.getInventory().contains(sword));
//        fightManager.doCharFights(character, square);
//        assertTrue(character.getInventory().contains(sword));
//        fightManager.doCharFights(character, square);
//        assertTrue(!character.getInventory().contains(sword));
//    }
//
//    @Test
//    public void shieldDurablity(){
//        Position position = new Position(0,0,0);
//        PlayerCharacter character = new PlayerCharacter(position);
//
//        Entity mercenary1 = new Mercenary(position);
//
//        while (!mercenary.hasArmour()) {
//            mercenary = new Mercenary(position);
//        }
//
//        Shield shield = new Shield(3);
//        character.addItemToInventory(shield);
//
//        ArrayList<Entity> square = new ArrayList<Entity>();
//        square.add((Entity)character);
//        square.add(mercenary);
//        //do the fights
//        FightManager fightManager = new FightManager();
//        fightManager.doCharFights(character, square);
//        assertTrue(character.getInventory().contains(shield));
//        fightManager.doCharFights(character, square);
//        assertTrue(character.getInventory().contains(shield));
//        fightManager.doCharFights(character, square);
//        assertTrue(character.getInventory().contains(shield));
//        fightManager.doCharFights(character, square);
//        assertTrue(!character.getInventory().contains(shield));
//    }
//
//    @Test
//    public void healthPotion(){
//        Position position = new Position(0,0,0);
//        PlayerCharacter character = new PlayerCharacter(position);
//        HealthPotion healthPotion = new HealthPotion(position);
//        HealthPotion healthPotion2 = new HealthPotion(position);
//        character.addItemToInventory(healthPotion);
//        character.addItemToInventory(healthPotion2);
//
//        Entity mercenary1 = new Mercenary(position);
//
//        ArrayList<Entity> square = new ArrayList<Entity>();
//        square.add((Entity)character);
//        square.add(mercenary);
//        //do the fights
//        FightManager fightManager = new FightManager();
//        Integer orginalHealth = character.getHealth();
//        fightManager.doCharFights(character, square);
//        assertTrue(orginalHealth > character.getHealth());
//        character.useItem(healthPotion.getType());
//        assertTrue(orginalHealth == character.getHealth());
//        //only on healthPotion can be used
//        character.useItem(healthPotion.getType());
//        assertTrue(character.getInventory().contains(healthPotion2));
//
//    }
   @Test
   public void StandardInvincibilityPotion(){
       DungeonManiaController dungeon = new DungeonManiaController();
       DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Standard");
       for (int i = 0; i < 3; i++){ 
           new_frame = dungeon.tick(null, Direction.RIGHT);
       }
       assertTrue(inventoryItemCount(new_frame, "invincibility_potion") == 1);
       new_frame = dungeon.tick(null, Direction.RIGHT);
       new_frame = dungeon.tick(null, Direction.RIGHT);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,1)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,3)));
       new_frame = dungeon.tick(getItemId(new_frame, "invincibility_potion"), Direction.NONE);
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,4)));
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,1)));
       new_frame = dungeon.tick(null, Direction.DOWN);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,2)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,5)));
       new_frame = dungeon.tick(null, Direction.RIGHT);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(8,2)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,6)));
       new_frame = dungeon.tick(null, Direction.LEFT);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,2)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,7)));
       new_frame = dungeon.tick(null, Direction.DOWN);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,3)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,8)));
       // potion wore out
       new_frame = dungeon.tick(null, Direction.DOWN);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,4)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,7)));
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
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,1)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,3)));
        new_frame = dungeon.tick(getItemId(new_frame, "invincibility_potion"), Direction.NONE);
        //potion no effect
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(6,2)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(7,1)));
        new_frame = dungeon.tick(null, Direction.DOWN);
        // player dies
        assertTrue(!checkEntityOnPosition(new_frame, "player", new Position(7,2)));
        assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(7,2)));
   }
   @Test
   public void invisibilityPotion(){
       DungeonManiaController dungeon = new DungeonManiaController();
       DungeonResponse new_frame = dungeon.newGame("test_maps/potion", "Standard");
        new_frame = dungeon.tick(null, Direction.RIGHT);
        new_frame = dungeon.tick(null, Direction.RIGHT);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,1)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
       new_frame = dungeon.tick(null, Direction.LEFT);
       new_frame = dungeon.tick(null, Direction.LEFT);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(2,1)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
       new_frame = dungeon.tick(null, Direction.UP);
       assertTrue(inventoryItemCount(new_frame, "invisibility_potion") == 1);
       new_frame = dungeon.tick(getItemId(new_frame, "invisibility_potion"), Direction.DOWN);
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(0,1)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-1,1)));
       assertTrue(checkEntityOnPosition(new_frame, "mercenary", new Position(3,3)));
        new_frame = dungeon.tick(null, Direction.LEFT);
        new_frame = dungeon.tick(null, Direction.LEFT);
       assertTrue(checkEntityOnPosition(new_frame, "player", new Position(-3,1)));
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
   public void oneRingPickUp(){
       DungeonManiaController dungeon = new DungeonManiaController();
       DungeonResponse new_frame = dungeon.newGame("test_maps/one_ring", "Standard");
       new_frame = dungeon.tick(null, Direction.RIGHT);
       assertTrue(inventoryItemCount(new_frame, "one_ring") == 1);
   }
}
