package dungeonmania;


public class FightManager {

    public FightManager(){}

    public void doCharFights(PlayerCharacter character, ArrayList<Entity> entities) {
        // does all fights, if somethings health <= 0 then it is removed in cleanup
        for (Entity e : entities) {
            entity.startFight(character, e);
        }
        
        cleanUp(entities);
        
    }

    private void cleanUp (PlayerCharacter character, ArrayList<Entity> entities) {
        for (Entity e : entities) {
            if (e.getHealth <= 0) {
                if (entity.canRevive == false) {
                     entities.remove(e);
                } else {
                     e.revive();
                }
            }
        }
    }
}