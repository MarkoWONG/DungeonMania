package dungeonmania.mobs;

import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public interface Subscriber {
    public void notifyFight();
    public void notifyMove(Position position);
    public void notifyInvisible(Boolean isInvisible);
    public void notifyInvincible(Boolean isInvincible);
}
