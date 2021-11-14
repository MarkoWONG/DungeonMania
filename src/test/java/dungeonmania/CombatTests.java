package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Random;

import dungeonmania.util.Position;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Anduril;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.Sword;
import dungeonmania.entity.collectables.buildable.Bow;
import dungeonmania.mobs.Spider;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.mobs.Assassin;
import dungeonmania.mobs.Hydra;
import dungeonmania.mobs.Mercenary;
import dungeonmania.mobs.Mob;
import dungeonmania.entity.staticEnt.Switch;
import org.junit.jupiter.api.Test;

public class CombatTests {

    @Test
    public void testCombat_playerVsMob() {
        Random testingRandom = new Random(1213);
        Position position = new Position(0,0,0);
        Random rand = new Random(1);
        EntityList square = new EntityList();
        Entity spider = new Spider(position, 5, 6,new EntityList());
        Mob zombie = new ZombieToast(position, 10, 2,testingRandom);
        while (zombie.getArmour() != null) {
            zombie = new ZombieToast(position, 10, 2,testingRandom);
        }
        Entity floorSwitch = new Switch(position);
        Mob mercenary = new Mercenary(position, 1, square, 15, 4,testingRandom);
        while (mercenary.getArmour() != null) {
            mercenary = new Mercenary(position, 1, square, 15, 4,testingRandom);
        }
        PlayerCharacter character = new PlayerCharacter(position, 20, 2);


        Position otherPosition = new Position(2,2,2);
        Mob otherZombie = new ZombieToast(otherPosition, 10, 2,testingRandom);

        square.add(character);
        square.add(spider);
        square.add(zombie);
        square.add(otherZombie);
        square.add(mercenary);
        square.add(floorSwitch);

        //do the fights
        FightManager fightManager = new FightManager(square, rand);
        fightManager.setCharacter(character);
        

        assertTrue(zombie.getArmour() == null);
        assertTrue(mercenary.getArmour() == null);
        assertTrue(character.getInventory().isEmpty());

        // spider dies
        fightManager.doCharFights();
        assertEquals(9, character.getHealth());
        assertTrue(spider.getHealth() == -3 );
        assertTrue(zombie.getHealth() == 4);
        assertTrue(mercenary.getHealth() == 9);
        assertTrue(otherZombie.getHealth() == 10);
        assertTrue(square.contains(character));
        assertTrue(square.contains(zombie));
        assertFalse(square.contains(spider));
        assertTrue(square.contains(mercenary));
        assertTrue(square.contains(floorSwitch));

        for (Entity e : square) {
            e.setHasFought(false);
        }

        // nothing should die
        fightManager.doCharFights();
        assertTrue(character.getHealth() == 6);
        assertTrue(zombie.getHealth() == 1);
        assertTrue(mercenary.getHealth() == 6);
        assertTrue(otherZombie.getHealth() == 10);
        assertTrue(square.contains(character));
        assertTrue(square.contains(zombie));
        assertFalse(square.contains(spider));
        assertTrue(square.contains(mercenary));
        assertTrue(square.contains(floorSwitch));


        for (Entity e : square) {
            e.setHasFought(false);
        }
        // zombie dies
        fightManager.doCharFights();
        assertTrue(character.getHealth() == 4);
        assertTrue(zombie.getHealth() == -1);
        assertTrue(mercenary.getHealth() == 4);
        assertTrue(otherZombie.getHealth() == 10);
        assertTrue(square.contains(character));
        assertFalse(square.contains(zombie));
        assertFalse(square.contains(spider));
        assertTrue(square.contains(mercenary));
        assertTrue(square.contains(floorSwitch));

        for (Entity e : square) {
            e.setHasFought(false);
        }
        Mob lastZombie = new ZombieToast(position, 10, 4, testingRandom);
        square.add(lastZombie);

        // player dies on this tick
        fightManager.doCharFights();
        assertEquals(-1, character.getHealth());
        assertTrue(mercenary.getHealth() == 3);
        assertFalse(square.contains(character));
        assertFalse(square.contains(zombie));
        assertFalse(square.contains(spider));
        assertTrue(square.contains(mercenary));
        assertTrue(square.contains(floorSwitch));
        assertTrue(character.getInventory().isEmpty());
    }

