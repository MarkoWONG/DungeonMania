package dungeonmania.goal;

import dungeonmania.Dungeon;
import dungeonmania.EntityList;
import dungeonmania.util.FileLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;

// use the composite pattern!!!
public class GoalManager {

    private Dungeon dungRef;
    private Goal goal;

    public GoalManager(String dungeonName, Dungeon dungRef) {
        this.dungRef = dungRef;
        this.goal = createGoal(dungeonName);
    }

    /**
     * Create the goal object for the given dungeon from JSON
     * @param dungeonName The name of the dungeon to be loaded from file
     * @return The goal object for the given dungeon
     * @throws IllegalArgumentException Where the dungeon map cannot be found
     */
    private Goal createGoal(String dungeonName) throws IllegalArgumentException {
        String currFileStr;
        JSONObject currGoals;
        try {
            currFileStr = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (IOException | NullPointerException e) {
            throw new IllegalArgumentException("Dungeon does not exist");
        }
        try {
            currGoals = new JSONObject(currFileStr).getJSONObject("goal-condition");
        } catch(Exception e) {
            return new NoGoal();
        }
        return doGoal(currGoals);
    }

    /**
     * Given a goal, recursively create a goal object
     * @param topLevelGoal the current goal level that has subgoals
     * @return Part of or a complete goal object
     */
    private Goal doGoal(JSONObject topLevelGoal) {
        String goalName = topLevelGoal.getString("goal");
        JSONArray currSubGoals;
        Goal arg1;
        Goal arg2;
        switch (goalName) {
            case ("AND"):
                currSubGoals = new JSONArray(topLevelGoal.getJSONArray("subgoals"));
                arg1 = doGoal(currSubGoals.getJSONObject(0));
                arg2 = doGoal(currSubGoals.getJSONObject(1));
                return new AndGoal(arg1,arg2);
            case ("OR"):
                currSubGoals = new JSONArray(topLevelGoal.getJSONArray("subgoals"));
                arg1 = doGoal(currSubGoals.getJSONObject(0));
                arg2 = doGoal(currSubGoals.getJSONObject(1));
                return new OrGoal(arg1, arg2);
            case ("enemies"):
                return new EnemiesGoal((EntityList) dungRef.getEntities());
            case ("treasure"):
                return new TreasureGoal((EntityList) dungRef.getEntities());
            case ("exit"):
                return new ExitGoal((EntityList) dungRef.getEntities());
            case("boulders"):
                return new BouldersGoal((EntityList) dungRef.getEntities());
            default:
                return null;
        }
    }

    /**
     * @return the readable goal string of the current goal object
     */
    public String getGoals() {
        return goal.toString();
    }
}
