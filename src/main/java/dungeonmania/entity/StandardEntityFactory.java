package dungeonmania.entity;
import dungeonmania.EntityList;

import java.util.Random;

public class StandardEntityFactory extends EntityFactory {
    public StandardEntityFactory(EntityList entityMap, Random currRandom) {
        super(entityMap,currRandom);
    }
}
