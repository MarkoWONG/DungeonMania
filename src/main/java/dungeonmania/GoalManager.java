package dungeonmania;

import dungeonmania.util.FileLoader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class GoalManager {

    private ArrayList<String> goals; // figure out how to store this...

    public GoalManager(String dungeonName) {

    }

    public ArrayList<String> createGoals(String dungeonName) throws IllegalArgumentException {
        String currFileStr;
        try {
            currFileStr = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        } catch (IOException e) {d
            throw new IllegalArgumentException("Dungeon does not exist");
        }
        JSONObject currGoals = new JSONObject(currFileStr).getJSONObject("goal-condition");
        // figure out what to do from here...
    }

}
