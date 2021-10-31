package dungeonmania.mobs.faction;

public class Enemy implements IFaction {
    public Enemy(){
    }
    
    @Override
    public String getFaction() {
        return "Enemy";
    }

    @Override
    public Boolean isAlly() {
        return false;
    }
    @Override
    public Boolean isEnemy() {
        return true;
    }
}
