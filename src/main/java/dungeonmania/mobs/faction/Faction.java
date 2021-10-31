package dungeonmania.mobs.faction;

public class Faction {
    private IFaction faction;

    public Faction() {
    }

    public void setFaction(IFaction faction) {
        this.faction = faction;
    }

    public String getFaction() {
        return faction.getFaction();
    }

    public Boolean isAlly() {
        return faction.isAlly();
    }

    public Boolean isEnemy() {
        return faction.isEnemy();
    }
}
