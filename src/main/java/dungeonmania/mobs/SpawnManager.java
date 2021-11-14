package dungeonmania.mobs;

import java.util.ArrayList;
import java.util.Random;

import dungeonmania.EntityList;
import dungeonmania.entity.Entity;
import dungeonmania.util.Position;

public class SpawnManager {
    
    /**
     * Check whether a spawn can be made on the given position
     * @param map The EntityList for the current dungeon
     * @param position The position to be checked
     * @return Whether or not an entity can spawn in the given position
     */
    public static Boolean checkValidSpawn(EntityList map, Position position){
        // note: is only applied to mercenary atm, so is hard coded to that height

        ArrayList<Entity> inPos = map.search(position);
        for (Entity e: inPos) {
            if (e.getPosition().getLayer() >= 50) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param map
     * @return a randomly generated, valid spawn position
     */
    public static Position getRandPosition(EntityList map, Random currRandom){
        ArrayList<Position> possible = positions(map);
        // generate random number in the range of the list length
        if (possible.size() == 0) {
            return null;
        }
        int x = currRandom.nextInt(possible.size());

        return possible.get(x);
    }
    /**
     * 
     * @param map
     * @return a list of positions available to spawn in
     */
    private static ArrayList<Position> positions(EntityList map) {
        int maxX = 0;
        int minX = 0;
        int maxY = 0;
        int minY = 0;

        // set the range of the map
        for (Entity e : map) {
            Position p = e.getPosition();
            maxX = Math.max(p.getX(), maxX);
            minX = Math.min(p.getX(), minX);
            maxY = Math.max(p.getY(), maxY);
            minY = Math.min(p.getY(), minY);
        }

        ArrayList<Position> positions = new ArrayList<>();
        // get the positions on the map a mob can spawn
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Boolean canSpawn = true;
                ArrayList<Entity> es = map.search(new Position(i,j));
                for (Entity e : es) {
                    if (e.getPosition().getLayer() > 90) { // randomly spawning entities are at level 90
                        canSpawn = false;
                    }
                }

                if (canSpawn) {
                    positions.add(new Position(i,j));
                }
            }
        }
         
        return positions;
    }

}
