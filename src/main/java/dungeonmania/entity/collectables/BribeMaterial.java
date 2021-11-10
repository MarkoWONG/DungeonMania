package dungeonmania.entity.collectables;
import dungeonmania.PlayerCharacter;

public interface BribeMaterial {
    public int getBribePriority();
    public int getBribeAmount(int price);
    public int getBribeDuration();
    public String getType();
    public void usedInBribe(PlayerCharacter player);
}
