package dungeonmania.entity;

import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.collectables.potion.InvincibilityPotion;
import dungeonmania.entity.staticEnt.Toaster;
import dungeonmania.mobs.Hydra;
import dungeonmania.util.Position;

import java.util.Random;

public class HardEntityFactory extends EntityFactory{

    public HardEntityFactory(EntityList entityMap, Random currRandom) {
        super(entityMap, currRandom);
    }

    @Override
    protected Entity makeToaster(Position startPos) {
        return new Toaster(startPos, 15, entityMap,currRandom);
    }

    @Override
    protected Entity makePlayer(Position startPos) {
        return new PlayerCharacter(startPos,6,2);
    }

    @Override
    protected Entity makeInvincibilityPotion(Position startPos) {
        if (startPos == null) {
            return new InvincibilityPotion(false);
        }
        return new InvincibilityPotion(startPos,false);
    }
}
