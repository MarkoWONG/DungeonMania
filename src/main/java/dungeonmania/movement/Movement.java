package dungeonmania.movement;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface Movement {
    public void move(Direction direction);
    public void teleport(Position position);
}
