package dungeonmania.entity.staticEnt;

import dungeonmania.entity.EntityFactory;

public class WallCreator extends EntityFactory{
    @Override
    public StaticEntity create(){
        return new Wall();
    }
}
