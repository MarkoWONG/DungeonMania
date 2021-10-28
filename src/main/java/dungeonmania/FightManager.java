package dungeonmania;


public class FightManager {

    public FightManager(){}

    public void doCharFights(PlayerCharacter character, ArrayList<Entity> entities) {
        //for each entity
            // entity.startFight(character, entities)

        // cleanUp(entities)


        
    }

    private void cleanUp (PlayerCharacter character, ArrayList<Entity> entities) {
        // for each entity
            // if entity.getHealth <= 0
                // if entity.canRevive == false
                    // entities.remove(entity)
                // else
                    // entity.revive
    }
}