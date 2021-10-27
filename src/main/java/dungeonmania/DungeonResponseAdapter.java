package dungeonmania;

import dungeonmania.response.models.DungeonResponse;

// Very loosely follows the Adapter Pattern
public class DungeonResponseAdapter {

    private Dungeon dungRef;

    static DungeonResponse createDungResponse(Dungeon dungeon) {
        return new DungeonResponse();
    }
}