    @Test
    public void testCombat_armour() {
        Random testingRandom = new Random(1213);
        Position position = new Position(0,0,0);
        EntityList square = new EntityList();
        Random rand = new Random(1);
        Mob zombie = new ZombieToast(position, 10, 2, testingRandom);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 10, 2,testingRandom);
        }
        Mob mercenary = new Mercenary(position, 1, square, 15, 4,testingRandom);
        while (mercenary.getArmour() == null) {
            mercenary = new Mercenary(position, 1, square, 15, 4,testingRandom);
        }

        PlayerCharacter character = new PlayerCharacter(position, 20, 1);
        Armour armoura = new Armour();
        Armour armourb = new Armour();
        ArrayList<CollectableEntity> inventory = new ArrayList<CollectableEntity>();
        inventory.add(armoura);
        inventory.add(armourb);
        character.replaceInventory(inventory);

        square.add(character);
        square.add(zombie);
        square.add(mercenary);
        assertTrue(zombie.getArmour().getDurability() == 3);
        assertTrue(mercenary.getArmour().getDurability() == 3);

        FightManager fightManager = new FightManager(square, rand);
        fightManager.setCharacter(character);
        fightManager.doCharFights();
        
        assertTrue(character.getHealth() == 16);
        assertTrue(zombie.getHealth() == 8);
        assertEquals(2, zombie.getArmour().getDurability());
        assertTrue(mercenary.getHealth() == 14);
        assertTrue(mercenary.getArmour().getDurability() == 2);
    }

    @Test
    public void testCombat_sword_and_bow() {
        Position position = new Position(0,0,0);
        Random rand = new Random(1);
        EntityList square = new EntityList();
        Mob zombie = new ZombieToast(position, 10, 2, rand);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 10, 2, rand);
        }

        PlayerCharacter character = new PlayerCharacter(position, 20, 1);
        Sword sworda = new Sword(new Position(0, 0));
        Sword swordb = new Sword(new Position(0, 0));
        character.addItemToInventory(sworda);
        character.addItemToInventory(swordb);

        square.add(character);
        square.add(zombie);

        assertEquals(zombie.getArmour().getDurability(), 3);
        assertEquals(3, sworda.getDurability());
        assertEquals(3, swordb.getDurability());
        FightManager fightManager = new FightManager(square, rand);

        fightManager.setCharacter(character);
        fightManager.doCharFights();
        
        assertTrue(character.getHealth() == 18);
        assertEquals(4, zombie.getHealth());
        assertEquals(2, sworda.getDurability());
        assertEquals(3, swordb.getDurability());

        for (Entity e : square) {
            e.setHasFought(false);
        }

        fightManager.doCharFights();

        assertTrue(character.getHealth() == 18);
        assertEquals(-1, zombie.getHealth());
        assertEquals(1, sworda.getDurability());
        assertEquals(3, swordb.getDurability());

        square.remove(zombie);

        zombie = new ZombieToast(position, 10, 2, rand);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 10, 2, rand);
        }

        for (Entity e : square) {
            e.setHasFought(false);
        }
        square.add(zombie);

        Bow bow = new Bow();

        character.addItemToInventory(bow);

        fightManager.doCharFights();

        assertEquals(character.getHealth(), 17);
        assertEquals(0, zombie.getHealth());
        assertEquals(0, sworda.getDurability());
        assertEquals(3, swordb.getDurability());
        assertEquals(2, bow.getDurability());

        square.remove(zombie);

        zombie = new ZombieToast(position, 10, 2, rand);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 10, 2, rand);
        }

        for (Entity e : square) {
            e.setHasFought(false);
        }
        square.add(zombie);

        fightManager.doCharFights();

        assertTrue(character.getHealth() == 16);
        assertEquals(0, zombie.getHealth());
        assertEquals(0, sworda.getDurability());
        assertEquals(2, swordb.getDurability());
        assertEquals(1, bow.getDurability());

        square.remove(zombie);

        zombie = new ZombieToast(position, 10, 2, rand);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 10, 2, rand);
        }

        for (Entity e : square) {
            e.setHasFought(false);
        }
        square.add(zombie);

        fightManager.doCharFights();

        assertEquals(character.getHealth(), 15);
        assertEquals(1, zombie.getHealth());
        assertEquals(0, sworda.getDurability());
        assertEquals(1, swordb.getDurability());
        assertEquals(0, bow.getDurability());

        square.remove(zombie);

        zombie = new ZombieToast(position, 10, 2, rand);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 10, 2, rand);
        }

        for (Entity e : square) {
            e.setHasFought(false);
        }
        square.add(zombie);

        fightManager.doCharFights();
        assertEquals(14, character.getHealth());
        assertEquals(6, zombie.getHealth());
        assertEquals(0, sworda.getDurability());
        assertEquals(0, swordb.getDurability());
        assertEquals(0, bow.getDurability());

        assertEquals(1, character.getInventory().size());
    }

    @Test
    public void testCombat_anduril() {
        Position position = new Position(0,0,0);
        EntityList square = new EntityList();
        Random rand = new Random(1);
        Mob zombie = new ZombieToast(position, 10, 2, rand);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 10, 2, rand);
        }

        PlayerCharacter character = new PlayerCharacter(position, 20, 1);
        Sword sword = new Sword(new Position(0, 0));
        Anduril anduril = new Anduril(new Position(0, 0));
        Bow bow = new Bow();

        character.addItemToInventory(bow);
        character.addItemToInventory(sword);
        character.addItemToInventory(anduril);

        square.add(character);
        square.add(zombie);

        assertEquals(zombie.getArmour().getDurability(), 3);
        assertEquals(3, sword.getDurability());
        assertEquals(3, anduril.getDurability());
        FightManager fightManager = new FightManager(square, rand);

        fightManager.setCharacter(character);
        fightManager.doCharFights();
        
        assertTrue(character.getHealth() == 18);
        assertEquals(-10, zombie.getHealth());
        assertEquals(2, anduril.getDurability());
        assertEquals(2, bow.getDurability());
        assertEquals(3, sword.getDurability());
    }

    @Test
    public void testCombat_hydra() { 
        Position position = new Position(0,0,0);
        Random random = new Random(5);
        EntityList square = new EntityList();
        Mob hydra = new Hydra(position, 50, 2, random);

        PlayerCharacter character = new PlayerCharacter(position, 20, 1);
        Sword sword = new Sword(new Position(0, 0));
        Anduril anduril = new Anduril(new Position(0, 0));
        Bow bow = new Bow();

        character.addItemToInventory(bow);
        character.addItemToInventory(sword);
        

        square.add(character);
        square.add(hydra);

        assertEquals(3, sword.getDurability());
        assertEquals(3, anduril.getDurability());
        FightManager fightManager = new FightManager(square, random);

        fightManager.setCharacter(character);
        fightManager.doCharFights();
        
        assertEquals(10, character.getHealth());
        assertEquals(74, hydra.getHealth());
        assertEquals(3, anduril.getDurability());
        assertEquals(2, bow.getDurability());
        assertEquals(2, sword.getDurability());

        character.addItemToInventory(anduril);

        Armour armour = new Armour();
        character.addItemToInventory(armour);

        for (Entity e : square) {
            e.setHasFought(false);
        }

        character.setHealth(20);
        fightManager.doCharFights();
        
        assertEquals(13, character.getHealth());
        assertEquals(34, hydra.getHealth());
        assertEquals(2, anduril.getDurability());
        assertEquals(1, bow.getDurability());
        assertEquals(2, sword.getDurability());

        character.removeItemFromInventory(anduril);

        for (Entity e : square) {
            e.setHasFought(false);
        }

        character.setHealth(20);
        fightManager.doCharFights();
        
        assertEquals(17, character.getHealth());
        assertEquals(10, hydra.getHealth());
        assertEquals(2, anduril.getDurability());
        assertEquals(0, bow.getDurability());
        assertEquals(1, sword.getDurability());
    }

    @Test
    public void testCombat_drops() {
        Position position = new Position(0,0,0);
        EntityList square = new EntityList();
        Random rand = new Random(3);


        Mob zombie = new ZombieToast(position, 1, 2, rand);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 1, 2, rand);
        }
        assertEquals(3, zombie.getArmour().getDurability());
        PlayerCharacter character = new PlayerCharacter(position, 20, 2);

        square.add(character);
        square.add(zombie);

        FightManager fightManager = new FightManager(square, rand);
        fightManager.setCharacter(character);
        fightManager.doCharFights();

        assertEquals(-3, zombie.getHealth());
        assertEquals(1, square.size());
        assertEquals(2, character.getInventory().size());
        assertEquals("armour", character.getInventory().get(0).getType());
        assertEquals("one_ring", character.getInventory().get(1).getType());

        zombie = new ZombieToast(position, 1, 2, rand);
        while (zombie.getArmour() != null) {
            zombie = new ZombieToast(position, 1, 2, rand);
        }
        square.add(zombie);

        character.replaceInventory(new ArrayList<CollectableEntity>());
        fightManager.doCharFights();
        assertEquals(-7, zombie.getHealth());
        assertEquals(1, square.size());
        assertEquals(1, character.getInventory().size());
        assertEquals("anduril", character.getInventory().get(0).getType());
    }

    @Test
    public void testCombat_allies() {
        Random rand = new Random(1);
        Position position = new Position(0,0,0);
        Position position2 = new Position(1,1,1);
        EntityList square = new EntityList();


        Mob zombie = new ZombieToast(position, 80, 2, rand);
        while (zombie.getArmour() != null) {
            zombie = new ZombieToast(position, 80, 2, rand);
        }
        PlayerCharacter character = new PlayerCharacter(position, 20, 2);

        square.add(character);
        square.add(zombie);
        Mercenary mercenary = new Mercenary(position2, 1, square, 15, 4, rand);
        mercenary.bribe(1);

        Assassin assassin = new Assassin(position2, 1, square, 15, 4, rand);
        assassin.bribe(1);
        assertEquals(mercenary, character.getAllies().get(0));
        assertEquals(assassin, character.getAllies().get(1));

        FightManager fightManager = new FightManager(square);
        fightManager.setCharacter(character);
        fightManager.doCharFights();

        assertEquals(4, character.getHealth());
        assertEquals(56, zombie.getHealth());

        character.removeAlly(assassin);
        character.setHealth(20);

        for (Entity e : square) {
            e.setHasFought(false);
        }

        fightManager.doCharFights();
        
        assertEquals(9, character.getHealth());
        assertEquals(40, zombie.getHealth());
    }

    @Test
    public void testCombat_invincibility() {
        Position position = new Position(0,0,0);
        EntityList square = new EntityList();
        Random rand = new Random(1);


        Mob zombie = new ZombieToast(position, 10, 2, rand);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 10, 2, rand);
        }
        assertEquals(3, zombie.getArmour().getDurability());
        PlayerCharacter character = new PlayerCharacter(position, 20, 2);

        square.add(character);
        square.add(zombie);

        FightManager fightManager = new FightManager(square, rand);
        fightManager.setCharacter(character);
        
        character.setInvincibleTicks(1);
        fightManager.doCharFights();

        assertEquals(6, zombie.getHealth());
        assertEquals(20, character.getHealth());

        for (Entity e : square) {
            e.setHasFought(false);
        }
        character.incrementTick();
        fightManager.doCharFights();

        
        assertEquals(2, zombie.getHealth());
        assertEquals(19, character.getHealth());

        character.setInvisibleTicks(1);
        for (Entity e : square) {
            e.setHasFought(false);
        }
        fightManager.doCharFights();

        
        assertEquals(2, zombie.getHealth());
        assertEquals(19, character.getHealth());

        for (Entity e : square) {
            e.setHasFought(false);
        }
        character.incrementTick();
        fightManager.doCharFights();

        
        assertEquals(-1, zombie.getHealth());
        assertEquals(19, character.getHealth());
    }

}

