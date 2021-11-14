package dungeonmania;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dungeonmania.entity.Entity;
import dungeonmania.movement.MovementManager;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class EntityList extends ArrayList<Entity> {

    public ArrayList<Entity> search(Position position) {
        ArrayList<Entity> output = new ArrayList<>();
        int givenX = position.getX();
        int givenY = position.getY();
        for (Entity eachEntity : this) {
            Position currEntPosition = eachEntity.getPosition();
            if (currEntPosition == null) {
                continue;
            }
            int currX = currEntPosition.getX();
            int currY = currEntPosition.getY();
            if (givenX == currX && givenY == currY) {
                output.add(eachEntity);
            }
        }
        return output;
    }

    public ArrayList<Entity> search(String entityType) {
        ArrayList<Entity> output = new ArrayList<>();
        for (Entity eachEntity : this) {
            if (eachEntity.getType().equals(entityType)) {
                output.add(eachEntity);
            }
        }
        return output;
    }

    /**
     * Returns all other entities on the same tile as a given entity
     * @param entity The entity to be searched for
     * @return
     */
    public ArrayList<Entity> searchTile(Entity entity) {
        if (entity == null || entity.getPosition() == null) {
            return new ArrayList<>();
        }
        ArrayList<Entity> input = search(entity.getPosition());
        ArrayList<Entity> output = new ArrayList<>();
        for (Entity eachEntity : input) {
            if (!eachEntity.getId().equals(entity.getId())) {
                output.add(eachEntity);
            }
        }
        return output;
    }

    /**
     * @return The player in this EntityList
     */
    public PlayerCharacter findPlayer() {
        for (Entity eachEntity : this) {
            if (eachEntity instanceof PlayerCharacter) {
                return (PlayerCharacter) eachEntity;
            }
        }
        return null;
    }

    public Entity searchId(String id) {
        for (Entity eachEntity : this) {
            if (eachEntity.getId().equals(id)) {
                return eachEntity;
            }
        }
        return null;
    }

    /**
     * Create a grid representing the EntityList for use by Dijkstra's algorithm
     * @param entity The entity doing the Dijkstra pathfinding
     * @return A list of Positions
     */
    public List<Position> grid(Entity entity) {
        
        int maxX = 0;
        int minX = 0;
        int maxY = 0;
        int minY = 0;

        for (Entity e : this) {
            Position p = e.getPosition();
            maxX = Math.max(p.getX(), maxX);
            minX = Math.min(p.getX(), minX);
            maxY = Math.max(p.getY(), maxY);
            minY = Math.min(p.getY(), minY);
        }

        ArrayList<Position> positions = new ArrayList<>();
        // get the positions on the map a mob can spawn
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Position newPos = new Position(i,j);
                if (MovementManager.staticCheckMove(entity, newPos, this)) {
                    positions.add(newPos);
                }
            }
        }

        return positions;
    }
}
