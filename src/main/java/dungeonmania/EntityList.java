package dungeonmania;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="id")
public class EntityList extends ArrayList<Entity> {

    private final String id = UUID.randomUUID().toString();

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
}
