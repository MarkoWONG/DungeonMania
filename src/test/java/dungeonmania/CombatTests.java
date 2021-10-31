package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.EntityList;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.Spider;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.mobs.Mercenary;
import dungeonmania.mobs.Mob;
import dungeonmania.entity.staticEnt.Switch;
import dungeonmania.PlayerCharacter;
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
            mercenary = new ZombieToast(position, 10, 2);
        }
        PlayerCharacter character = new PlayerCharacter(position, 20, 2);
        square.add(character);
        square.add(spider);
        square.add(zombie);
        square.add(mercenary);
        square.add(floorSwitch);

        //do the fights
        FightManager fightManager = new FightManager(square);
        fightManager.setCharacter(character);
        fightManager.doCharFights();

        assertTrue(character.getHealth() == 9);
        assertTrue(spider.getHealth() == -3 );
        assertTrue(zombie.getHealth() == 4);
        assertTrue(mercenary.getHealth() == 9);

        assertTrue(square.contains(character));
        assertTrue(square.contains(zombie));
        assertFalse(square.contains(spider));
        assertTrue(square.contains(mercenary));
        assertTrue(square.contains(floorSwitch));

        fightManager.doCharFights();
        assertTrue(character.getHealth() == 6);
        assertTrue(zombie.getHealth() == 1);
        assertTrue(mercenary.getHealth() == 10);

        assertTrue(square.contains(character));
        assertTrue(square.contains(zombie));
        assertFalse(square.contains(spider));
        assertTrue(square.contains(mercenary));
        assertTrue(square.contains(floorSwitch));
    }

    @Test
    public void testCombat_playerVsMercenary() {

    }

    @Test
    public void testCombat_playerDeath() {

    }

    @Test
    public void testCombat_eventOrder() {
    }

/*
    @Test
    public void testItemsInFights() {
        Position position = new Position(0,0,0);
        PlayerCharacter character = new PlayerCharacter(position, 20);
        
        
        Entity mercenary = new Mercenary(position);

        while (!mercenary.hasArmour()) {
            mercenary = new Mercenary(position);
        }
        
        
        CollectableEntity playerArmour = new Armour();
        CollectableEntity sword = new Sword();
        CollectableEntity bow = new Bow();

        character.addItemToInventory(playerArmour);
        character.addItemToInventory(sword);
        character.addItemToInventory(bow);

        ArrayList<Entity> square = new ArrayList<Entity>();
        square.add((Entity)character);
        square.add(mercenary);
        //do the fights
        FightManager fightManager = new FightManager();
        fightManager.doCharFights(character, square);


        assertEqual(character.getHealth(), 17);
        assertEqual(mercenary.getHealth(), 3);
    }
}
*/
}