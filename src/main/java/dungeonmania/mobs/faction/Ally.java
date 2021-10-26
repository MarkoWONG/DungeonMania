package dungeonmania.mobs;

public class Ally implements IFaction {
    public Ally() {
    }
    
    @Override
    public String getFaction() {
        return "Ally";
    }

    @Override
    public Boolean isAlly() {
        return true;
    }
    @Override
    public Boolean isEnemy() {
        return false;
    }
}
