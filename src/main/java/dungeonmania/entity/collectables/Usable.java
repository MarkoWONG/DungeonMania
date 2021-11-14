package dungeonmania.entity.collectables;

import dungeonmania.PlayerCharacter;

public interface Usable {
    /**
     * Use this item on the given character
     * @param player The character currently being controlled
     */
     void useItem(PlayerCharacter player);
}
