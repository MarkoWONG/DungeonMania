package dungeonmania.difficultyStates;

import dungeonmania.Dungeon;

public abstract class Difficulty {

    protected Dungeon dungref;
    protected MovementManager movementManager;
    protected InteractionManager interactionManager;
    protected FightManager fightManager;

    public Difficulty(Dungeon dungRef, MovementManager movementManager, InteractionManager interactionManager, FightManager fightManager) {
        this.dungref = dungRef;
        this.movementManager = movementManager;
        this.interactionManager = interactionManager;
        this.fightManager = fightManager;
    }

    abstract public void simulate(Direction movementDirection);

    abstract public EntityFactory createEntityFactory();
}
