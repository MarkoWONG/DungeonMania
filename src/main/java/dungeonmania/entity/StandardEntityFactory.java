package dungeonmania.entity;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;

public class StandardEntityFactory extends EntityFactory {
    public StandardEntityFactory(HashMap<Position, ArrayList<Entity>> entityMap) {
        super(entityMap);
    }
}
