package dungeonmania.goal;

import dungeonmania.Dungeon;
import dungeonmania.util.FileLoader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

// use the composite pattern!!!
public class GoalManager {

    private Dungeon dungRef;
    private Goal goal;

    public GoalManager(String dungeonName, Dungeon dungRef) {
        this.dungRef = dungRef;
        this.goal = createGoal(dungeonName);
    }

    private Goal createGoal(String dungeonName) throws IllegalArgumentException {
        String currFileStr;
        try {
            currFileStr = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (IOException e) {
            throw new IllegalArgumentException("Dungeon does not exist");
        }
        JSONObject currGoals = new JSONObject(currFileStr).getJSONObject("goal-condition");
        return doGoal(currGoals);
    }

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
                return new EnemiesGoal();
            case ("treasure"):
                return new TreasureGoal();
            case ("exit"):
                return new ExitGoal();
            case("boulders"):
                return new BouldersGoal();
            default:
                return null;
        }
    }

    public String getGoals() {
        return goal.toString();
    }
}
