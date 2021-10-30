package dungeonmania.entity.Mob;
import dungeonmania.util.Position;

public class Zombie extends MobEntity{
    public Zombie(Position position){
        super(new Position(position.getX(), position.getY(), 80), "zombie_toast"); 
    }
}
