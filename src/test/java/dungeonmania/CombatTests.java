package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import dungeonmania.EntityList;
import dungeonmania.util.Position;
import dungeonmania.entity.Entity;
import dungeonmania.mobs.Spider;
import dungeonmania.mobs.ZombieToast;
import dungeonmania.mobs.Mercenary;
import dungeonmania.entity.staticEnt.Switch;
import dungeonmania.PlayerCharacter;
import org.junit.jupiter.api.Test;

public class CombatTests {

    @Test
    public void testCombat_playerVsMob() {
        Position position = new Position(0,0,0);
        EntityList square = new EntityList();
        Entity spider = new Spider(position, 5, 6);
        Entity zombie = new ZombieToast(position, 10, 2);
        Entity floorSwitch = new Switch(position);
        Entity mercenary = new Mercenary(position, 1, square, 15, 10);
        PlayerCharacter character = new PlayerCharacter(position, square, 20, 5);
        square.add(character);
        square.add(spider);
        square.add(zombie);
        square.add(mercenary);
        square.add(floorSwitch);

        //do the fights
        FightManager fightManager = new FightManager(square);
        fightManager.doCharFights();

        /*
        assertEqual(character.getHealth(), (Integer)9);
        assertEqual(spider.getHealth(), (Integer)1);
        assertEqual(zombie.getHealth(), (Integer)7);
        assertEqual(mercenary.getHealth(), (Integer)12);
        */
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