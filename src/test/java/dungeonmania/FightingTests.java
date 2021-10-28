package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.text.html.parser.Entity;

import static org.junit.jupiter.api.Assertions.assertEqual;

public class DungeonTests {
    @Test
    public void testPlayerFightsAllMobs() {
        Position position = new Position(0,0,0);
        PlayerCharacter character = new PlayerCharacter(position, 20);
        Entity spider = new Spider(position);
        Entity zombie = new Zombie(position);
        Entity mercenary = new Mercenary(position);
        Entity floorSwitch = new FloorSwitch(position);
        ArrayList<Entity> square = new ArrayList<Entity>();
        square.add((Entity)character);
        square.add(Spider);
        square.add(zombie);
        square.add(mercenary);

        


        //do the fights
        FightManager fightManager = new FightManager();
        fightManager.doCharFights(character, square);


        assertEqual(character.getHealth(), 9);
        assertEqual(spider.getHealth(), 1);
        assertEqual(zombie.getHealth(), 7);
        assertEqual(mercenary.getHealth(), 12);
    }

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
