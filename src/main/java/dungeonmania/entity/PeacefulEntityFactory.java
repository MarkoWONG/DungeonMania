package dungeonmania.entity;

import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.HashMap;

public class PeacefulEntityFactory extends EntityFactory {

    public PeacefulEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap) {
        super(entityMap);
    }
}
