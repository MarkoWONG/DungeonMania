package dungeonmania.entity;

import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Mob.*;
public interface Fights {
    // iterate through the entitieslist, if its a mob (use instanceof) do mob.startFight(character)
    // this mean we're using instanceof for Fight but not for interacts
    // this lets us not have to have a case for every subclass of Entity, ( we have to do that for Interacts )

    void startFight(PlayerCharacter playerCharacter);
    void fight(Mob mob);
}
