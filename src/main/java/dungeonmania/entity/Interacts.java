package dungeonmania.entity;

import dungeonmania.entity.collectables.*;
import dungeonmania.entity.collectables.potion.HealthPotion;
import dungeonmania.entity.collectables.potion.InvincibilityPotion;
import dungeonmania.entity.collectables.potion.InvisibilityPotion;
import dungeonmania.entity.collectables.rare.OneRing;
import dungeonmania.entity.staticEnt.*;
import dungeonmania.PlayerCharacter;
import dungeonmania.mobs.Mercenary;
import dungeonmania.mobs.Spider;
import dungeonmania.mobs.ZombieToast;

public interface Interacts {
    /**
     * Visitor pattern starter for interactions (when two entities share a tile)
     * @param entity The entity that this entity should try for an interaction with
     */
    void startInteraction(Entity entity);

    // we need to have a case for every subclass of entity... (a lot)

    void interact(Entity entity);

    void interact(PlayerCharacter player);

    // static entities
    void interact(Wall wall);
    void interact(Exit exit);
    void interact(Boulder boulder);
    void interact(Switch floorSwitch);
    void interact(Door door);
    void interact(Portal portal);
    void interact(Toaster toaster);

    // mobs
    void interact(Mercenary mercenary);
    void interact(ZombieToast zombie);
    void interact(Spider spider);

    // collectables
    void interact(Treasure treasure);
    void interact(Key key);
    void interact(HealthPotion healthPotion);
    void interact(InvincibilityPotion invincibilityPotion);
    void interact(InvisibilityPotion invisibilityPotion);
    void interact(Wood wood);
    void interact(Arrow arrow);
    void interact(Bomb bomb);
    void interact(Sword sword);
    void interact(Armour armour);
    void interact(OneRing oneRing);

    // buildables not needed, don't appear on the ground, cant collide with them.

}
