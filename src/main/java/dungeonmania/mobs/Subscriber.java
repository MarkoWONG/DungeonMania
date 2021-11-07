package dungeonmania.mobs;

import dungeonmania.util.Direction;

public interface Subscriber {
    public void notifyFight();
    public void notifyMove(Direction direction);
    public void notifyInvisible(Boolean isInvisible);
    public void notifyInvincible(Boolean isInvincible);
}
