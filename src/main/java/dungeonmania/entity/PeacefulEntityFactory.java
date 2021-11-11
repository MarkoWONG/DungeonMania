package dungeonmania.entity;

import dungeonmania.EntityList;

import java.util.Random;

public class PeacefulEntityFactory extends EntityFactory{
    public PeacefulEntityFactory(EntityList entityMap, Random currRandom) {
        super(entityMap, currRandom);
    }
}
