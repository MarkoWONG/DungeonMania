package dungeonmania.mobs;

import dungeonmania.util.Position;

public interface Subscriber {
    /**
     * Notify a subscriber of a fight occuring at a position
     * @param position
     */
    void notifyFight(Position position);
    /**
     * Notify a subscriber of a move at a position
     * @param position
     */
    void notifyMove(Position position);
    /**
     * Notify a subscriber of a fight of a change in visibility
     * @param isInvisible the visibility status of the observed entity
     */
    void notifyInvisible(Boolean isInvisible);
    /**
     * Notify a subscriber of a fight of a change in invincibility
     * @param isInvincible the invicibility status of the observed entity
     */
    void notifyInvincible(Boolean isInvincible);
}
