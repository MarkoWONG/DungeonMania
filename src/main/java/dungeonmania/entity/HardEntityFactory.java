package dungeonmania.entity;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.collectables.potion.InvincibilityPotion;
import dungeonmania.entity.staticEnt.Toaster;
import dungeonmania.util.Position;
import java.util.ArrayList;
import java.util.HashMap;

public class HardEntityFactory extends EntityFactory{

    public HardEntityFactory(EntityList entityMap) {
        super(entityMap);
    }

    @Override
    protected Entity makeToaster(Position startPos) {
        return new Toaster(startPos, 15, entityMap);
    }

    @Override
    protected Entity makePlayer(Position startPos) {
        return new PlayerCharacter(startPos,6,2);
    }

    @Override
    protected Entity makeInvincibilityPotion(Position startPos) {
        return new InvincibilityPotion(startPos,false);
    }
}
