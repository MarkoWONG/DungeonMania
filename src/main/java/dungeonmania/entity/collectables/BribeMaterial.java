package dungeonmania.entity.collectables;
import dungeonmania.PlayerCharacter;

public interface BribeMaterial {
    public int getBribePriority();
    public int getBribeAmount(int price);
    public void usedInBribe(PlayerCharacter player);
}
