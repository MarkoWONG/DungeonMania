package dungeonmania.mobs;
import dungeonmania.EntityList;
import dungeonmania.PlayerCharacter;
import dungeonmania.entity.collectables.Armour;
import dungeonmania.entity.collectables.BribeMaterial;
import dungeonmania.entity.collectables.CollectableEntity;
import dungeonmania.entity.collectables.rare.OneRing;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.abs;

public class Assassin extends Mercenary{
    public Assassin(Position position, int price, EntityList entities,int health, int ad, Random currRandom) {
        super(new Position(position.getX(), position.getY(),50),price,entities,health,ad, currRandom);
    }

    @Override
    public boolean isInteractable() {
        return isEnemy();
    }

    @Override
    public String getType() {
        return "assassin";
    }

    @Override
    public void click(PlayerCharacter character) {
        BribeMaterial bribeMat = searchBribeMaterial(character);
        OneRing ringBribe = searchOneRing(character);
        if (bribeMat == null) {
            throw new InvalidActionException("Bribe material is required to bribe");
        }
        if (!bribeMat.getType().equals("sceptre") && ringBribe == null) {
            throw new InvalidActionException("One ring is required to bribe this boss");
        }
        if (checkBribeRange(getCharacterPos(), this.getPosition())) {
            bribe(bribeMat.getBribeAmount(getPrice()), bribeMat.getBribeDuration());
            if (!bribeMat.getType().equals("sceptre")){
                ringBribe.usedInBribe(character);
            }
            bribeMat.usedInBribe(character);
        } else {
            throw new InvalidActionException("Mercenary out of range");
        }
    }

    /**
     * Search the player's inventory for all valid bribe materials, and return the highest priority one
     * @param character The player character's whose inventory is to be searched
     * @return The highest priority bribe material or null if none
     */
    private BribeMaterial searchBribeMaterial(PlayerCharacter character){
        // get all avaliable bribe Materials
        ArrayList<BribeMaterial> bribeMats = new ArrayList<BribeMaterial>();
        for (CollectableEntity ent : character.getInventory()){
            if (ent instanceof BribeMaterial){
                bribeMats.add((BribeMaterial) ent);
            }
        }

        // return the highest priority bribe Material (spectre -> sun_stone -> treasure)
        BribeMaterial highestPriorityMat = null;
        if (bribeMats.size() > 0){
            highestPriorityMat = bribeMats.get(0);
            for (BribeMaterial mat: bribeMats){
                if (highestPriorityMat.getBribePriority() < mat.getBribePriority()){
                    highestPriorityMat = mat;
                }
            }
        }
        return highestPriorityMat;
    }

    /**
     * Search the players inventory for the One Ring
     * @param character The player character's whose inventory is to be searched
     * @return The one ring or null if not found
     */
    private OneRing searchOneRing(PlayerCharacter character) {
        for (CollectableEntity ent : character.getInventory()){
            if (ent instanceof OneRing){
                return (OneRing) ent;
            }
        }
        return null;
    }
}
