package dungeonmania;

import dungeonmania.util.Position;

public abstract class Entity {

    private Position position;


    public void interact(PlayerCharacter character) {
        return;
        // does nothing by default, we override this to do visitor pattern funky stuff
        // for example, we could do character.interact(this) here
    }

}
