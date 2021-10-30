package dungeonmania.goal;

public class ExitGoal implements Goal {
    @Override
    public boolean compute() {
        return false;
    }

    @Override
    public String toString() {
        return ":exit";
    }
}
