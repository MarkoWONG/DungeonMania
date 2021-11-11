package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import dungeonmania.util.Position;
import dungeonmania.entity.Entity;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.mobs.Spider;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.mobs.Mercenary;
import dungeonmania.mobs.Mob;
import dungeonmania.entity.staticEnt.Switch;
import org.junit.jupiter.api.Test;

public class CombatTests {

    @Test
    public void testCombat_playerVsMob() {
        Position position = new Position(0,0,0);
        EntityList square = new EntityList();
        Entity spider = new Spider(position, 5, 6);
        Mob zombie = new ZombieToast(position, 10, 2);
        while (zombie.getArmour() != null) {
            zombie = new ZombieToast(position, 10, 2);
        }
        Entity floorSwitch = new Switch(position);
        Mob mercenary = new Mercenary(position, 1, square, 15, 4);
        while (mercenary.getArmour() != null) {
            mercenary = new Mercenary(position, 1, square, 15, 4);
        }
        PlayerCharacter character = new PlayerCharacter(position, 20, 2);


        Position otherPosition = new Position(2,2,2);
        Mob otherZombie = new ZombieToast(otherPosition, 10, 2);

        square.add(character);
        square.add(spider);
        square.add(zombie);
        square.add(otherZombie);
        square.add(mercenary);
        square.add(floorSwitch);

        //do the fights
        FightManager fightManager = new FightManager(square);
        fightManager.setCharacter(character);
        

        assertTrue(zombie.getArmour() == null);
        assertTrue(mercenary.getArmour() == null);
        assertTrue(character.getInventory().isEmpty());

        // spider dies
        fightManager.doCharFights();
        assertTrue(character.getHealth() == 9);
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
        Mob lastZombie = new ZombieToast(position, 10, 4);
        square.add(lastZombie);

        // player dies on this tick
        fightManager.doCharFights();
        assertTrue(character.getHealth() == -1);
        assertTrue(mercenary.getHealth() == 3);
        assertFalse(square.contains(character));
        assertFalse(square.contains(zombie));
        assertFalse(square.contains(spider));
        assertTrue(square.contains(mercenary));
        assertTrue(square.contains(floorSwitch));
    }

    @Test
    public void testCombat_armour() {
        Position position = new Position(0,0,0);
        EntityList square = new EntityList();
        Mob zombie = new ZombieToast(position, 10, 2);
        while (zombie.getArmour() == null) {
            zombie = new ZombieToast(position, 10, 2);
        }
        Mob mercenary = new Mercenary(position, 1, square, 15, 4);
        while (mercenary.getArmour() == null) {
            mercenary = new Mercenary(position, 1, square, 15, 4);
        }

        PlayerCharacter character = new PlayerCharacter(position, 20, 1);
        Armour armoura = new Armour();
        Armour armourb = new Armour();
        ArrayList<CollectableEntity> inventory = new ArrayList<CollectableEntity>();
        inventory.add(armoura);
        inventory.add(armourb);
        character.setInventory(inventory);

        square.add(character);
        square.add(zombie);
        square.add(mercenary);
        assertTrue(zombie.getArmour().getDurability() == 6);
        assertTrue(mercenary.getArmour().getDurability() == 6);

        FightManager fightManager = new FightManager(square);
        fightManager.setCharacter(character);
        fightManager.doCharFights();
        
        assertTrue(character.getHealth() == 16);
        assertTrue(zombie.getHealth() == 8);
        assertTrue(zombie.getArmour().getDurability() == 5);
        assertTrue(mercenary.getHealth() == 14);
        assertTrue(mercenary.getArmour().getDurability() == 5);
    }

}