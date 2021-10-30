package dungeonmania.goal;

public class TreasureGoal implements Goal{
    @Override
    public boolean compute() {
        return false;
    }

    @Override
    public String toString() {
        return compute() ? ":treasure" : "";
    }
}
