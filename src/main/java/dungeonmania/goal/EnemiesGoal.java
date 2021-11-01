package dungeonmania.goal;

public class EnemiesGoal implements Goal {
    @Override
    public boolean compute() {
        return false;
    }

    @Override
    public String toString() {
        // mercenary represents all entities
        return compute() ? "" : ":mercenary";
    }
}
