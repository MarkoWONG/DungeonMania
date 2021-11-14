package dungeonmania.entity.collectables;
import dungeonmania.PlayerCharacter;

public interface BribeMaterial {
    /**
     * @return The type of the bribe material
     */
     String getType();
    /**
     * @return the priority of this item as a bribe material, higher number means higher priority
     */
    int getBribePriority();

    /**
     * Determines how much the item reduces the bribe cost by
     * @param price Can supply the output if need be, such as for sceptres
     * @return
     */
    int getBribeAmount(int price);

    /**
     * How long this BribeMaterial will have an effect for
     * @return The duration of the allied status
     */
    int getBribeDuration();

    /**
     * Use this item as a material in bribe, MAY remove the item from inventory
     * @param player
     */
    void usedInBribe(PlayerCharacter player);
}
