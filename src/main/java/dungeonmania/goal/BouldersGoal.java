package dungeonmania.goal;

public class BouldersGoal implements Goal{
    @Override
    public boolean compute() {
        return false;
    }

    @Override
    public String toString() {
        return ":boulder";
    }
}
