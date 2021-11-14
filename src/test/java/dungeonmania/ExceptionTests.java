package dungeonmania;

import dungeonmania.response.models.DungeonResponse;
import org.junit.jupiter.api.Test;

public class ExceptionTests {

    @Test
    public void testUseItemExceptions() {
        DungeonManiaController currController = new DungeonManiaController();
        DungeonResponse currResponse = currController.newGame("dungeon_collectable", "Standard",1L);
    }
}
