package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DungeonManiaController {

    private Dungeon currDungeon;
    private DungeonResponseAdapter currAdapter;

    public DungeonManiaController() {
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("Standard", "Peaceful", "Hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {
        Long random_seed = System.currentTimeMillis();
        currDungeon = new Dungeon(dungeonName,gameMode, random_seed);
        this.currAdapter = new DungeonResponseAdapter(currDungeon);
        return currAdapter.createDungResponse();
    }

    public DungeonResponse newGame(String dungeonName, String gameMode, Long seed) throws IllegalArgumentException {
        currDungeon = new Dungeon(dungeonName,gameMode, seed);
        this.currAdapter = new DungeonResponseAdapter(currDungeon);
        return currAdapter.createDungResponse();
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        try {
            Path saveGamesFolder = Paths.get("./savegames/");
            Path newitemPath = Path.of(saveGamesFolder + "/" + name + ".dungeon").toAbsolutePath().normalize();
            File newItem = new File(String.valueOf(newitemPath));
            newItem.createNewFile();
            FileWriter saveGameWriter = new FileWriter(newItem);
            String gameData = DungeonJSONAdapter.toJSON(currDungeon).toString();
            saveGameWriter.write(gameData);
            saveGameWriter.close();
        } catch (Exception e) { // we can disregard all exceptions since the two stream constructors which throw the exceptions are always provided a different name
            System.out.println(e);
            return currAdapter.createDungResponse();
        }
        return currAdapter.createDungResponse();
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        try {
            Path saveGamesFolder = Paths.get("./savegames/");
            Path saveGamePath = Path.of(saveGamesFolder + "/" + name + ".dungeon").toAbsolutePath().normalize();
            String saveGame = new String(Files.readAllBytes(saveGamePath));
            currDungeon = new Dungeon(new JSONObject(saveGame));
            currAdapter = new DungeonResponseAdapter(currDungeon);
        } catch (Exception e) {
            System.out.println(e);
            return currAdapter.createDungResponse();
        }
        return currAdapter.createDungResponse();
    }

    public List<String> allGames() {
        try {
            Path saveGamesFolder = Paths.get("./savegames/");
            File[] files = new File(String.valueOf(saveGamesFolder)).listFiles();
            ArrayList<String> output = new ArrayList<>();
            for (File eachFile : files) {
                String eachName = eachFile.getName().substring(0, eachFile.getName().lastIndexOf('.'));
                output.add(eachName);
            }
            return output;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }


    }
    // itemUsed refers to the id!!
    public DungeonResponse tick(String itemUsed, Direction movementDirection) throws IllegalArgumentException, InvalidActionException {
        currDungeon.tick(itemUsed,movementDirection);
        return currAdapter.createDungResponse();
    }

    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        currDungeon.click(entityId);
        return currAdapter.createDungResponse();
    }

    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        currDungeon.build(buildable);
        return currAdapter.createDungResponse();
    }
}