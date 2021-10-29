package dungeonmania;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.entity.collectables.Sword;
import dungeonmania.entity.collectables.buildable.Shield;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
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

    // public PlayerCharacter findPlayer(DungeonResponse frame){
    //     for (EntityResponse ent : frame.getEntities()){
    //         if (ent.getType().equals("player")){
    //             PlayerCharacter player = (PlayerCharacter) ent;
    //             return ent;
    //         }
    //     }
    // }

    @Test
    public void singleCollectionTest(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced", "standard");

        //move to collect sword
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
            assertTrue(inventoryItemCount(new_frame, "sword") == 0);
        }
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);
    }

    @Test
    public void muiltpleCollectionTest(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced", "standard");

        //move to collect sword
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
            assertTrue(new_frame.getInventory().size() == 0);
        }
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 1);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);

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
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);
        assertTrue(inventoryItemCount(new_frame, "key") == 1);

        // move to collect wood 
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 3);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);
        assertTrue(inventoryItemCount(new_frame, "key") == 1);
        assertTrue(inventoryItemCount(new_frame, "wood") == 1);
    }

    @Test
    public void duplicateItemCollectionTest(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced", "standard");

        //move to collect sword
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
            assertTrue(new_frame.getInventory().size() == 0);
        }
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 1);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);

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
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);
        assertTrue(inventoryItemCount(new_frame, "key") == 1);

        // move to collect wood 
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(new_frame.getInventory().size() == 4);
        assertTrue(inventoryItemCount(new_frame, "wood") == 2);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 8);
        assertTrue(inventoryItemCount(new_frame, "wood") == 3);
        assertTrue(inventoryItemCount(new_frame, "arrow") == 3);
    }

    // stat change when picked up a sword or armour
    @Test
    public void swordStatChange(){
        PlayerCharacter player = new PlayerCharacter(new Position(0,0,0));
        assertTrue(player.getAttackDamage() == 1);
        Sword sword = new Sword(new Position(0,0,0), 3);
        player.addItemToInventory(sword);
        assertTrue(player.getAttackDamage() == 3);
    }

    @Test
    public void armourStatUpdate(){
        PlayerCharacter player = new PlayerCharacter(new Position(0,0,0));
        assertTrue(!player.getHasArmour());
        Armour armour = new Armour(new Position(0,0,0));
        player.addItemToInventory(armour);
        assertTrue(player.getHasArmour());
    }

    // durablity of bow and shield
    @Test
    public void bowDurablity(){
        Position position = new Position(0,0,0);
        PlayerCharacter character = new PlayerCharacter(position);
        
        Entity mercenary1 = new Mercenary(position);

        while (!mercenary.hasArmour()) {
            mercenary = new Mercenary(position);
        }
        
        Bow bow = new Bow();
        character.addItemToInventory(bow);

        ArrayList<Entity> square = new ArrayList<Entity>();
        square.add((Entity)character);
        square.add(mercenary);
        //do the fights
        FightManager fightManager = new FightManager();
        fightManager.doCharFights(character, square);
        assertTrue(character.getInventory().contains(bow));
        fightManager.doCharFights(character, square);
        assertTrue(character.getInventory().contains(bow));
        fightManager.doCharFights(character, square);
        assertTrue(character.getInventory().contains(bow));
        fightManager.doCharFights(character, square);
        assertTrue(!character.getInventory().contains(bow));
    }

    @Test
    public void swordDurablity(){
        Position position = new Position(0,0,0);
        PlayerCharacter character = new PlayerCharacter(position);
        
        Entity mercenary1 = new Mercenary(position);

        while (!mercenary.hasArmour()) {
            mercenary = new Mercenary(position);
        }
        
        Sword sword = new Sword(position, 2);
        character.addItemToInventory(sword);

        ArrayList<Entity> square = new ArrayList<Entity>();
        square.add((Entity)character);
        square.add(mercenary);
        //do the fights
        FightManager fightManager = new FightManager();
        fightManager.doCharFights(character, square);
        assertTrue(character.getInventory().contains(sword));
        fightManager.doCharFights(character, square);
        assertTrue(character.getInventory().contains(sword));
        fightManager.doCharFights(character, square);
        assertTrue(!character.getInventory().contains(sword));
    }
    
    @Test
    public void shieldDurablity(){
        Position position = new Position(0,0,0);
        PlayerCharacter character = new PlayerCharacter(position);
        
        Entity mercenary1 = new Mercenary(position);

        while (!mercenary.hasArmour()) {
            mercenary = new Mercenary(position);
        }
        
        Shield shield = new Shield(position, 3);
        character.addItemToInventory(shield);

        ArrayList<Entity> square = new ArrayList<Entity>();
        square.add((Entity)character);
        square.add(mercenary);
        //do the fights
        FightManager fightManager = new FightManager();
        fightManager.doCharFights(character, square);
        assertTrue(character.getInventory().contains(shield));
        fightManager.doCharFights(character, square);
        assertTrue(character.getInventory().contains(shield));
        fightManager.doCharFights(character, square);
        assertTrue(character.getInventory().contains(shield));
        fightManager.doCharFights(character, square);
        assertTrue(!character.getInventory().contains(shield));
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
        DungeonResponse new_frame = dungeon.newGame("bombs", "standard");
        
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(inventoryItemCount(new_frame, "bomb") == 1);
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("1", Direction.LEFT);
        assertTrue(checkEntityOnPosition(new_frame, "bomb", new Position(4,2)));
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
        assertTrue(!checkEntityOnPosition(new_frame, "switch", new Position(5,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "boulder", new Position(5,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,2)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,1)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,3)));
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
        assertTrue(!checkEntityOnPosition(new_frame, "switch", new Position(5,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "boulder", new Position(5,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,3)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,2)));
        assertTrue(!checkEntityOnPosition(new_frame, "wall", new Position(6,1)));
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,2)));
    }

    // crafting
    @Test
    public void craftingOneAtATime(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("crafting", "standard");

        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
        }
        assertTrue(inventoryItemCount(new_frame, "bow") == 0);
        new_frame = dungeon.build("bow");
        assertTrue(inventoryItemCount(new_frame, "bow") == 1);

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick("none", Direction.DOWN);
        }
        assertTrue(inventoryItemCount(new_frame, "shield") == 0);
        new_frame = dungeon.build("shield");
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);

        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick("none", Direction.LEFT);
        }
        assertTrue(inventoryItemCount(new_frame, "shield") == 1);
        new_frame = dungeon.build("shield");
        assertTrue(inventoryItemCount(new_frame, "shield") == 2);
    }
    @Test
    public void craftingAllAtATime(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("crafting", "standard");
        for (int i = 0; i < 4; i++){
            new_frame = dungeon.tick("none", Direction.RIGHT);
        }
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick("none", Direction.DOWN);
        }
        for (int i = 0; i < 3; i++){
            new_frame = dungeon.tick("none", Direction.LEFT);
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

    //only one key at a time
    public void onlyOneKeyTest(){
        // Create new dungeon
        DungeonManiaController dungeon = new DungeonManiaController();
        DungeonResponse new_frame = dungeon.newGame("advanced", "standard");

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
        assertTrue(inventoryItemCount(new_frame, "key") == 1);

        // only one key
        new_frame = dungeon.tick("none", Direction.RIGHT);
        assertTrue(new_frame.getInventory().size() == 2);
        assertTrue(inventoryItemCount(new_frame, "key") == 1);
        assertTrue(inventoryItemCount(new_frame, "sword") == 1);
    }

    // one key at a time (use a key (craft / open door) then pick up another key
    @Test
    public void usingKey(){
        // Create new dungeon
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

        // collect key and 2 wood and build a shield
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.tick("none", Direction.DOWN);
        new_frame = dungeon.build("shield");

        // no key = locked door
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(4,4)));
        
        // collect key and open door
        new_frame = dungeon.tick("none", Direction.RIGHT);
        new_frame = dungeon.tick("none", Direction.DOWN);
        assertTrue(checkEntityOnPosition(new_frame, "player", new Position(5,5)));
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
