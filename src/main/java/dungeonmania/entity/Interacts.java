package dungeonmania.entity;

import dungeonmania.PlayerCharacter;
import dungeonmania.entity.Entity;

public interface Interacts {
    // In InteractionManager: for each entity in the entities map, do currEntity.doInteraction(currEntity2), for each entity on the same square
    // currEntity.doInteraction(currEntity2) will then call currEntity2.interact(this) to get the right type without typecasting. (HOPEFULLY?!)
    void startInteraction(Entity entity);

    // we need to have a case for every subclass of entity... (a lot)

    void interact(Entity entity);

    void interact(PlayerCharacter player);

    // static entities
    void interact(Wall wall);
    void interact(Exit wall);
    void interact(Boulder boulder);
    void interact(floorSwitch floorSwitch);
    void interact(Door door);
    void interact(Portal portal);
    void interact(Toaster toaster);

    // mobs
    void interact(Mercenary mercenary);
    void interact(Zombie zombie);
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
