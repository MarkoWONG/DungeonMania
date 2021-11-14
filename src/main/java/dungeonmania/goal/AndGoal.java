package dungeonmania.goal;

public class AndGoal implements Goal {

    private Goal goal1;
    private Goal goal2;

    public AndGoal (Goal goal1, Goal goal2 ){
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    @Override
    public boolean compute() {
        return goal1.compute() && goal2.compute();
    }

    @Override
    public String toString() {
        if (compute()) {
            return "";
        } else if (goal1.compute() || goal2.compute()) {
            return goal1.compute() ? goal2.toString() : goal1.toString();
        } else {
            return "( " + goal1.toString() + " AND " + goal2.toString() + " )";
        }
    }
}
