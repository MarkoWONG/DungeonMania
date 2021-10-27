package dungeonmania.entity;

import dungeonmania.PlayerCharacter;
import dungeonmania.util.Position;

public abstract class Entity implements Interacts, Fights {

    private Position position;

    // none of these do anything by default you need to override them in the specific class to implement the behaviour
    // startFight and startInteraction just call .fight(this) when overridden

    @Override
    public void startFight(PlayerCharacter playerCharacter) {
        // playerCharacter.fight(this); example override for playerCharacter
    }

    @Override
    public void fight(Mob mob) {

    }

    @Override
    public void startInteraction(Entity entity) {

    }

    @Override
    public void interact(Entity entity) {

    }
}
