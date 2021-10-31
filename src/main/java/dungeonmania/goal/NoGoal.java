package dungeonmania.goal;

public class NoGoal implements Goal{

    @Override
    public boolean compute() {
        return false;
    }

    @Override
    public String toString() {
        return "";
    }
}
