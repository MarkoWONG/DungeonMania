package dungeonmania;

import java.util.Random;

public class RandomManager {
    private static RandomManager instance;
    private static Long seed;
    private static Random random;

    private RandomManager(){}

    private RandomManager(Long seed){}

    public static synchronized RandomManager getRandomManager() {
        if (instance == null) {
            instance = new RandomManager();
            seed = System.currentTimeMillis();
            random = new Random(seed);
        }
        return instance;
    }

    public static synchronized RandomManager getRandomManager(Long newSeed) {
        if (instance == null) {
            instance = new RandomManager();
            seed = newSeed;
            random = new Random(seed);
        }
        return instance;
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
