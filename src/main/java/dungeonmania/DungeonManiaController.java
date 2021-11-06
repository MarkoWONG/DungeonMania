package dungeonmania;

import com.google.gson.Gson;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.File;
import java.io.IOException;
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
        currDungeon = new Dungeon(dungeonName,gameMode);
        this.currAdapter = new DungeonResponseAdapter(currDungeon);
        return currAdapter.createDungResponse();
    }
    
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        Gson gson = new Gson();
        try {
            Path saveGamesFolder = Paths.get(FileLoader.class.getResource("/savegames/").toURI());
            Path newitemPath = Path.of(saveGamesFolder + "/" + name + ".dungeon");
            File newItem = new File(String.valueOf(newitemPath));
            newItem.createNewFile();
            System.out.println(gson.toJson(currDungeon));
//            FileOutputStream saveGameFileStream = new FileOutputStream(saveLocation);
//            ObjectOutputStream saveGameObjStream = new ObjectOutputStream(saveGameFileStream);
//            saveGameObjStream.writeObject(currDungeon);
//            saveGameObjStream.close();
//            saveGameFileStream.close();
        } catch (Exception e) { // we can disregard all exceptions since the two stream constructors which throw the exceptions are always provided a different name
            System.out.println(e);
            return currAdapter.createDungResponse();
        }
        return currAdapter.createDungResponse();
    }

    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        return null;
    }

    public List<String> allGames() {
        return new ArrayList<>();
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