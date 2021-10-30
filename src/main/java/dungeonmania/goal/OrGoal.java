package dungeonmania.goal;

public class OrGoal implements Goal {

    private Goal goal1;
    private Goal goal2;

     public OrGoal(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    @Override
    public boolean compute() {
        return goal1.compute() || goal2.compute();
    }

    @Override
    public String toString() {
        return compute() ? "" : "( " + goal1.toString() + " OR " + goal2.toString() + " )";
    }
}
