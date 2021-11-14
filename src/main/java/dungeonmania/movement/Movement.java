package dungeonmania.movement;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface Movement {
    /**
     * Move the entity in the given direction
     * @param direction The direction to be moved in
     */
    void move(Direction direction);
    /**
     * Teleport the entity to the given position
     * @param position The position to be teleported to
     */
    void teleport(Position position);
}
