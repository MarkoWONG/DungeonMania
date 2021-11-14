package dungeonmania.goal;

public interface Goal {
    /**
     * Compute whether the goal has been completed
     * @return The goal's completion status
     */
    boolean compute();

    /**
     * @return A readable string, empty string for parts that have been completed, or if the whole goal has been completed
     */
    String toString();
}
