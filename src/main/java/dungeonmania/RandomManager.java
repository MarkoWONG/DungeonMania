package dungeonmania;

import java.util.Random;
import java.util.UUID;

public class RandomManager {
    private static Long seed;
    private static Random random;

    public RandomManager(){
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    public RandomManager(Long newSeed){
        seed = newSeed;
        random = new Random(seed);
    }

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public static String generateID() {
        return UUID.randomUUID().toString();//UUID.nameUUIDFromBytes(Integer.toString(random.nextInt()).getBytes()).toString();
    }
}
